package damson.suites.suites;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Andy on 5/13/2016.
 *
 * Usage:   Must first either make new default constructor and call login() or use the
 *          constructor with account and password.
 *          Call each method using a child of the AsyncResponseHandler class. Add an inline class
 *          definition.
 *          Android Studio can fill in the AsyncResponseHandler framework for you if you
 *          press tab/enter to autofill the AysncResponseHandler
 *          ex.
 *      new AsyncResponseHandler<ResponseClass>() {
 *          // fill in necessary methods
 *
 *          // In onSuccess, response is the intended return variable.
 *      }
 */
public class DBHelper {

    // The Jackson JSON mapper
    public final static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
    public static final String APPLICATION_JSON = "application/json";
    final String http = "http";
    String host = "104.236.61.10";
    String path = "";
    String account = "";
    String password = "";
    int port = 8080;
    AsyncHttpClient client;
    URL url;

    public enum RequestMethod {
        GET, POST, PUT, HEAD, DELETE, OPTIONS, TRACE, CONNECT, PATCH
    }
    RequestMethod requestMethod;

    /**
     * Used when a different server address from the default is needed.
     * @param server_address
     * @param account
     * @param password
     */
    DBHelper(String server_address, String account, String password) {
        if(server_address != null) host = server_address;
        this.account = account;
        this.password = password;
    }

    /**
     * Used when a different server address from the default is needed.
     * @param server_address
     * @param u
     */
    DBHelper(String server_address, User u) { this(server_address, u.getEmail(), u.getPassword());}

    /**
     * Used when a different server address from the default is needed. Used only for login().
     * @param server_address
     */
    DBHelper(String server_address) {
        this(server_address, null, null);
    }

    /**
     * Constructor used when account is known.
     * @param account
     * @param password
     */
    DBHelper(String account, String password) { this(null, account, password); }

    /**
     * Constructor used when account is known.
     * @param u
     *          User, usually at User.user.
     */
    DBHelper(User u) { this(null, u.getEmail(), u.getPassword()); }

    /**
     * Constructor used for the login() method only.
     */
    DBHelper() {this(null, null, null); }

    /**
     * Setup the URL and the login header, if specified.
     * @param path
     */
    private void setup(String path) {
        this.path = path;
        try {
            url = new URL(http, host, port, this.path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        client = new AsyncHttpClient();
        client.setTimeout(3000);

        if(account != null && password != null) {
            client.setBasicAuth(account, password);
        }


        /*client.setReadTimeout(7000);
        client.setChunkedStreamingMode(0);
        client.setRequestProperty("Content-Type","application/json");*/
    }


    /**
     * Use the default constructor.
     *
     * @param mEmail
     * @param mPassword
     * @param mName
     * @param arh
     */
    public void registerAccount(String mEmail, String mPassword, String mName,
                                AsyncResponseHandler<DBGenericResult> arh)  {
        setup("/account");

        // Output stream to server
        DBRegistrationRequest user = new DBRegistrationRequest(mEmail, mName, mPassword);
        String jsonrequest = null;
        try {
            jsonrequest = DBHelper.mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonrequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void editAccount(String mEmail, String mPassword, String mName,
                            AsyncResponseHandler<DBGenericResult> arh) {
        registerAccount(mEmail, mPassword, mName, arh);
    }

    /**
     * Use the default constructor.
     *
     * @param mEmail
     * @param mPassword
     * @param arh
     */
    public void login(String mEmail, String mPassword, AsyncResponseHandler<User> arh) {
        account = mEmail;
        password = mPassword;
        setup("/account");

        // Get info
        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(User.class, arh));
    }

    public void addSuite(String suitename, AsyncResponseHandler<DBGenericResult> arh) {
        setup("/suite");

        // Output stream to server
        String jsonrequest = null;
        DBAddSuiteRequest req = new DBAddSuiteRequest(suitename);
        try {
            jsonrequest = DBHelper.mapper.writeValueAsString(req);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonrequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void getUserSuites(AsyncResponseHandler<List<Suite>> arh) {
        setup("/suite");

        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(List.class, Suite.class, arh));
    }

    public void getUserInvites(AsyncResponseHandler<Suite[]> arh) {
        setup("/invite");

        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(Suite[].class, arh));
    }

    public void makeInvitation(String invitee, int suiteID, AsyncResponseHandler<DBGenericResult> arh) {
        setup("/invite");

        // Output stream to server
        String jsonrequest = null;
        DBInvitation invite = new DBInvitation(invitee, suiteID);
        try {
            jsonrequest = DBHelper.mapper.writeValueAsString(invite);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonrequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void joinSuite(int suiteID, AsyncResponseHandler<DBGenericResult> arh) {
        setup("/join");

        // Output stream to server
        String jsonrequest = null;

        try {
            jsonrequest = DBHelper.mapper.writeValueAsString(suiteID);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonrequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void leaveSuite(int suiteID, AsyncResponseHandler<Void> arh) {
        setup("/suite/leave");

        String jsonrequest = null;
        try {
            jsonrequest = DBHelper.mapper.writeValueAsString(suiteID);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonrequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.put(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(null, arh));
    }

    public void listUsersInASuite(int suiteID, AsyncResponseHandler<DBUserListResult> arh) {
        setup("/suite/userlist?suiteid="+suiteID);

        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBUserListResult.class, arh));
    }

    public void listSuiteChores(int suiteID, AsyncResponseHandler<DBChoresListResult> arh){
        setup("/chore?suiteid="+suiteID);
        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBChoresListResult.class, arh));
    }

    public void listSuiteGroceries(int suiteID, AsyncResponseHandler<DBGroceryListResult> arh) {
        setup("/grocery?suiteid="+suiteID);

        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBGroceryListResult.class, arh));
    }

    public void addGroceryToSuite(int suiteID, DBAddGroceryRequest grocery, AsyncResponseHandler<DBGenericResult> arh) {
        setup("/grocery?suiteid="+suiteID);

        // Output stream to server
        String jsonrequest = null;
        try {
            jsonrequest = DBHelper.mapper.writeValueAsString(grocery);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonrequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void editGrocery(Grocery grocery, AsyncResponseHandler<DBGenericResult> arh) {
        setup("/grocery?groceryid="+grocery.getId());
        DBAddGroceryRequest req =
                new DBAddGroceryRequest(grocery.getName(), grocery.getQuant(), grocery.getPrice());

        // Output stream to server
        String jsonrequest = null;
        try {
            jsonrequest = DBHelper.mapper.writeValueAsString(req);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonrequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.put(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void deleteGrocery(Grocery grocery, AsyncResponseHandler<DBGenericResult> arh) {
        setup("/grocery?groceryid="+grocery.getId());

        client.delete(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void addChoreToSuite(int suiteID, DBAddChoreRequest chore, AsyncResponseHandler<DBGenericResult> arh){
        setup("/chore?suiteid="+suiteID);

        //output stream to server
        String jsonrequest = null;
        try{
            jsonrequest = DBHelper.mapper.writeValueAsString(chore);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        StringEntity entity = null;
        try{
            entity = new StringEntity(jsonrequest);
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        client.post(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void editChore(DBChoreView chore, AsyncResponseHandler<DBGenericResult> arh){
        setup("/chore?choreid="+chore.getId());

        List<Integer> userInts = new ArrayList<Integer>();
        List<User> assignees = chore.getAssignees();
        for(User u : assignees) {
            userInts.add(u.getId());
        }

        DBAddChoreRequest req =
                new DBAddChoreRequest(chore.getName(), chore.getDescription(), userInts);

        // Output stream to server
        String jsonrequest = null;
        try {
            jsonrequest = DBHelper.mapper.writeValueAsString(req);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonrequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.put(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void deleteChore(DBChoreView chore, AsyncResponseHandler<DBGenericResult> arh){
        setup("/chore?choreid="+chore.getId());

        client.delete(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void advanceChore(DBChoreView chore, AsyncResponseHandler<DBGenericResult> arh) {
        setup("/chore/advance?choreid="+chore.getId());

        client.post(url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void listSuitePSA(int suiteID, AsyncResponseHandler<DBPSAListResult> argh){
        setup("/psa?suiteid="+suiteID);

        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBPSAListResult.class, argh));
    }

    public void postSuitePSA(int suiteID, String title, String description, AsyncResponseHandler<DBGenericResult> arh) {
        setup("/psa?suiteid="+suiteID);

        // Output stream to server
        String jsonrequest = null;
        DBAddPSARequest request = new DBAddPSARequest(title, description);

        try {
            jsonrequest = DBHelper.mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonrequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void editSuitePSA(DBPSAView psa, AsyncResponseHandler<DBGenericResult> arh) {
        editSuitePSA(psa.getId(), psa.getTitle(), psa.getDescription(), arh);
    }
    public void editSuitePSA(int psaID, String psaTitle, String psaDescription,
                             AsyncResponseHandler<DBGenericResult> arh) {
        setup("/psa?psaid="+psaID);

        // Output stream to server
        String jsonrequest = null;
        DBAddPSARequest request = new DBAddPSARequest(psaTitle, psaDescription);

        try {
            jsonrequest = DBHelper.mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonrequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void deleteSuitePSA(int psaID, AsyncResponseHandler<DBGenericResult> arh) {
        setup("/psa?psaid="+psaID);

        client.delete(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }
}
