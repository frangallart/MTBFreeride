/*************************************************************************************
 *                                                                                   *
 *  MTB FreeRide por Java Norriors se distribuye bajo una                            *
 *  Licencia Creative Commons Atribución-NoComercial-SinDerivar 4.0 Internacional.   *
 *                                                                                   *
 *  http://creativecommons.org/licenses/by-nc-nd/4.0/                                *
 *                                                                                   *
 *  @author: Arnau Roma Vidal  - aroma@infoboscoma.net                               *
 *  @author: Rubén Garcia Torres - rgarcia@infobosccoma.net                          *
 *  @author: Francesc Gallart Vila - fgallart@infobosccoma.net                       *
 *                                                                                   *
/************************************************************************************/
package com.norriors.java.mtbfreeride.Models;

/**
 * Classe UserVisites
 * <p/>
 * Classe que modela l'estructura de les dades que formen una visita d'usuari.
 */
public class UserVisites {

    private String user;
    private String img;
    private int numVisites;
    private String data;

    public UserVisites(String user, String img, String data, String numVisites) {
        this.user = user;
        this.img = img;
        this.data = data;
        this.numVisites = Integer.parseInt(numVisites);
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

    public int getNumVisites() {
        return numVisites;
    }

    public void setNumVisites(int total) {
        this.numVisites = total;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
