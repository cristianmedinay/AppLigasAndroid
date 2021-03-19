package com.example.practicafinal.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicafinal.R;
import com.example.practicafinal.utils.Ligas;

import java.util.ArrayList;

public class AdaptadorLigas  extends BaseAdapter {

    ArrayList<Ligas> listaLigas;
    Context context;

    public AdaptadorLigas(ArrayList<Ligas> listaLigas, Context context) {
        this.listaLigas = listaLigas;
        this.context = context;
    }

    /*public AdaptadorLigas(Context context) {
        this.listaLigas = new ArrayList<>();
        this.context = context;
    }*/

    @Override
    public int getCount() {
        return listaLigas.size();
    }

    @Override
    public Object getItem(int i) {
        return listaLigas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_lista, viewGroup,false);
        Ligas datoactual = listaLigas.get(i);
        TextView name = view.findViewById(R.id.texto_liga);
        name.setText(datoactual.getNombre());
        return view;
    }

    public void aniadirLiga(Ligas ligas){
        listaLigas.add(ligas);
        notifyDataSetChanged();
    }




}
