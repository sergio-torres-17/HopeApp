package com.MultiDev.hopeapp.Herramientas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Herramientas {
    public static String convertirBinImagen(Bitmap imagen){
        String dev = "";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imagenByte = stream.toByteArray();
        dev = Base64.encodeToString(imagenByte, Base64.DEFAULT);
        return dev;
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL("http://multicodemx.com/hopeapp/DEV/Request/Archivos/CA_SergioTorres/Fotos/FP_SergioTorres.png");
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            System.err.println("Error "+e.getMessage());
            return null;
        }
    }
    public static String fechaActual(){
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        return date;
    }
    public static Bitmap base64ToImage(String stream){
        Bitmap bitmap = null;
        try{
            byte[] bitmapArray = Base64.decode(stream, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray,0, bitmapArray.length);

        }catch(Exception e){
            System.out.println("Error al descifrar la imagen "+e.getMessage());
        }
        return bitmap;
    }
}
