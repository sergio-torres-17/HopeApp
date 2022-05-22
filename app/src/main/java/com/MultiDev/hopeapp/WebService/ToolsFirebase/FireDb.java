package com.MultiDev.hopeapp.WebService.ToolsFirebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireDb {
    private DatabaseReference reference;
    private String doctor;

    public FireDb(String doctor) {
        this.reference = FirebaseDatabase.getInstance().getReference("RegSintomas");
        this.doctor = doctor;
    }

    public void crearColeccionDoctor() {
        this.reference.child("doctores").child(this.doctor);
        this.reference.child("doctores").child(this.doctor).child("AlertasRegSintomas");
    }
    public void leerDatos(ValueEventListener escuchador){
        this.reference.child("doctores").child(this.doctor).child("AlertasRegSintomas").addValueEventListener(escuchador);
    }
    public void insertarSintoma(String sintoma, String paciente){
        String notificacion = "El paciente "+paciente+" insertó un nuevo sintoma";
        this.reference.child("doctores").child(this.doctor).child("AlertasRegSintomas").setValue(notificacion);
    }
}
