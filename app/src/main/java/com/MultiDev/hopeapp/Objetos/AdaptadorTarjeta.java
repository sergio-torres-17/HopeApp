package com.MultiDev.hopeapp.Objetos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MultiDev.hopeapp.GUIVisores.VisorPacientesDetalles;
import com.MultiDev.hopeapp.R;

import java.util.List;

public class AdaptadorTarjeta extends RecyclerView.Adapter<AdaptadorTarjeta.ViewHolder> {
    private List<Paciente> pacientes;
    private Context context;

    public AdaptadorTarjeta(List<Paciente> pacientes, Context context) {
        this.pacientes = pacientes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paciente,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int prf = position;
        holder.txtNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(context, VisorPacientesDetalles.class);
                intent.putExtra("idPaciente", String.valueOf(pacientes.get(prf).getUsr().getNombre()+" "+pacientes.get(prf).getUsr().getApellidos()));
                context.startActivity(intent);
            }
        });
        holder.imgPaciente.setImageResource(R.drawable.olsen);
        holder.txtNombre.setText(pacientes.get(position).getUsr().getNombre()+ " "+pacientes.get(position).getUsr().getApellidos());
        holder.txtEtapa.setText("Etapa: "+pacientes.get(position).getEtapa());
        holder.txtEdad.setText("Edad: "+String.valueOf(pacientes.get(position).getUsr().getEdad()));
        holder.txtTipo.setText("Tipo: "+pacientes.get(position).getTipo());
    }

    @Override
    public int getItemCount() {
        return this.pacientes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPaciente;
        private TextView txtNombre,txtEdad,txtTipo,txtEtapa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgPaciente = itemView.findViewById(R.id.imgCdPaciente);
            this.txtNombre = itemView.findViewById(R.id.txtCdNombrePaciente);
            this.txtEdad = itemView.findViewById(R.id.txtCdEdad);
            this.txtTipo = itemView.findViewById(R.id.txtCdTipoCancer);
            this.txtEtapa = itemView.findViewById(R.id.txtCdEtapaCancer);
        }
    }

}
