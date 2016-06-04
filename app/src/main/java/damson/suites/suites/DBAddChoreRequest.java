package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Michael Chin on 5/28/2016.
 */
public class DBAddChoreRequest{
    private final String name;
    private final String description;
    private final List<Integer> assignees;

    @JsonCreator
    public DBAddChoreRequest(@JsonProperty("name") String name,
                               @JsonProperty("description") String description,
                             @JsonProperty("assignees") List<Integer> assignees) {
        this.name = name;
        this.description = description;
        this.assignees = assignees;
    }
    @JsonProperty("name")
    public String getName(){
        return name;
    }
    @JsonProperty("description")
    public String getDescription(){
        return description;
    }
    @JsonProperty("assignees")
    public List<Integer> getAssignees(){
        return assignees;
    }
}
