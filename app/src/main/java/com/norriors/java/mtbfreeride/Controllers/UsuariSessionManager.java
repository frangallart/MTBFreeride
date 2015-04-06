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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * Classe UsuariSessionManager
 * <p/>
 * Classe que desa l'usuari en sessió; el seu nom em@il i imatge d'usuari.
 * També controla si hi ha algun usuari en sessió o no per dirigir-se a l'activitat de LoginUsuari
 * o anarl a l'activitat MainActivity
 */
public class UsuariSessionManager {

    // Shared Preferences referencia
    private SharedPreferences preferencies;

    // Editor referenciat a Shared preferences
    private Editor editor;

    // Context
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref nom del fitxer de preferències
    private static final String PREFER_NAME = "UserMTBFreeridePref";

    // (Clau per a validar si hi ha prefències desades) All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // User name (make variable public to access from outside)
    public static String KEY_REAL_NAME = "real_name";

    // User surname1 (make variable public to access from outside)
    public static String KEY_SURNAME1 = "surname1";

    // User surname2 (make variable public to access from outside)
    public static String KEY_SURNAME2 = "surname2";

    // User pass (make variable public to access from outside)
    public static String KEY_PASS = "pass";

    // Email address (make variable public to access from outside)
    public static String KEY_EMAIL = "email";

    // User image
    public static final String KEY_IMAGE = "image";

    /**
     * Constructor amb paràmetres
     *
     * @param context
     */
    public UsuariSessionManager(Context context) {
        this._context = context;
        preferencies = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = preferencies.edit();
    }

    /**
     * Crea el login de sessió
     * <p/>
     * Aquest mètode s'ha de crear al entrar com a un usuari vàlid
     *
     * @param name
     * @param cognom1
     * @param cognom2
     * @param pass
     * @param email
     * @param image
     */
    public void createUserLoginSession(String name, String realName, String cognom1, String cognom2, String pass, String email, String image) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing real name user
        editor.putString(KEY_REAL_NAME, realName);

        // Storing surname1 in pref
        editor.putString(KEY_SURNAME1, cognom1);

        // Storing surname2 in pref
        editor.putString(KEY_SURNAME2, cognom2);

        // Stroing pass in pref
        editor.putString(KEY_PASS, pass);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Stroing image in pref
        editor.putString(KEY_IMAGE, image);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginUsuari.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return false;
        }
        return true;
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, preferencies.getString(KEY_NAME, null));

        // user real name
        user.put(KEY_REAL_NAME, preferencies.getString(KEY_REAL_NAME, null));

        // user surname1
        user.put(KEY_SURNAME1, preferencies.getString(KEY_SURNAME1, null));

        // user surname2
        user.put(KEY_SURNAME2, preferencies.getString(KEY_SURNAME2, null));

        // user pass
        user.put(KEY_PASS, preferencies.getString(KEY_PASS, null));

        // user email
        user.put(KEY_EMAIL, preferencies.getString(KEY_EMAIL, null));

        // user image
        user.put(KEY_IMAGE, preferencies.getString(KEY_IMAGE, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginUsuari.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Check for login
     *
     * @return true si l'usuari està loggejat, false en cas contrari
     */
    public boolean isUserLoggedIn() {
        return preferencies.getBoolean(IS_USER_LOGIN, false);
    }
}
