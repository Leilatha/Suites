package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

/**
 * Created by Andy on 5/27/2016.
 */
public class DBChoresListResult {
    private final boolean success;
    private final String message;
    private final List<DBChoreView> choreList;

    @JsonCreator
    public DBChoresListResult(@JsonProperty("success") boolean success,
                           @JsonProperty("message") String message,
                           @JsonProperty("choreList") List<DBChoreView> choreList) {
        this.success = success;
        this.message = message;
        this.choreList = choreList;
    }

    @JsonProperty("success")
    public boolean getSuccess() {
        return success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("choreList")
    public List<DBChoreView> getChoreList() {
        return choreList;
    }
}
