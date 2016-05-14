package com.suites.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.suites.server.core.Grocery;

import java.util.List;

public class GroceryListResult {
    private final boolean success;
    private final String message;
    private final List<Grocery> groceryList;

    @JsonCreator
    public GroceryListResult(@JsonProperty("success") boolean success,
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
