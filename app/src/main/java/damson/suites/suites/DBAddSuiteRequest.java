package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DBAddSuiteRequest {
    private final String name;

    @JsonCreator
    public DBAddSuiteRequest(@JsonProperty("name") String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }
}
