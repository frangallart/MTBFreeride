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
        insertModalitats.bindString(2, "Es tracta d'una sèrie d'estructures col·locades a certa distància del terra.\n" + "\n" + "Aquesta modalitat es practica normalment en el bosc.\n" + "\n" + "No és gaire popular aquí Catalunya ni Espanya, però és una modalitat que es practica molt al Nord d'Amèrica i al Canada.");
        insertModalitats.bindString(3, "ImatgesModalitats/inorthShore.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/inorthShore2.jpg");
        insertModalitats.bindString(5, "http://r2---sn-h5q7enek.googlevideo.com/videoplayback?ratebypass=yes&upn=5rLyr7bylok&ipbits=0&ip=79.158.60.6&mm=31&id=o-ANeSsswBfVxeNAPQnHne4WS_uge0ZUtT4kqCSOO33BhC&mime=video%2Fmp4&pl=23&sver=3&ms=au&mt=1428188400&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmime%2Cmm%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&mv=m&itag=18&signature=DD41EE3D86678E1D4F14DCC95602028F0B7DE380.327CFFF768E24131610C4C6A48F7FFE5264E1132&expire=1428210107&key=yt5&fexp=900720%2C905024%2C907263%2C924638%2C924648%2C934954%2C936111%2C9406543%2C9407432%2C9408101%2C947243%2C948124%2C948703%2C951703%2C952009%2C952612%2C957201%2C961404%2C961406%2C964605%2C965200%2C966201&nh=IgpwcjAyLm1hZDAxKgkxMjcuMC4wLjE&dur=326.727&source=youtube&initcwndbps=1038750&title=Mountain%20Biking%20the%20North%20Shore%20B.C%20--%20The%20Classics");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Dirt Jump");
        insertModalitats.bindString(2, "Aquesta modalitat consisteix en diferents salts de terra, i fer el major número de trucs en cada salt.\n" + "\n" + "A la foto es pot veure un truc que se n'hi diu l'indian.\n" + "\n" + "Hi ha infinitat de combinacions de trucs, casibé s'han inventat tots i per fer-ho més difícil convinen vàris trucs en un mateix salt.");
        insertModalitats.bindString(3, "ImatgesModalitats/idirtJump.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/idirtJump2.jpg");
        insertModalitats.bindString(5, "http://r13---sn-h5q7dn7k.googlevideo.com/videoplayback?sver=3&itag=18&pl=23&expire=1428224961&mm=31&signature=A9DBFB9BDA6A98F817E3F0E299773FBB0AA4C7A1.51EF64C47DB9E0EB6C6260F9930820B4512A8C71&upn=kTy11nZ6DNU&mime=video%2Fmp4&initcwndbps=1223750&nh=IgpwcjAyLm1hZDAxKgkxMjcuMC4wLjE&ratebypass=yes&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmime%2Cmm%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&ipbits=0&ip=79.158.60.6&key=yt5&fexp=900720%2C905648%2C907263%2C916727%2C931392%2C934954%2C9406012%2C9407647%2C9407881%2C9407908%2C9408101%2C9408345%2C941805%2C947243%2C948124%2C948703%2C951703%2C952612%2C957201%2C961404%2C961406%2C964605%2C966201&source=youtube&mv=m&ms=au&mt=1428203321&id=o-AKWYvjcUcq9BjHxqnQqQD_hpqyOpRlh84biF7v4hxQ7F&dur=403.377&title=Mountain%20Bike%20Dirt%20Jumping%20-%20Aptos%2C%20California");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Street");
        insertModalitats.bindString(2, "En aquesta modalitat s'han d'anar superant els diferents obstacles urbans.\n" + "\n" + "És una modalitat molt complicada que necessita molta pràctica. La majoria de la gent no la té gaire ben vista i moltes vegades és sancionada per la policia, amb multes.");
        insertModalitats.bindString(3, "ImatgesModalitats/istreet.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/istreet2.jpg");
        insertModalitats.bindString(5, "http://r6---sn-h5q7dnee.googlevideo.com/videoplayback?id=o-ADm-WU83J1-WzoHAiNhmTmcYXUZAwp0AHgQBTScGFXVO&mm=31&initcwndbps=1181250&ip=79.158.60.6&signature=5BC980C1A00E66FB263BBA796B441DAF8497708D.D41BD51063D177E5C7C498DB5E485B6BDACCDD2A&ms=au&mt=1428202633&mv=m&pl=23&upn=Xvrv4SrT4Oo&ipbits=0&sver=3&expire=1428224296&itag=18&ratebypass=yes&dur=191.912&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmime%2Cmm%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&mime=video%2Fmp4&key=yt5&nh=IgpwcjAyLm1hZDAxKgkxMjcuMC4wLjE&fexp=900720%2C907263%2C934954%2C936118%2C9406963%2C9407721%2C9408102%2C9408144%2C9408755%2C947243%2C948124%2C948703%2C951703%2C952612%2C957201%2C961404%2C961406%2C966201&source=youtube&title=MTB%20Street%3A%20Unexpected%20Thursday%2038%20-%20The%20Rise");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Big Air");
        insertModalitats.bindString(2, "És com el dirt jump però amb salts molt més grans, de 10 metres cap amunt.\n" + "\n" + "És la modalitat que es fa més perillosa de totes per culpa de les grans distàncies que es recorren fent aquests salts.");
        insertModalitats.bindString(3, "ImatgesModalitats/ibigAir.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/ibigAir2.jpg");
        insertModalitats.bindString(5, "http://r7---sn-h5q7dn7e.googlevideo.com/videoplayback?initcwndbps=1168750&expire=1428224525&sver=3&key=yt5&itag=18&ip=79.158.60.6&mm=31&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmime%2Cmm%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&mv=m&pl=23&mt=1428202832&ms=au&mime=video%2Fmp4&dur=108.344&id=o-AB577INPdzgXKmmeMuMcnabnRLwNB1iB145HxGHyn6sa&ipbits=0&fexp=900720%2C907263%2C934954%2C9407429%2C9407667%2C9408102%2C9408267%2C947243%2C948124%2C948703%2C951703%2C952612%2C957201%2C961404%2C961406%2C966201&signature=CB04BDC4FB4BBB9A0CAC5478688F6332CB3AF820.F96216D352EF6F0CE18B0D2D8805706559A28280&source=youtube&ratebypass=yes&nh=IgpwcjAyLm1hZDAxKgkxMjcuMC4wLjE&upn=xb4zudvERig&title=Big%20Air%20MTB%20Slopestyle%20Competition%20-%20Martin%20S%C3%B6derstr%C3%B6m%20Invitational%202014");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Park");
        insertModalitats.bindString(2, "Aquesta modalitat és derivada del BMX i tracta de moure's en diferents estructures de ciment, fusta o metall.\n" + "\n" + "Aquesta modalitat és practica menys que les altres, ja que és té que tenir molta capacitat de maniobra, com per exemple la que et proporciona una BMX, i amb bicis de mountain bike és fa més difícil moure's per skate parks.");
        insertModalitats.bindString(3, "ImatgesModalitats/ipark.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/ipark2.jpg");
        insertModalitats.bindString(5, "http://r12---sn-h5q7dn7l.googlevideo.com/videoplayback?id=o-AIgrFHKngOpM-YQVZE4wvBlUN5ZSN7jHOK9yhaJsC49e&mm=31&ms=au&mt=1428203030&mv=m&pl=23&ip=79.158.60.6&initcwndbps=1220000&ipbits=0&mime=video%2Fmp4&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmime%2Cmm%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&key=yt5&signature=F7F9836BF1BBAC1C198705CA36BF9839BD60C535.7FBBB7F8A5E9B2B5B816405603E8C8BB90B26522&nh=IgpwcjAyLm1hZDAxKgkxMjcuMC4wLjE&fexp=900720%2C907263%2C934954%2C9406736%2C9407575%2C9407798%2C9408101%2C947243%2C948124%2C948703%2C951703%2C952612%2C957201%2C961404%2C961406%2C966201&source=youtube&dur=212.300&ratebypass=yes&itag=18&sver=3&upn=8PZdj3t0_pg&expire=1428224664&title=Slopestyle%20MTB%20training%20at%20Highland%20Mountain%20Bike%20Park");
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
