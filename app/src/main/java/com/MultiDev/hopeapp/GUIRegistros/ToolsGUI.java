package com.MultiDev.hopeapp.GUIRegistros;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

public class ToolsGUI {
    public static void abrirCamara(AppCompatActivity compatActivity){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        compatActivity.startActivityForResult(intent,1);
    }
}
