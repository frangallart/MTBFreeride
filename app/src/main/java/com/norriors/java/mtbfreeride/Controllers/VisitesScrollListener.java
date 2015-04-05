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

import android.widget.AbsListView;

/**
 * Classe VisitesScrollListener
 *
 * Aquesta classe comprova el moviment de l'scroll per tal d'executar una nova
 * càrrega d'usuaris a la llista de visites
 *
 */

public abstract class VisitesScrollListener implements AbsListView.OnScrollListener {
    // Mínim d'items a tenir per sota de la posició actual de l'scroll abans de carregar-ne més
    private int visbleSota = 1;
    // Total d'items després de la última càrrega
    private int totalItems = 0;
    // True si se segueix esperant que acabi de carrega l'últim grup d'usuaris sol·licitat
    private boolean carregant = true;

    public VisitesScrollListener() {
        this.visbleSota = 1;
        this.totalItems = 0;
    }

    /**
     * Mètode que comprova l'estat de l'scroll
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        // Si no hi ha cap càrrega en execució.
        if (!carregant && (totalItemCount < totalItems)) {
            this.totalItems = totalItemCount;
            if (totalItemCount == 0) {
                this.carregant = true;
            }
        }

        // Si s'està executant una càrrega de dades
        if (carregant) {
            // Es comprova si el total d'items és més gran que el número
            // d'items carregats. Si és més gran vol dir que la càrrega
            // ja acabat.
            if (totalItemCount > totalItems) {
                carregant = false;
                totalItems = totalItemCount;
            }
        }

        // Si hem arribat al final de la llista i una càrrega de dades no està en procés,
        // invoco el mètode carregaDades per poder carrega més usuaris.
        if (!carregant
                && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visbleSota)) {
            carregaDades();
            carregant = true;
        }
    }

    public abstract void carregaDades();

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }
}
