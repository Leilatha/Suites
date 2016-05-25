package damson.suites.suites;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

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


    DBHelper(String server_address, String account, String password) {
        if(server_address != null) host = server_address;
        this.account = account;
        this.password = password;
    }

    DBHelper(String server_address, User u) { this(server_address, u.getEmail(), u.getPassword());}

    DBHelper(String server_address) {
        this(server_address, null, null);
    }

    DBHelper(String account, String password) { this(null, account, password); }

    DBHelper(User u) { this(null, u.getEmail(), u.getPassword()); }

    DBHelper() {this(null, null, null); }

    private void setup(String path) {
        this.path = path;
        try {
            url = new URL(http, host, port, this.path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        client = new AsyncHttpClient();
        if(account != null && password != null) {
            client.setBasicAuth(account, password);
        }


        /*client.setReadTimeout(7000);
        client.setConnectTimeout(7000);
        client.setChunkedStreamingMode(0);
        client.setRequestProperty("Content-Type","application/json");*/
    }


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

    public void getUserSuites(AsyncResponseHandler<Suite[]> arh) {
        setup("/suite");

        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(Suite[].class, arh)); //TODO: If crash check this line
    }

    public void getUserInvites(AsyncResponseHandler<DBInvitation[]> arh) {
        setup("/invite");

        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBInvitation[].class, arh));
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

    public void listUsersInASuite(int suiteID, AsyncResponseHandler<DBUserListResult> arh) {
        setup("/suite/userlist?suiteid="+suiteID);

        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBUserListResult.class, arh));
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
        client.put(null, url.toExternalForm(), entity, APPLICATION_JSON,
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void deleteGrocery(Grocery grocery, AsyncResponseHandler<DBGenericResult> arh) {
        setup("/grocery?groceryid="+grocery.getId());

        client.delete(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }
}
