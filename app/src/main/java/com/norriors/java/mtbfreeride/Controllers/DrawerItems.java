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
