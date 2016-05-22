package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Andy on 5/10/2016.
 *
 * A class to store Member information. Used particularly for Jackson JSON creation.
 */
public class Member {
    private int _id;
    private String _email;
    private String _name;
    private String _password;
    private byte[] _profilepicture; // TODO: Implement pictures

    protected int getID() { return _id; }
    protected String getEmail() { return _email; }
    public String getName() { return _name; }
    protected String getPassword() { return _password; }
    public byte[] getProfilePicture() { return _profilepicture; }

    @JsonProperty("id")
    protected void setID(int i) { _id = i; }
    @JsonProperty("email")
    protected void setEmail(String e) { _email = e; }
    @JsonProperty("name")
    protected void setName(String n) { _name = n; }
    @JsonProperty("password")
    protected void setPassword(String p) { _password = p; }
    @JsonProperty("profilePicture")
    protected void setUserImage(byte[] pp) { _profilepicture = pp; }

    @JsonCreator
    public Member(  @JsonProperty("id") int id,
                    @JsonProperty("email") String email,
                    @JsonProperty("name") String name,
                    @JsonProperty("password") String pw,
                    @JsonProperty("profilePicture")byte[] picture) {
        _id = id;
        _email = email;
        _name = name;
        _password = pw;
        _profilepicture = picture;
    }

    public Member() {
        this(0, null, null, null, null);
    }
}
