package com.networkH2021.gachon;

public class Person {
    private String name;
    private String id;
    private char[] Password;
    private String email;

    public Person(String name, String id, char[] password, String email) {
        this.name = name;
        this.id = id;
        Password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char[] getPassword() {
        return Password;
    }

    public void setPassword(char[] password) {
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
