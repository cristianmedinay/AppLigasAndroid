package com.example.practicafinal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicafinal.R;
import com.example.practicafinal.adaptador.RecyclerFavoritos;
import com.example.practicafinal.utils.Equipo;

import java.util.ArrayList;

public class FragmentFavoritos extends Fragment {

    RecyclerView lista;
    RecyclerFavoritos adaptador;
    Equipo detalle;
    ArrayList<Equipo> listaEquipo;
    TextView texto;
    /*public static FragmentFavoritos newInstance(Equipo equipo) {
        Bundle args = new Bundle();
        args.putSerializable("favorito", equipo);
        FragmentFavoritos fragment = new FragmentFavoritos();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            detalle = (Equipo) this.getArguments().getSerializable("favorito");
            //listaEquipo = Filtro.newInstance().getListaFiltrada(detalle);

        } else {
            //listaEquipo = Filtro.newInstance().getListaCompleta();

        }
    }*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        adaptador = new RecyclerFavoritos(context);
        adaptador.notifyDataSetChanged();
    }

    public void addEquipo(Equipo equipo){
        //texto.setText(equipo.toString());


        adaptador.aniadirFavorito(equipo);
    }
    public void eliminaEquipo(Equipo equipo){
        adaptador.deleteFavorito(equipo);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favoritos, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        texto = getView().findViewById(R.id.texto);
        lista= getView().findViewById(R.id.recycler_uno);
        lista.setAdapter(adaptador);
        lista.setLayoutManager(new GridLayoutManager(getContext(),2));

        //texto.setText();

    }
}
