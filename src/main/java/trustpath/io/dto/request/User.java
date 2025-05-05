package trustpath.io.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	@JsonProperty("first_name")
    public String firstName;
    
    @JsonProperty("last_name")
    public String lastName;

    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
}