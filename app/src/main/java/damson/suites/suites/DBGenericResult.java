package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DBGenericResult {
    private final boolean success;
    private final String message;

    @JsonCreator
    public DBGenericResult(@JsonProperty("success") boolean success,
                           @JsonProperty("message") String message) {
        this.success = success;
        this.message = message;
    }

    @JsonProperty("success")
    public boolean getSuccess() {
        return success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
}
