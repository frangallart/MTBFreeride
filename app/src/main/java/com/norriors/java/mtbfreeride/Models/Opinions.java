package com.norriors.java.mtbfreeride.Models;

/**
 * Created by Arnau on 02/04/2015.
 */
public class Opinions {

    private String user;
    private String data;

    public Opinions(String user, String data) {
        this.user = user;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
