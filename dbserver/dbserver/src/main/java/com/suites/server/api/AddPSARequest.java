package com.suites.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class AddPSARequest {
    private final String title;
    private final String description;

    @JsonCreator
    public AddPSARequest(@JsonProperty("title") String title,
                         @JsonProperty("description") String description) {
        this.title = title;
        this.description = description;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }
}
