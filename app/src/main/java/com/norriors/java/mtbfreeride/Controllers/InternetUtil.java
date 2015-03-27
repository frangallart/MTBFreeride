package com.norriors.java.mtbfreeride.Controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Classe InternetUtil
 * <p/>
 * Classe que implementa mètodes estàtics per controlar si el dispositiu té connexiò a Internet
 */
public class InternetUtil {

    /**
     * Mètode que controla si el dispositiu té connexiò a la xarxa
     *
     * @return true en cas afirmatiu, false en cas contrari
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Mètode que mostra un missatge d'alerta si no hi ha connexió a internet
     *
     * @param context activity on es mostrarà
     * @param title   títol del dialeg
     * @param message missatge del dialeg
     */
    public static void showAlertDialog(final Context context, String title, String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.setButton(-1, "Obre les opcions d'internet", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        });

        alertDialog.setButton(-2, "Cancel·lar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.closeOptionsMenu();
                ((Activity) (context)).finish();
            }
        });

        alertDialog.show();
    }
}
