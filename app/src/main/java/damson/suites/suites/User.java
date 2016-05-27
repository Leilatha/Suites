package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.Principal;

public class User implements Principal {
    protected static User user = new User(0, null, null, null);

    private final int id;
    private final String email;
    private final String name;
    private final String profilePicture;
    private String password;

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String pw) {
        password =  pw;
    }

    public boolean equals(Object another) {
        return another instanceof User && ((User)another).id == id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public int hashCode() {
        return id;
    }

    @JsonCreator
    public User(@JsonProperty("id") int id,
                @JsonProperty("email") String email,
                @JsonProperty("name") String name,
                @JsonProperty("profilePicture") String profilePicture) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profilePicture = profilePicture;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("profilePicture")
    public String getProfilePicture() {
        return profilePicture;
    }
}
