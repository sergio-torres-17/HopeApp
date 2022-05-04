package com.MultiDev.hopeapp.GUIVisores;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        pacientes = new ArrayList<>();

        new RequestList(view.getContext()).mostrarPacientes(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty() && !response.equals("0")){
                    System.out.println("Respuesta: "+response);
                    pacientes = ToolJson.convertirJsonListaPacientes(response);
                    if(pacientes.size()>0){
                        adaptador = new AdaptadorTarjeta(pacientes);
                        recyclerView.setAdapter(adaptador);
                    }
                }
            }
        });



        return view;
    }

}