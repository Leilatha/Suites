package damson.suites.suites;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Andy on 5/13/2016.
 *
 * Use in a class that extends AsyncTask.
 */
public class DBHelper {

    // The Jackson JSON mapper
    public final static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
    final String http = "http";
    String host = "104.236.61.10";
    String path = "";
    String account = "";
    String password = "";
    User user;
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

    DBHelper(String server_address) {
        this(server_address, null, null);
    }

    DBHelper(String account, String password) { this(null, account, password); }

    DBHelper() {this(null, null, null); }

    private void setup(String path) {
        this.path = path;
        try {
            url = new URL(http, host, port, this.path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        client = new AsyncHttpClient();
        if(user != null) {

        }
        else if(account != null && password != null) {
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

        client.post(null, url.toExternalForm(), entity, "application/json",
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void login(String mEmail, String mPassword, AsyncResponseHandler<User> arh) {
        account = mEmail;
        password = mPassword;
        setup("/account");

        // Get info
        client.get(null, url.toExternalForm(),
                new AsyncResponseHandlerAdapter<>(DBGenericResult.class, arh));
    }

    public void addSuite() {

    }

    public void getUserSuites() {

    }

    public void getUserInvites() {

    }

    public void makeInvitation() {

    }

    public void joinSuite() {

    }

    public void listUsersInASuite() {

    }

    public void listSuiteGroceries() {

    }

    public void addGroceryToSuite() {

    }

    public void editGrocery() {

    }

    public void deleteGrocery() {
        
    }
}
