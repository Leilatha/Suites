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
    private final int currentTurn;
    private final List<User> assignees;

    @JsonCreator
    public DBAddChoreRequest(@JsonProperty("name") String name,
                               @JsonProperty("description") String description,
                             @JsonProperty("currentTurn") int currentTurn,
                             @JsonProperty("assignees") List<User> assignees) {
        this.name = name;
        this.description = description;
        this.currentTurn = currentTurn;
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
    @JsonProperty("currentTurn")
    public int getCurrentTurn(){
        return currentTurn;
    }
    @JsonProperty("assignees")
    public List<User> getAssignees(){
        return assignees;
    }
}
