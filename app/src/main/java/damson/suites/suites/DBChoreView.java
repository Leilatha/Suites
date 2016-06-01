package damson.suites.suites;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Andy on 5/27/2016.
 */
public class DBChoreView {
    private final DBChore chore;
    private final List<User> assignees;

    @JsonCreator
    public DBChoreView(@JsonProperty("id") int id,
                     @JsonProperty("name") String name,
                     @JsonProperty("description") String description,
                     @JsonProperty("currentTurn") int currentTurn,
                     @JsonProperty("assignees") List<User> assignees) {
        chore = new DBChore (id, name, description, currentTurn);
        this.assignees = assignees;
    }

    public DBChoreView(DBChore chore, List<User> assignees) {
        this.chore = chore;
        this.assignees = assignees;
    }

    @JsonProperty("id")
    public int getId() {
        return chore.getId();
    }

    @JsonProperty("name")
    public String getName() {
        return chore.getName();
    }

    @JsonProperty("description")
    public String getDescription() {
        return chore.getDescription();
    }

    @JsonProperty("currentTurn")
    public int getCurrentTurn() {
        return chore.getCurrentTurn();
    }

    @JsonProperty("assignees")
    public List<User> getAssignees() {
        return assignees;
    }
}
