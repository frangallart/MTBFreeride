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

import android.widget.ToggleButton;

/**
 * Classe DrawerItems
 */
public class DrawerItems {

	private String title;
    private String usuari;
    private MLRoundedImageView imgUser;
    private ToggleButton so;

	public DrawerItems(String title) {
		this.title = title;
	}

    public DrawerItems(MLRoundedImageView img){
        this.imgUser = img;
    }

    public DrawerItems(ToggleButton so){
        this.so = so;
    }

    public ToggleButton getSo() {
        return so;
    }

    public void setSo(ToggleButton so) {
        this.so = so;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
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
