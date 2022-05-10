package com.MultiDev.hopeapp.WebService.ToolsFirebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireDb {
    private DatabaseReference reference;
    private String doctor;

    public FireDb(String doctor) {
        this.reference = FirebaseDatabase.getInstance().getReference("RegSintomas");
        this.doctor = doctor;
    }

    public void crearColeccionDoctor() {
        this.reference.child("doctores").setValue(this.doctor);
        this.reference.child("doctores").child(this.doctor).child("AlertasRegSintomas").setValue("");
    }
}
