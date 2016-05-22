package com.suites.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class PSA {
    private final int id;
    private final int suiteId;
    private final String title;
    private final String description;

    @JsonCreator
    public PSA(@JsonProperty("id") int id,
               @JsonProperty("suiteId") int suiteId,
               @JsonProperty("title") String title,
               @JsonProperty("description") String description) {
        this.id = id;
        this.suiteId = suiteId;
        this.title = title;
        this.description = description;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("suiteId")
    public int getSuiteId() {
        return suiteId;
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
