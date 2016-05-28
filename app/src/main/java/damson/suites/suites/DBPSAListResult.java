package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class DBPSAListResult {
    private final boolean success;
    private final String message;
    private final List<DBPSAView> psaList;

    @JsonCreator
    public DBPSAListResult(@JsonProperty("success") boolean success,
                         @JsonProperty("message") String message,
                         @JsonProperty("psaList") List<DBPSAView> psaList) {
        this.success = success;
        this.message = message;
        this.psaList = psaList;
    }

    @JsonProperty("success")
    public boolean getSuccess() {
        return success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("psaList")
    public List<DBPSAView> getPSAList() {
        return psaList;
    }
}
