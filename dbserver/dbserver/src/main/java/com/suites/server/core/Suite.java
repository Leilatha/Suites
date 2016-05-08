package com.suites.server.core;

public class Suite {
    private final int id;
    private final String name;

    public Suite(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getString() {
        return name;
    }
}
