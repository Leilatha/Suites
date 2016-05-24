package com.suites.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.suites.server.api.PSAView;

import java.util.List;

public class PSAListResult {
    private final boolean success;
    private final String message;
    private final List<PSAView> psaList;

    @JsonCreator
    public PSAListResult(@JsonProperty("success") boolean success,
                         @JsonProperty("message") String message,
                         @JsonProperty("psaList") List<PSAView> psaList) {
        this.success = success;
        this.message = message;
	this.psaList = psaList;
    }

    @JsonProperty("success")
    public boolean getSuccess() {
        return success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("psaList")
    public List<PSAView> getPSAList() {
        return psaList;
    }
}
