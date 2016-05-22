package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DBUserListResult {
    private final boolean success;
    private final String message;
    private final List<User> userList;

    @JsonCreator
    public DBUserListResult(@JsonProperty("success") boolean success,
                            @JsonProperty("message") String message,
                            @JsonProperty("userList") List<User> userList) {
        this.success = success;
        this.message = message;
	    this.userList = userList;
    }

    @JsonProperty("success")
    public boolean getSuccess() {
        return success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("userList")
    public List<User> getUserList() {
        return userList;
    }
}
