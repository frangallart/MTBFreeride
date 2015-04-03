package com.norriors.java.mtbfreeride.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Classe User
 * <p/>
 * Classe que modela un usuari de l'aplicació.
 */
public class User {

    private int id;
    private String user, password, nom, cognom1, cognom2, email;
    private String img;
    private Bitmap photo;

    /**
     * Constructor per defecte
     */
    public User() {

    }

    /**
     * Constructor amb paràmetres
     *
     * @param id       id de l'usuari
     * @param user     nom de l'usuari
     * @param password contrasenya de l'usuari
     * @param img      imatge de l'usuari
     * @param nom      nom de l'usuari
     * @param cognom1  primer cognom de l'usuari
     * @param cognom2  segon cognom de l'usuari
     * @param email    correu electrònic de l'usuari
     */
    public User(int id, String user, String password, String img, String nom, String cognom1, String cognom2, String email) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.img = img;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.email = email;
    }

    /**
     * Getter i Setters
     */
    public int getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
        try {
            byte[] byteData = Base64.decode(img, Base64.DEFAULT);
            this.photo = BitmapFactory.decodeByteArray(byteData, 0, byteData.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCognom1() {
        return cognom1;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
