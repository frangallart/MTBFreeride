package com.norriors.java.mtbfreeride.Models;

import java.io.Serializable;

/**
 * Classe que modela una modalitat
 */
public class Modalitat implements Serializable {

    private long codi;
    private String titol;
    private String descripcio;
    private String imatge1;
    private String imatge2;
    private String url_video;

    /**
     * Constructor
     */
    public Modalitat() {
    }

    /**
     * Constructor amb paràmetres
     *
     * @param codi
     * @param titol
     * @param descripcio
     * @param imatge1
     * @param imatge2
     * @param url_video
     */
    public Modalitat(long codi, String titol, String descripcio, String imatge1, String imatge2, String url_video) {
        this.codi = codi;
        this.titol = titol;
        this.descripcio = descripcio;
        this.imatge1 = imatge1;
        this.imatge2 = imatge2;
        this.url_video = url_video;
    }

    /* Getter i Setters */
    public long getCodi() {
        return codi;
    }

    public void setCodi(long codi) {
        this.codi = codi;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getImatge1() {
        return imatge1;
    }

    public void setImatge1(String imatge1) {
        this.imatge1 = imatge1;
    }

    public String getImatge2() {
        return imatge2;
    }

    public void setImatge2(String imatge2) {
        this.imatge2 = imatge2;
    }

    public String getUrl_video() {
        return url_video;
    }

    public void setUrl_video(String url_video) {
        this.url_video = url_video;
    }
}