package damson.suites.suites;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

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
    int port = 8080;
    HttpURLConnection client;
    URL url;

    public enum RequestMethod {
        GET, POST, PUT, HEAD, DELETE, OPTIONS, TRACE, CONNECT, PATCH
    }
    RequestMethod requestMethod;


    DBHelper(String server_address, String account, String password) {
        host = server_address;
        this.account = account;
        this.password = password;

        try {
            switch (requestMethod) {
                case GET:
                    client.setRequestMethod("GET");
                    client.setDoOutput(false);
                    break;
                case POST:
                    client.setRequestMethod("POST");
                    client.setDoOutput(true);
                    break;
                //default: is nothing
            }
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }

        client.setReadTimeout(7000);
        client.setConnectTimeout(7000);
        client.setChunkedStreamingMode(0);
        client.setRequestProperty("Content-Type","application/json");
    }

    DBHelper(String server_address) {
        this(server_address, null, null);
    }

    private void setup(String path) {
        try {
            url = new URL(http, host, port, path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            client = (HttpURLConnection) url.openConnection();
        } catch (SocketTimeoutException error){
            // Handles URL access timeout
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerAccount() {

    }

    public void getResponseCode() {

    }

    public void login() {

    }
}
