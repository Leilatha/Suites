package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DBGroceryListResult {
    private final boolean success;
    private final String message;
    private final List<Grocery> groceryList;

    @JsonCreator
    public DBGroceryListResult(@JsonProperty("success") boolean success,
                          @JsonProperty("message") String message,
			  @JsonProperty("groceryList") List<Grocery> groceryList) {
        this.success = success;
        this.message = message;
        this.groceryList = groceryList;
    }

    @JsonProperty("success")
    public boolean getSuccess() {
        return success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("groceryList")
    public List<Grocery> getGroceryList() {
        return groceryList;
    }
}
