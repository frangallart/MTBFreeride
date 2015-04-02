package com.norriors.java.mtbfreeride.Controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Classe ImageTool
 */
public class ImageTool {

    public ImageTool(){

    }

    public Bitmap convertImageToByte(String path){
        return decodeSampledBitmapFromFile(path, 300, 300);
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getBitmap(String img) {
        try {
            byte[] byteData = Base64.decode(img, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(byteData, 0, byteData.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Mètode que rep una imatge en Bitmap i ens retorna un String per poder passar-lo al JSON
     * @param image
     * @return
     */
    public String getImageString(Bitmap image){

        byte[] byteArray;
        // Passem la imatge a una cadena de bytes
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byteArray = stream.toByteArray();

       return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
