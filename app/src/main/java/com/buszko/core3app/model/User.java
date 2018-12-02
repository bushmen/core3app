package com.buszko.core3app.model;

public class User {
    //I have deserialized only couple of fields, as I don't need all of them.
    private String id;
    private String name;
    private String email;
    private String phone;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
