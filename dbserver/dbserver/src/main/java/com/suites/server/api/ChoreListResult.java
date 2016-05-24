package com.suites.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.suites.server.core.Chore;

import java.util.List;

public class ChoreListResult {
    private final boolean success;
    private final String message;
    private final List<ChoreView> choreList;

    @JsonCreator
    public ChoreListResult(@JsonProperty("success") boolean success,
                           @JsonProperty("message") String message,
			   @JsonProperty("choreList") List<ChoreView> choreList) {
        this.success = success;
        this.message = message;
        this.choreList = choreList;
    }

    @JsonProperty("success")
    public boolean getSuccess() {
        return success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("choreList")
    public List<ChoreView> getChoreList() {
        return choreList;
    }
}
