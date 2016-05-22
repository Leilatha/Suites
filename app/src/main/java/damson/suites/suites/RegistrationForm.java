package damson.suites.suites;

/**
 * Created by Andy on 5/12/2016.
 */
public class RegistrationForm {
    private String _email;
    private String _password;
    private String _name;

    protected String getEmail() { return _email; }
    public String getName() { return _name; }
    protected String getPassword() { return _password; }

    protected void setEmail(String e) { _email = e; }
    protected void setName(String n) { _name = n; }
    protected void setPassword(String p) { _password = p; }

    public RegistrationForm(String email, String pw, String name) {
        _email = email;
        _name = name;
        _password = pw;
    }
}
