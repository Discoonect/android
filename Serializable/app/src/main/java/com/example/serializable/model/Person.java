package com.example.serializable.model;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private String email;
    private boolean isMale;

    public Person(){

    }

    public Person(String name, String email, boolean isMale) {
        this.name = name;
        this.email = email;
        this.isMale = isMale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}
