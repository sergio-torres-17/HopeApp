package com.MultiDev.hopeapp.Herramientas;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.MultiDev.hopeapp.GUIRegistros.Pacientes.RegistroSintomas;
import com.MultiDev.hopeapp.MainActivity;
import com.MultiDev.hopeapp.R;
import com.MultiDev.hopeapp.WebService.ToolsFirebase.FireDb;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ServicioNotificacion extends Service {
    private FireDb fire;
    private String nombreDoctorCompleto;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        nombreDoctorCompleto = intent.getExtras().getString("nombreCompleto");
        fire = new FireDb(this.nombreDoctorCompleto);
        fire.leerDatos(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!String.valueOf(snapshot.getValue()).isEmpty() &&snapshot.getValue() !=null){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Intent intent_ = new Intent(getApplicationContext(), RegistroSintomas.class);
                        intent_.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "cnl01");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle("Nuevo sintoma registrado");
                        builder.setContentText(String.valueOf(snapshot.getValue()));
                        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        builder.setVibrate(new long[] {100, 250, 100, 500});
                        builder.setStyle(new NotificationCompat.BigTextStyle());
                        builder.setAutoCancel(true);
                        builder.setContentIntent(pendingIntent);
                        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                        NotificationChannel channel = new NotificationChannel("cnl01", "Micanal", NotificationManager.IMPORTANCE_DEFAULT);
                        channel.setDescription("Mi descripcion");
                        notificationManager.createNotificationChannel(channel);
                        notificationManager.notify(001, builder.build());
                    }
                }
                System.out.println("Notificación lanzada");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {


    }
}
