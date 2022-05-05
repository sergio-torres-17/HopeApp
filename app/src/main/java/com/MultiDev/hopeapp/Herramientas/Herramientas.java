package com.MultiDev.hopeapp.Herramientas;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Herramientas {
    public static String convertirBinImagen(Bitmap imagen){
        String dev = "";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imagenByte = stream.toByteArray();
        dev = Base64.encodeToString(imagenByte, Base64.DEFAULT);
        return dev;
    }
}
