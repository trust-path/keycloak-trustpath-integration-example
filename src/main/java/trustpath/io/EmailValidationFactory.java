package trustpath.io;

import org.keycloak.Config;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;

public class EmailValidationFactory implements FormActionFactory {

  public static final String PROVIDER_ID = "trustpath-io-fraud-detection";
  private static final String PROVIDER_NAME = "TrustPath.io - Fraud Detection";
  private static final String PROVIDER_HELP_TEXT = "Validate registration attempts with TrustPath.io API to detect and block disposable email providers and bots.";

  private static final List<ProviderConfigProperty> CONFIG_PROPERTIES = new ArrayList<>();
  
  private static final Requirement[] REQUIREMENT_CHOICES = {
      Requirement.REQUIRED, Requirement.DISABLED
  };

  @Override
  public FormAction create(KeycloakSession session) {
    return new EmailValidator();
  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }

  @Override
  public String getDisplayType() {
    return PROVIDER_NAME;
  }

  @Override
  public String getHelpText() {
    return PROVIDER_HELP_TEXT;
  }

  @Override
  public void close() {
    // No-op
  }

  @Override
  public void init(Config.Scope scope) {
    // No-op
  }

  @Override
  public void postInit(KeycloakSessionFactory sessionFactory) {
    // No-op
  }

  @Override
  public String getReferenceCategory() {
    return "trustpath-email-validation";
  }

  @Override
  public Requirement[] getRequirementChoices() {
    return REQUIREMENT_CHOICES;
  }

  @Override
  public boolean isConfigurable() {
    return false;
  }

  @Override
  public boolean isUserSetupAllowed() {
    return false;
  }

  @Override
  public List<ProviderConfigProperty> getConfigProperties() {
    return CONFIG_PROPERTIES;
  }
}