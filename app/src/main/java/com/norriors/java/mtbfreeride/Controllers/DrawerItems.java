package com.norriors.java.mtbfreeride.Controllers;

/**
 * Classe DrawerItems
 */
public class DrawerItems {

	String title;
    String usuari;

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    MLRoundedImageView imgUser;

	public DrawerItems(String title) {
		this.title = title;
	}

    public DrawerItems(String title, int usuari) {
        this.usuari = title;
    }

    public DrawerItems(MLRoundedImageView img){
        this.imgUser = img;
    }

    public void setImgUser(MLRoundedImageView imgUser) {
        this.imgUser = imgUser;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    public MLRoundedImageView getImgUser() {
        return imgUser;
    }
}
