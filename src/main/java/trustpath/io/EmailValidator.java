package trustpath.io;

import jakarta.ws.rs.core.MultivaluedMap;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.authentication.forms.RegistrationPage;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import trustpath.io.dto.response.State;

import java.io.IOException;
import java.util.List;

// Class that implements the actual validation logic
public class EmailValidator implements FormAction {

  private static final Logger logger = LoggerFactory.getLogger(EmailValidator.class);
  private final TrustPathClient trustPathClient = new TrustPathClient();

  @Override
  public void buildPage(FormContext context, LoginFormsProvider form) {
    // Nothing to do here
  }

  @Override
  public void validate(ValidationContext context) {
    MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
    String email = formData.getFirst(RegistrationPage.FIELD_EMAIL);
    String firstName = formData.getFirst(RegistrationPage.FIELD_FIRST_NAME);
    String lastName = formData.getFirst(RegistrationPage.FIELD_LAST_NAME);

    if (email == null || email.trim().isEmpty()) {
      FormMessage errorMessage = new FormMessage(RegistrationPage.FIELD_EMAIL, "Email is required");
      context.error(RegistrationPage.FIELD_EMAIL);
      context.validationError(formData, List.of(errorMessage));
      return;
    }

    
    // Get client IP address - note that TrustpathApi implementation currently doesn't use this value
    String ipAddress = null;
    if (context.getSession() != null && context.getSession().getContext() != null) {
      ipAddress = context.getSession().getContext().getConnection().getRemoteAddr();
    }
    
    try {
      // Evaluate risk using TrustPathApi
      State decision = trustPathClient.evaluateRisk(ipAddress, email, firstName, lastName);
      
      switch (decision) {
        case APPROVE:
          // User is approved, continue registration
          logger.info("Registration approved for email: {}", email);
          context.success();
          break;
        case REVIEW:
          // User needs review - could potentially add custom actions here
          logger.info("Registration needs review for email: {}", email);
          // For now we'll allow registration but could implement different behavior
          context.success();
          break;
        case DECLINE:
          // User is declined, show error message
          logger.info("Registration declined for email: {}", email);
          FormMessage errorMessage = new FormMessage(RegistrationPage.FIELD_EMAIL, 
              "This email address has been flagged as risky and cannot be used for registration.");
          context.error(RegistrationPage.FIELD_EMAIL);
          context.validationError(formData, List.of(errorMessage));
          break;
      }
    } catch (IOException e) {
      // Here you need to decide, whether you want to delay registration
      // or allow it if you have network problems or API is not reachable.
      logger.error("Error checking risk status: {}", e.getMessage(), e);
      FormMessage errorMessage = new FormMessage(RegistrationPage.FIELD_EMAIL, "Unable to validate email at this time. Please try again later.");
      context.error(RegistrationPage.FIELD_EMAIL);
      context.validationError(formData, List.of(errorMessage));
    }
  }

  @Override
  public void success(FormContext context) {
    // Nothing to do here
  }

  @Override
  public boolean requiresUser() {
    return false;
  }

  @Override
  public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
    return true;
  }

  @Override
  public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    // Nothing to do here
  }

  @Override
  public void close() {
    // Nothing to do here
  }
}