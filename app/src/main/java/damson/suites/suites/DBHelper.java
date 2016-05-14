package damson.suites.suites;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Andy on 5/13/2016.
 */
public class DBHelper {

    // The Jackson JSON mapper
    public final static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
}
