package trustpath.io.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
/**
 * Enum representing possible risk states returned by the Trustpath API.
 */
public enum State {
    APPROVE,
    REVIEW,
    DECLINE;
    
    @JsonCreator
    public static State fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        for (State state : State.values()) {
            if (state.name().equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Invalid State value: " + value);
    }
    
    @JsonValue
    public String getValue() {
        return name().toLowerCase();
    }
}
