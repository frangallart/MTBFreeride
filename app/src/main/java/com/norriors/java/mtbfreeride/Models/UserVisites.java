package com.norriors.java.mtbfreeride.Models;

import java.util.Date;

/**
 * Created by Ruben on 28/3/15.
 */
public class UserVisites {

    private String user;
    private String img;
    private int total;
    private Date data;

    public UserVisites(String user, String img, Date data, String total) {
        this.user = user;
        this.img = img;
        this.data = data;
        this.total = Integer.parseInt(total);

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
