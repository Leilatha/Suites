package com.suites.server.core;

import java.security.Principal;

public class User implements Principal {
    int id;
    String email;
    String name;
    String profilePicture;

    public boolean equals(Object another) {
        return another instanceof User && ((User)another).id == id;
    }

    public String getName() {
        return email;
    }

    public int hashCode() {
        return id;
    }

    public User(int id, String email, String name, String profilePicture) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
