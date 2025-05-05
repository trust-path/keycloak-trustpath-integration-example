package trustpath.io.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RiskRequest {
	
	public String ip;
	public String email;
	@JsonProperty("event_type")
	public String eventType;
	public User user;

	public RiskRequest(String ip, String email, String eventType, String firstName, String lastName) {
		this.ip = ip;
		this.email = email;
		this.eventType = eventType;
		this.user = new User(firstName, lastName);
	}
}