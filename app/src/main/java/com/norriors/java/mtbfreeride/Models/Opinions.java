package com.norriors.java.mtbfreeride.Models;

/**
 * Created by Arnau on 02/04/2015.
 */
public class Opinions {

    private int id;
    private String user;
    private String data;
    private String so;


    public Opinions (String so){
        this.so = so;

    }
    public Opinions(int id, String user, String data) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }
}
