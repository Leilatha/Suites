package com.suites.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class AddChoreRequest {
    private final String name;
    private final String description;
    private final List<Integer> assignees;

    @JsonCreator
    public AddChoreRequest(@JsonProperty("name") String name,
                           @JsonProperty("description") String description,
                           @JsonProperty("assignees") List<Integer> assignees) {
        this.name = name;
        this.description = description;
        this.assignees = assignees;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("assignees")
    public List<Integer> getAssignees() {
        return assignees;
    }
}
