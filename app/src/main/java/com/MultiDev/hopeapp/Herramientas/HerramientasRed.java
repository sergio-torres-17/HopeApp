package com.MultiDev.hopeapp.Herramientas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;

import java.io.IOException;
import java.net.InetAddress;

public class HerramientasRed {
    public static boolean execPing(String host) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8" + host);
            int exitValue = ipProcess.waitFor();
            System.out.println("Exit value "+exitValue);
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean validarConexionAInternet(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return  isConnected;
    }
    public static boolean validarConexionServer(){
        System.out.println("Respuesta de ping server: "+execPing("multicodemx.com"));
        return true;
    }

}
