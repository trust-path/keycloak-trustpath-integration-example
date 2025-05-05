package trustpath.io;


import trustpath.io.dto.request.RiskRequest;
import trustpath.io.dto.response.RiskAssessment;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import trustpath.io.dto.response.State;

import java.io.IOException;

public class TrustPathClient
{

   public static final String API_URL = System.getenv("TRUST_PATH_API_URL");
   public static final String API_KEY = System.getenv("TRUST_PATH_API_KEY");

   private final Logger log = LoggerFactory.getLogger(TrustPathClient.class);
   private static ObjectMapper objectMapper;

   static {
      objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
   }

   /**
    * Evaluates the risk for a user registration and returns a decision.
    * 
    * @param ip Client IP address
    * @param email User's email address
    * @param firstName User's first name
    * @param lastName User's last name
    * @return State enum value (APPROVE, REVIEW, or DECLINE)
    * @throws IOException If API communication fails
    */
   public State evaluateRisk(String ip, String email, String firstName, String lastName) throws IOException {
      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost(API_URL);
      
      ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
      String json = ow.writeValueAsString(new RiskRequest(ip, email, "account_register", firstName, lastName));

      log.info(json);
      StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
      httpPost.addHeader("Authorization", "Bearer "+API_KEY);
      httpPost.setEntity(entity);
      
      CloseableHttpResponse response = httpClient.execute(httpPost);
      String responseJson = EntityUtils.toString(response.getEntity());
      RiskAssessment riskAssessment = objectMapper.readValue(responseJson, RiskAssessment.class);
      
      response.close();
      httpClient.close();
      
      return riskAssessment.data.score.state;
   }
}
