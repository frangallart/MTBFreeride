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
        insertModalitats.bindString(5, "http://r2---sn-h5q7enek.googlevideo.com/videoplayback?ratebypass=yes&upn=5rLyr7bylok&ipbits=0&ip=79.158.60.6&mm=31&id=o-ANeSsswBfVxeNAPQnHne4WS_uge0ZUtT4kqCSOO33BhC&mime=video%2Fmp4&pl=23&sver=3&ms=au&mt=1428188400&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmime%2Cmm%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&mv=m&itag=18&signature=DD41EE3D86678E1D4F14DCC95602028F0B7DE380.327CFFF768E24131610C4C6A48F7FFE5264E1132&expire=1428210107&key=yt5&fexp=900720%2C905024%2C907263%2C924638%2C924648%2C934954%2C936111%2C9406543%2C9407432%2C9408101%2C947243%2C948124%2C948703%2C951703%2C952009%2C952612%2C957201%2C961404%2C961406%2C964605%2C965200%2C966201&nh=IgpwcjAyLm1hZDAxKgkxMjcuMC4wLjE&dur=326.727&source=youtube&initcwndbps=1038750&title=Mountain%20Biking%20the%20North%20Shore%20B.C%20--%20The%20Classics");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Dirt Jump");
        insertModalitats.bindString(2, "Aquesta modalitat consisteix en diferents salts de terra, i fer el major número de trucs en cada salt.\n" + "\n" + "A la foto es pot veure un truc que se n'hi diu l'indian.\n" + "\n" + "Hi ha infinitat de combinacions de trucs, casibé s'han inventat tots i per fer-ho més difícil convinen vàris trucs en un mateix salt.");
        insertModalitats.bindString(3, "ImatgesModalitats/idirtJump.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/idirtJump2.jpg");
        insertModalitats.bindString(5, "http://r13---sn-h5q7dn7k.googlevideo.com/videoplayback?sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmime%2Cmm%2Cms%2Cmv%2Cnh%2Cpl%2Csource%2Cupn%2Cexpire&mt=1428189254&itag=36&ip=79.158.60.6&source=youtube&mime=video%2F3gpp&sver=3&ms=au&nh=IgpwcjAyLm1hZDAxKgkxMjcuMC4wLjE&mv=m&ipbits=0&upn=_ZZU6bSQMZ8&expire=1428210941&dur=403.516&id=o-AKVVUip2kGsqTVaP01VX6HRsukPqKe7MUxGSLYC38lus&pl=23&fexp=900720%2C907263%2C934954%2C9406616%2C9406843%2C9407756%2C9408101%2C947243%2C948124%2C948703%2C951703%2C952612%2C957201%2C961404%2C961406%2C966201&mm=31&signature=7CCCA6C985E49F50F8F3367EA6A7EBF077ECA05A.10CC5EF04F5CA58F6E24B689752AF9AD157EC418&key=yt5&initcwndbps=1058750&title=Mountain%20Bike%20Dirt%20Jumping%20-%20Aptos%2C%20California");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Street");
        insertModalitats.bindString(2, "En aquesta modalitat s'han d'anar superant els diferents obstacles urbans.\n" + "\n" + "És una modalitat molt complicada que necessita molta pràctica. La majoria de la gent no la té gaire ben vista i moltes vegades és sancionada per la policia, amb multes.");
        insertModalitats.bindString(3, "ImatgesModalitats/idirtJump2.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/idirtJump2.jpg");
        insertModalitats.bindString(5, "http://r11---sn-h5q7dn7e.googlevideo.com/videoplayback?upn=e3Pf1CigGqo&mm=31&pl=23&id=o-AOsenVJ2GBmPh5dffyITyJUHn1p9qezSVVw4uruG8bkp&dur=330.001&initcwndbps=1066250&mime=video%2F3gpp&ipbits=0&sver=3&ip=79.158.60.6&key=yt5&itag=36&fexp=900720%2C900805%2C902904%2C907263%2C919155%2C934954%2C936117%2C9406001%2C9406090%2C9407578%2C9408006%2C9408101%2C9408207%2C9408385%2C945074%2C946800%2C947243%2C948124%2C948703%2C951703%2C952612%2C957201%2C961404%2C961406%2C966201&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmime%2Cmm%2Cms%2Cmv%2Cnh%2Cpl%2Csource%2Cupn%2Cexpire&mv=m&expire=1428211295&source=youtube&mt=1428189668&ms=au&nh=IgpwcjAyLm1hZDAxKgkxMjcuMC4wLjE&title=MTB%3A%20Unexpected%20Thursday%2036%20-%20The%20Rise");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Big Air");
        insertModalitats.bindString(2, "És com el dirt jump però amb salts molt més grans, de 10 metres cap amunt.\n" + "\n" + "És la modalitat que es fa més perillosa de totes per culpa de les grans distàncies que es recorren fent aquests salts.");
        insertModalitats.bindString(3, "ImatgesModalitats/ibigAir.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/ibigAir2.jpg");
        insertModalitats.bindString(5, "http://r7---sn-h5q7dn7e.googlevideo.com/videoplayback?mv=m&source=youtube&ms=au&nh=IgpwcjAyLm1hZDAxKgkxMjcuMC4wLjE&sver=3&key=yt5&ip=79.158.60.6&itag=36&expire=1428211643&initcwndbps=1071250&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmime%2Cmm%2Cms%2Cmv%2Cnh%2Cpl%2Csource%2Cupn%2Cexpire&mt=1428190006&fexp=900720%2C907263%2C934954%2C936117%2C9406038%2C9406141%2C9407441%2C9407455%2C9408101%2C9408249%2C947243%2C948124%2C948703%2C951703%2C952612%2C957201%2C961404%2C961406%2C966201&mime=video%2F3gpp&ipbits=0&upn=Tdt4BYC3Vko&mm=31&signature=8D7A63795DD63F7406A4C66892A833C2FC111C82.DF9C1A4DCD6A9DFE175BD57E2E9E42F6AD912101&pl=23&dur=108.483&id=o-AIRva7HPCqDQW9Dz4LwdVyJoAbs4qigZLCpgEC567czS&title=Big%20Air%20MTB%20Slopestyle%20Competition%20-%20Martin%20S%C3%B6derstr%C3%B6m%20Invitational%202014");
        insertModalitats.executeInsert();

        insertModalitats.clearBindings();
        insertModalitats.bindString(1, "Park");
        insertModalitats.bindString(2, "Aquesta modalitat és derivada del BMX i tracta de moure's en diferents estructures de ciment, fusta o metall.\n" + "\n" + "Aquesta modalitat és practica menys que les altres, ja que és té que tenir molta capacitat de maniobra, com per exemple la que et proporciona una BMX, i amb bicis de mountain bike és fa més difícil moure's per skate parks.");
        insertModalitats.bindString(3, "ImatgesModalitats/ipark.jpg");
        insertModalitats.bindString(4, "ImatgesModalitats/ipark2.jpg");
        insertModalitats.bindString(5, "http://r12---sn-h5q7dn7l.googlevideo.com/videoplayback?mime=video%2F3gpp&ip=79.158.60.6&fexp=900720%2C907263%2C913445%2C916732%2C934954%2C9406615%2C9407921%2C9408093%2C9408101%2C9408249%2C947243%2C947258%2C948124%2C948703%2C951703%2C952612%2C957201%2C961000%2C961404%2C961406%2C966201&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Cmime%2Cmm%2Cms%2Cmv%2Cnh%2Cpl%2Csource%2Cupn%2Cexpire&upn=Hcm3er0rBYY&mt=1428190221&mm=31&signature=E67986587CF21755B108E5F23A155635B8F7B0F2.F3BA440BCC6836DDD8FCD3881CF6AFDDC5F28989&itag=36&initcwndbps=1072500&nh=IgpwcjAyLm1hZDAxKgkxMjcuMC4wLjE&pl=23&mv=m&id=o-ACDXWw1LoxpMsR8OSnF4cyjOPzjTqOoK5HwsY5Y2kOjN&ms=au&key=yt5&source=youtube&expire=1428211866&dur=212.369&sver=3&ipbits=0&title=Slopestyle%20MTB%20training%20at%20Highland%20Mountain%20Bike%20Park");
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
