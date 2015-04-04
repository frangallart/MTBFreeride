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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Classe ModalitatsSQLiteHelper
 * <p/>
 * Classe que estén les funcionalitats de SQLiteOpenHelper.
 */
public class ModalitatsSQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    private String nomBD;
    private CursorFactory factory;
    private int versio;

    // Sentència SQL per crear la taula de Modalitats
    private final String SQL_CREATE_MODALITATS = "CREATE TABLE Modalitats (" +
            " codi INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            " nom TEXT," +
            " descripcio TEXT," +
            " imatge1 TEXT," +
            " imatge2 TEXT," +
            " url_video TEXT);";


    /**
     * Constructor amb paràmetres
     *
     * @param context context de l'aplicació
     * @param nomBD   nom de la base de dades
     * @param factory cursor o null
     * @param versio  versió de la base de dades. Si és més gran que l'actual es farà un Upgrade;
     *                si és més petita es faraà un Downgrade
     */
    public ModalitatsSQLiteHelper(Context context, String nomBD, CursorFactory factory, int versio) {
        super(context, nomBD, factory, versio);
        this.context = context;
        this.nomBD = nomBD;
        this.factory = factory;
        this.versio = versio;
    }

    /**
     * Event que es produeix quan s'ha de crear la BD
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // S'execcuten les sentències SQL de creació de la BD
        db.execSQL(SQL_CREATE_MODALITATS);
        executaInsertsModalitas(db);
    }

    /**
     * Mètode que ens permet introduir les dades a la base de dades
     */
    public void executaInsertsModalitas(SQLiteDatabase db) {

        String sgl_insert = "INSERT INTO modalitats (nom, descripcio, imatge1, imatge2, url_video)" +
                " VALUES(?,?,?,?,?)";
        SQLiteStatement insertModalitats = db.compileStatement(sgl_insert);

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "North Shore");
        insertModalitats.bindString(2, "Es tracta d'una sèrie d'estructures col·locades a certa distància del terra.\n" + "\n" + "Aquesta modalitat es practica normalment en el bosc.\n" + "\n" + "No és gaire popular aquí Catalunya,( n'hi Espanya), però és una modalitat que es practica molt al Nord d'Amèrica i al Canada.");
        insertModalitats.bindString(3, "ImatgesModalitats/inorthShore.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/inorthShore2.jpg");
        insertModalitats.bindString(5, "http://techslides.com/demos/sample-videos/small.mp4");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Dirt Jump");
        insertModalitats.bindString(2, "Aquesta modalitat consisteix en diferents salts de terra, i fer el major número de trucs en cada salt.\n" + "\n" + "A la foto es pot veure un truc que se n'hi diu l'indian.\n" + "\n" + "Hi ha infinitat de conbinacions de trucs, casibé s'han inventat tots i per fer-ho més difícil convinen vàris trucs en un mateix salt.");
        insertModalitats.bindString(3, "ImatgesModalitats/idirtJump.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/idirtJump2.jpg");
        insertModalitats.bindString(5, "http://techslides.com/demos/sample-videos/small.mp4");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Street");
        insertModalitats.bindString(2, "En aquesta modalitat s'han d'anar superant els diferents obstacles urbans.\n" + "\n" + "És una modalitat molt complicada que necessita molta pràctica. La majoria de la gent no la té gaire ben vista i moltes vegades es sancionada per la policia, amb multes.");
        insertModalitats.bindString(3, "ImatgesModalitats/idirtJump2.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/idirtJump2.jpg");
        insertModalitats.bindString(5, "http://techslides.com/demos/sample-videos/small.mp4");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Big Air");
        insertModalitats.bindString(2, "És com el dirt jump però amb salts molt més grans, de 10 metres cap amunt.\n" + "\n" + "És la modalitat que es fa més perillosa de totes per culpa de les grans distàncies que es recorren fent aquests salts.");
        insertModalitats.bindString(3, "ImatgesModalitats/ibigAir.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/ibigAir2.jpg");
        insertModalitats.bindString(5, "http://techslides.com/demos/sample-videos/small.mp4");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Park");
        insertModalitats.bindString(2, "Aquesta modalitat és derivada del BMX i tracta de moure's en diferents estructures de ciment, fusta o metall.\n" + "\n" + "Aquesta modalitat és practica menys que les altres, ja que és té que tenir molta capacitat de maniobra, com per exemple la que et proporciona una BMX, i amb bicis de mountain bike és fà més difícil moure's per skate parks.");
        insertModalitats.bindString(3, "ImatgesModalitats/ipark.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/ipark2.jpg");
        insertModalitats.bindString(5, "http://techslides.com/demos/sample-videos/small.mp4");
        insertModalitats.executeInsert();
    }

    /**
     * Event que es produeix quan s'ha d'actualitzar la BD a una versió superior
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
