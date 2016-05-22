package damson.suites.suites;

import android.support.v4.util.CircularArray;

import java.lang.String;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Michael Chin on 5/17/2016.
 */
public class Chore {
    private CircularArray<String> rotation;
    private String name;
    private String description;

    /* Constructor */
    //Need something that returns the list of users
    public Chore(String title, String description, int numUsers){
        name = title;
        this.description = description;
        //List of user names, put into LinkedList
        rotation = new CircularArray<>(numUsers);
        for(int i = 0; i < numUsers; i++) {
            rotation.addFirst("user " + Integer.toString(i));
        }
        randomize();
    }

    /* Changes the name of the chore */
    public void setName(String str){
        name = str;
    }

    /* Changes the description of the chore */
    public void setDescription(String str){
        description = str;
    }

    /* Returns the name of the chore */
    public String getName(){
        return name;
    }

    /* Returns the description of the chore */
    public String getDescription(){
        return description;
    }

    //Randomizes the list of users
    public void randomize(){
        String [] users = new String[rotation.size()];
        for(int i = 0; i < rotation.size(); i++){
            users[i] = rotation.get(i);
        }

        //set up random to return random indices
        Random random = new Random();
        int index = random.nextInt(users.length);
        //stores indices already stored
        ArrayList<Integer> found = new ArrayList<>(users.length);
        //clear rotation
        rotation.clear();

        //Loop to fill in rotation
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
    }

}
