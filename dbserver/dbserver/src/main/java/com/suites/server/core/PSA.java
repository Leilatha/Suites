package com.suites.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.sql.Timestamp;

public class PSA {
    private final int id;
    private final int authorId;
    private final String title;
    private final String description;
    private final Timestamp timestamp;

    @JsonCreator
    public PSA(@JsonProperty("id") int id,
               @JsonProperty("authorId") int authorId,
               @JsonProperty("title") String title,
               @JsonProperty("description") String description,
               @JsonProperty("timestamp") Timestamp timestamp) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("authorId")
    public int getAuthorId() {
        return authorId;
    }
    
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
