package damson.suites.suites;

import android.app.Application;
import android.test.ApplicationTestCase;
import java.io.*;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    //You should see this Comment as a test. 4/21/2016 4:09pm
    public static void main( String[] args ) {
        System.out.println("hi");
    }
}