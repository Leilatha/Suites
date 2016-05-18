package com.suites.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class Chore {
    private final int id;
    private final String name;
    private final String descript;
    private final int currentTurn;

    @JsonCreator
    public Chore(@JsonProperty("id") int id,
                 @JsonProperty("name") String name,
                 @JsonProperty("description") String descript,
                 @JsonProperty("currentTurn") int currentTurn) {
        this.id = id;
        this.name = name;
        this.descript = descript;
        this.currentTurn = currentTurn;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return descript;
    }

    @JsonProperty("currentTurn")
    public int getCurrentTurn() {
        return currentTurn;
    }
}
