package damson.suites.suites;


import android.support.v4.util.CircularArray;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Andy on 5/27/2016.
 */
public class DBChoreView {
    private final DBChore chore;
    private final List<User> assignees;
    private int zero = 0;

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

    public void randomize(){
        User [] users = new User[assignees.size()];
        for(int i = 0; i < assignees.size(); i++){
            users[i] = assignees.get(i);
        }

        //set up random to return random indices
        Random random = new Random();
        int index = random.nextInt(users.length);
        //stores indices already stored
        ArrayList<Integer> found = new ArrayList<>(users.length);

        CircularArray<User> rotation = new CircularArray<User>(users.length);

        //Loop to fill in rotation  //This could accidentally run for a long time
        while(rotation.size() < users.length){
            if(!found.contains(index)) {
                rotation.addLast(users[index]);
                found.add(new Integer(index));
                index = random.nextInt(users.length);
            }
            else{
                index = random.nextInt(users.length);
            }
        }

        //put new rotation into assignees
        for(int i = 0; i < rotation.size(); i++){
            assignees.remove(i);
            assignees.add(i, rotation.get(i));
        }
    }
}
