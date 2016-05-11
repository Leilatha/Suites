package com.suites.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;


public class AddSuiteResult {
    private final boolean success;
    private final String message;

    @JsonCreator
    public AddSuiteResult(@JsonProperty("success") boolean success,
                          @JsonProperty("message") String message) {
        this.success = success;
        this.message = message;
    }

    @JsonProperty("success")
    public boolean getSuccess() {
        return success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
}
