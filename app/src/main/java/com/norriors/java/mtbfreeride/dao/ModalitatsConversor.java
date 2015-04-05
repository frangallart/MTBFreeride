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
package com.norriors.java.mtbfreeride.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.norriors.java.mtbfreeride.Models.Modalitat;

import java.util.ArrayList;

/**
 * Classe conversora d'objectes Modalitat a BD
 * <p/>
 * Implementa els mètodes per recollir, editar, modificar i eliminar les dades que conte la base de dades.
 */
public class ModalitatsConversor {

    private ModalitatsSQLiteHelper helper;

    /**
     * Constructor
     */
    public ModalitatsConversor() {

    }

    /**
     * Constructor amb paràmetres
     *
     * @param helper
     */
    public ModalitatsConversor(ModalitatsSQLiteHelper helper) {
        this.helper = helper; // Permetrà obrir la base de dades
    }

    /**
     * Mètode que retorna totes les dades de la taula
     *
     * @return arraylist, llista que conté totes les dades
     */
    public ArrayList<Modalitat> getAllAsList() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true, "Modalitats", new String[]{"codi", "nom", "descripcio", "imatge1", "imatge2", "url_video"},
                null, null, null, null, null, null);

        ArrayList<Modalitat> dades = null;

        if (c.moveToFirst()) {
            dades = new ArrayList<Modalitat>();
            do {
                dades.add(new Modalitat(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
            } while (c.moveToNext());
        }

        db.close();
        // Retorna el contingut de la base de dades en un llista
        return dades;
    }

    /**
     * Desa un nou fact a la taula
     *
     * @param fact el fet que es vol desar
     * @return l'index del fet afegit
     */
   /* public long save(Modalitat fact) {
        long index = -1;
        // S'agafa l'objecte base de dades en mode escriptura
        SQLiteDatabase db = helper.getWritableDatabase();
        // es crea un objecte de diccionari (clau, valor) per indicar els valors a afegir
        ContentValues dades = new ContentValues();

        dades.put("title", fact.getTitle());
        dades.put("content", fact.getContent());
        dades.put("gif", fact.getGif());

        try {
            index = db.insertOrThrow("Facts", null, dades);
            // volem veure en el long el que passa
            Log.i("Facts", dades.toString() + " afegit amb el codi " + index);
        } catch (Exception e) {
            // volem reflectir en el log que hi ha agut un error
            Log.e("Facts", e.getMessage());
        }

        return index;
    }*/

    /**
     * Edita un fact de la taula
     *
     * @param fact el fet que es vol editar
     * @return l'index del fet editat
     */
    /*public long edit(Fact fact) {
        long index = -1;
        // S'agafa l'objecte base de dades en mode escriptura
        SQLiteDatabase db = helper.getWritableDatabase();
        // es crea un objecte de diccionari (clau, valor) per indicar els valors a afegir
        ContentValues dades = new ContentValues();

        dades.put("title", fact.getTitle());
        dades.put("content", fact.getContent());
        dades.put("gif", fact.getGif());
        try {
            index = db.update("Facts", dades, "codi =" + fact.getCodi(), null);
            // volem veure en el long el que passa
            Log.i("Facts", dades.toString() + " afegit amb el codi " + fact.getCodi());
        } catch (Exception e) {
            // volem reflectir en el log que hi ha agut un error
            Log.e("Facts", e.getMessage());
        }

        return index;
    }*/



   /* public Fact getByCodi(int codi) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(true, "Facts", new String[]{"codi", "title", "content", "gif"},
                "codi =" + codi, null, null, null, null, null);

        Fact f = null;

        if (c.getCount() > 0) {
            f = new Fact(c.getInt(c.getColumnIndex("codi")),
                    c.getString(c.getColumnIndex("title")),
                    c.getString(c.getColumnIndex("content")),
                    c.getString(c.getColumnIndex("gif")));
        }

        db.close();
        return f;
    }*/

    /*public ArrayList<Fact> geyByTitol(String titol) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(true, "Facts", new String[]{"codi", "title", "content", "gif"},
                "title like % = " + titol, null, null, null, null, null);

        ArrayList<Fact> llista = null;

        while (c.moveToNext()) {
            llista.add(new Fact(c.getInt(c.getColumnIndex("codi")),
                    c.getString(c.getColumnIndex("title")),
                    c.getString(c.getColumnIndex("content")),
                    c.getString(c.getColumnIndex("gif"))));
        }
        db.close();
        return llista;
    }*/

    /**
     * Esborra el fact passat per paràmetre
     */
   /* public boolean remove(Fact f) {
        // obtenir l'objecte BD en mode esriptura
        SQLiteDatabase db = helper.getWritableDatabase();
        // Taula, clau i where args
        return db.delete("Facts", "codi=" + f.getCodi(), null) > 0;
    }*/

    /**
     * Esborra tots els facts de la taula
     *
     * @return
     */
   /*public boolean removeAll() {
        // obtenir l'objecte BD en mode escriptura
        SQLiteDatabase db = helper.getWritableDatabase();

        return db.delete("Facts", null, null) > 0;
    }*/
}
