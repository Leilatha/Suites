package com.suites.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class Invitation {
    private final String invitee; // Email
    private final int suiteId;

    @JsonCreator
    public Invitation(@JsonProperty("invitee") String invitee,
                      @JsonProperty("suiteId") int suiteId) {
        this.invitee = invitee;
        this.suiteId = suiteId;
    }

    @JsonProperty("invitee")
    public String getInvitee() {
        return invitee;
    }

    @JsonProperty("suiteId")
    public int getSuiteId() {
        return suiteId;
    }
}
