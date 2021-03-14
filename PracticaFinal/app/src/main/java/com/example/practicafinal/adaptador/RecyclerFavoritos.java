package com.example.practicafinal.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.practicafinal.ActivityEquipos;
import com.example.practicafinal.R;
import com.example.practicafinal.utils.Equipo;

import java.util.ArrayList;

public class RecyclerFavoritos extends RecyclerView.Adapter<RecyclerFavoritos.miHolder> {

    ArrayList<Equipo> listaEquipos;
    Context context;
    RecyclerFavoritos.OnFavoritoSelected listener;
    RequestOptions option;

    public RecyclerFavoritos(Context context) {
        this.listaEquipos = new ArrayList<>();
        this.context = context;

        listener = (RecyclerFavoritos.OnFavoritoSelected) context;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.correcta).error(R.drawable.error);
    }

    @NonNull
    @Override
    public RecyclerFavoritos.miHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_favorito,parent,false);
        RecyclerFavoritos.miHolder miHolder = new RecyclerFavoritos.miHolder(view);
        return miHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerFavoritos.miHolder holder, int position) {
        final  Equipo equipos = listaEquipos.get(position);
        //holder.getImagen().setImageResource(equipos.getImagen());



        Glide.with(context).load(equipos.getImagen()).apply(option).into(holder.imagen);
        holder.getBoton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ejecuto algo algo
                //listener.onEquipoFavoritosSelected(equipos);
                listaEquipos.remove(equipos);
            }
        });
    }

    public void aniadirFavorito(Equipo equipo){
        listaEquipos.add(equipo);
        notifyDataSetChanged();
    }
    public void deleteFavorito(Equipo equipo){
        listaEquipos.remove(equipo);
        notifyDataSetChanged();
    }

    public interface OnFavoritoSelected{
        void onEquipoFavoritosSelected(Equipo equipos);

    }

    @Override
    public int getItemCount() {
        return listaEquipos.size();
    }

    public class miHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        Button boton;
        public miHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_equipo);
            boton = itemView.findViewById(R.id.boton_eliminar);
        }

        public ImageView getImagen() {
            return imagen;
        }

        public void setImagen(ImageView imagen) {
            this.imagen = imagen;
        }

        public Button getBoton() {
            return boton;
        }

        public void setBoton(Button boton) {
            this.boton = boton;
        }
    }
}
