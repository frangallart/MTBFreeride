package com.norriors.java.mtbfreeride.Models;

/**
 * Classe UserVisites
 *
 * Classe que modela l'estructura de les dades que formen una visita d'usuari.
 */
public class UserVisites {

    private String user;
    private String img;
    private int total;
    private String data;

    public UserVisites(String user, String img, String data, String total) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
