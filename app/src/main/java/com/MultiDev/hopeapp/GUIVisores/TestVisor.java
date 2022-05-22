package com.MultiDev.hopeapp.GUIVisores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.MultiDev.hopeapp.Objetos.AdaptadorTarjeta;
import com.MultiDev.hopeapp.Objetos.Paciente;
import com.MultiDev.hopeapp.Objetos.Usuario;
import com.MultiDev.hopeapp.R;

import java.util.ArrayList;
import java.util.List;

public class TestVisor extends AppCompatActivity {
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_visor);
        this.rv = findViewById(R.id.rvTestPer);
        rv.setLayoutManager(new LinearLayoutManager(TestVisor.this));
        AdaptadorTarjeta tarjeta = new AdaptadorTarjeta(getPacientes(), getApplicationContext());
        rv.setAdapter(tarjeta);
    }
    private List<Paciente> getPacientes(){
        List<Paciente> dev = new ArrayList<>();
        dev.add(new Paciente(new Usuario("Elizabeth","Olsen",33), "Radiante","Belleza"));
        return dev;
    }
}