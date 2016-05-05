package com.suites.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class RegistrationRequest {
    private final String email;
    private final String name;
    private final String password;

    @JsonCreator
    public RegistrationRequest(@JsonProperty("email") String email,
                               @JsonProperty("name") String name,
                               @JsonProperty("password") String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }
}
