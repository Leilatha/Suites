package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.sql.Timestamp;

public class DBPSAView {
    private final int id;
    private final User author;
    private final String title;
    private final String description;
    private final Timestamp timestamp;

    @JsonCreator
    public DBPSAView(@JsonProperty("id") int id,
                   @JsonProperty("author") User author,
                   @JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("timestamp") Timestamp timestamp) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("author")
    public User getAuthor() {
        return author;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }
}