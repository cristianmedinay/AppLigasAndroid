package com.example.practicafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.practicafinal.dialogo.DialogoRedes;
import com.example.practicafinal.utils.Equipo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityDetalle extends AppCompatActivity  {

    TextView texto3,equipoNombre,texto;
    ImageView imagen;
    String nombre,baner,team,descripcion,idteam,ruta,name,twit,face,web,insta;
    Button btnRedes;
    String url = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=";
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        //texto3=findViewById(R.id.texto3);
        instancias();
        Intent intent = getIntent();
        nombre=intent.getStringExtra("name");
        baner=intent.getStringExtra("baner");
        team=intent.getStringExtra("team");
        descripcion=intent.getStringExtra("descripcion");
        idteam =intent.getStringExtra("idteam");
        twit =intent.getStringExtra("twit");
        face =intent.getStringExtra("face");
        web =intent.getStringExtra("web");
        insta =intent.getStringExtra("insta");
        ruta = url+idteam;


        Equipo equipo = new Equipo(name,baner,team,descripcion,idteam,twit,face,web,insta);

        equipoNombre.setText(team);
        texto.setText(descripcion);
        Glide.with(getApplicationContext()).load(baner).into(imagen);

        //System.out.println(descripcion);
       // getApi();

       // System.out.println(idteam);
        btnRedes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogoRedes dialogoRedes = DialogoRedes.newInstance(equipo);
                dialogoRedes.show(getSupportFragmentManager(),"uno");



            }
        });

    }

    private void instancias() {
        imagen = findViewById(R.id.imagen_equipo_fragment);
        texto = findViewById(R.id.texto_equipo_fragment);
        equipoNombre =findViewById(R.id.nombre_equipo_fragment);
        btnRedes = findViewById(R.id.btn_redes);


    }

/*
    private void getApi() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ruta, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray object=response.getJSONArray("teams");
                    for (int i = 0; i < object.length() ; i++) {
                        JSONObject H = object.getJSONObject(i);
                        name = H.getString("strTeamBadge");
                        baner = H.getString("strTeamBanner");
                        team = H.getString("strTeam");
                        descripcion = H.getString("strDescriptionEN");
                        idteam = H.getString("idTeam");




                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onErrorResponse HAY ERROR"+error.getMessage());

            }

        });
        requestQueue.add(request);

        }*/


    }


