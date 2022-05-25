package com.MultiDev.hopeapp.GUIVisores;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MultiDev.hopeapp.Objetos.AdaptadorTarjeta;
import com.MultiDev.hopeapp.Objetos.Paciente;
import com.MultiDev.hopeapp.R;
import com.MultiDev.hopeapp.WebService.Herramientas.ToolJson;
import com.MultiDev.hopeapp.WebService.Medicos.RequestList;
import com.android.volley.Response;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisorPacientes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisorPacientes extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private EditText edtNombrePaciente;
    private Button btnBuscar;
    private AdaptadorTarjeta adaptador;
    private List<Paciente> pacientes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VisorPacientes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VisorPacientes.
     */
    // TODO: Rename and change types and number of parameters
    public static VisorPacientes newInstance(String param1, String param2) {
        VisorPacientes fragment = new VisorPacientes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_visor_pacientes, container, false);
        recyclerView = view.findViewById(R.id.rcVisPacientes);
        inicializarObjetos();

        new RequestList(view.getContext()).mostrarPacientes(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty() && !response.equals("0")){
                    pacientes = ToolJson.convertirJsonListaPacientes(response);
                    if(pacientes.size()>0){
                        adaptador = new AdaptadorTarjeta(pacientes, getContext());
                        recyclerView.setAdapter(adaptador);
                    }
                }
            }
        });
        inicializarEventos();
        return view;
    }
    public void inicializarObjetos(){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        pacientes = new ArrayList<>();
        edtNombrePaciente = view.findViewById(R.id.edtNombreBusquedaPaciente);
        btnBuscar = view.findViewById(R.id.btnBuscarPaciente);
    }
    private void inicializarEventos(){
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNombrePaciente.getText().toString().equals("")){
                    new RequestList(view.getContext()).mostrarPacientes(new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("Respuesta recarga pacientes: "+response);
                            if(!response.isEmpty() && !response.equals("0")){

                                pacientes = ToolJson.convertirJsonListaPacientes(response);
                                if(pacientes.size()>0){
                                    adaptador = new AdaptadorTarjeta(pacientes, getContext());
                                    recyclerView.setAdapter(adaptador);
                                }
                            }else if(response.equals("0")){
                                Toast.makeText(getContext(), "No hay pacientes sin tutela en este momento", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{

                }
            }
        });
        edtNombrePaciente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(edtNombrePaciente.getText().length()>0){
                    btnBuscar.setText("Buscar");
                }else
                    btnBuscar.setText("Actualizar");
            }
        });
    }


}