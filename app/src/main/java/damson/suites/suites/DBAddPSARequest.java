package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by Andy on 5/28/2016.
 */
public class DBAddPSARequest {
    private final String title;
    private final String description;

    @JsonCreator
    public DBAddPSARequest(@JsonProperty("title") String title,
                         @JsonProperty("description") String description) {
        this.title = title;
        this.description = description;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }
}
