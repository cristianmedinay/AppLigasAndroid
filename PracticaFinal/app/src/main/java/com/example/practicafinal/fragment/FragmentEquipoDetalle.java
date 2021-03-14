package com.example.practicafinal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.practicafinal.R;
import com.example.practicafinal.dialogo.DialogoRedes;
import com.example.practicafinal.utils.Equipo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentEquipoDetalle  extends Fragment {

    Equipo recuperarDetalle;
    TextView texto3,equipoNombre,texto;
    ImageView imagen;
    String nombre,baner,team,descripcion,idteam,ruta,name,twit,face,web,insta;
    Button btnRedes,btnFavoritos;
    String url = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=";
    OnSelectedListenerEquipoDetalle listener;

    public static FragmentEquipoDetalle newInstance(Equipo equipo) {
        Bundle args = new Bundle();
        args.putSerializable("key2",equipo);
        FragmentEquipoDetalle fragment = new FragmentEquipoDetalle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnSelectedListenerEquipoDetalle) context;
        recuperarDetalle = (Equipo) this.getArguments().getSerializable("key2");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipo_detalle,container,false);
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instancias();
        acciones();
        equipoNombre.setText(recuperarDetalle.getNombre());
        texto.setText(recuperarDetalle.getTexto());
        Glide.with(getContext()).load(recuperarDetalle.getImagen2()).into(imagen);

    }

    private void acciones() {
        btnRedes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Equipo equipo = new Equipo(recuperarDetalle.getImagen(),
                        recuperarDetalle.getImagen2(),
                        recuperarDetalle.getNombre(),
                        recuperarDetalle.getTexto(),
                        recuperarDetalle.getId(),
                        recuperarDetalle.getTwit(),recuperarDetalle.getFace(),recuperarDetalle.getWeb(),recuperarDetalle.getInsta());
                listener.OnSelecterEquipoDetalle(equipo);

            }
        });
        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Equipo equipo = new Equipo(recuperarDetalle.getImagen(),
                        recuperarDetalle.getImagen2(),
                        recuperarDetalle.getNombre(),
                        recuperarDetalle.getTexto(),
                        recuperarDetalle.getId(),
                        recuperarDetalle.getTwit(),recuperarDetalle.getFace(),recuperarDetalle.getWeb(),recuperarDetalle.getInsta());
                listener.OnSelecterEquipoFavorito(equipo);



            }
        });
    }

    public interface OnSelectedListenerEquipoDetalle {
       void OnSelecterEquipoDetalle(Equipo equipo);
       void OnSelecterEquipoFavorito(Equipo equipo);

    }

    private void instancias() {
        imagen = getView().findViewById(R.id.imagen_equipo_fragment_);
        texto = getView().findViewById(R.id.texto_equipo_fragment_);
        equipoNombre =getView().findViewById(R.id.nombre_equipo_fragment_);
        btnRedes = getView().findViewById(R.id.btn_redes_);
        btnFavoritos =getView().findViewById(R.id.btn_favoritos);
    }


}
