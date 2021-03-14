package com.example.practicafinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.practicafinal.adaptador.RecyclerEquipos;
import com.example.practicafinal.utils.Equipo;
import com.example.practicafinal.utils.Ligas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityEquipos extends AppCompatActivity implements RecyclerEquipos.OnEquiposSelected {


    TextView texto1,texto2;
    String URL = "https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id=";
    String ids,ruta,name,baner,team,descripcion,idteam,web,insta,face,twit;
    RecyclerEquipos adaptador;
    ArrayList<Equipo> lista;
    RecyclerView recyclerView;
    ImageView imagen,img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liga);


        instancias();
        acciones();
        Intent intent = getIntent();
        texto1.setText(intent.getStringExtra("nombre"));
        ids= intent.getStringExtra("id");
        ruta = URL+ids;
        texto2.setText(ruta);
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        getApi();

    }

    private void getApi() {

        RequestQueue requestQueue= Volley.newRequestQueue(this);

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
                        web = H.getString("strWebsite");
                        face = H.getString("strFacebook");
                        twit = H.getString("strTwitter");
                        insta = H.getString("strInstagram");

        //                for (int j = 0; j <name.length() ; j++) {
                             lista.add(new Equipo(name,baner,team,descripcion,idteam,twit,face,web,insta));
                            adaptador.notifyDataSetChanged();
          //              }
/*
                        ImageRequest imageRequest = new ImageRequest(name, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                recyclerView.setAdapter(adaptador);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));

                                lista.add(new Equipo(response));
                                adaptador.notifyDataSetChanged();
                            }
                        }, 0, 0, null, null, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        */
                        /*
                        for (int j = 0; j <name.length() ; j++) {
                            recyclerView.setAdapter(adaptador);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));

                            lista.add(new Equipo(j));
                            adaptador.notifyDataSetChanged();
                        }*/

                        //u=Glide.with(getApplicationContext())
                          //      .load(name)
                            //    .into(imagen);
                        //lista.add(new Equipo(u));
                        //Glide.with(Liga.this).load(name).into(imagen);




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
    }

    private void acciones() {

    }

    private void instancias() {
        recyclerView = findViewById(R.id.id_recycler);
        //img= findViewById(R.id.img);
        //imagen= findViewById(R.id.imagen_equipo);
        texto1 = findViewById(R.id.texto1);
        texto2 = findViewById(R.id.texto2);
        lista = new ArrayList();
        //adaptador = new RecyclerEquipos(lista, ActivityEquipos.this);
        adaptador = new RecyclerEquipos(lista,ActivityEquipos.this);

    }




    @Override
    public void onEquipoLigaSelected(Equipo equipos) {

        for (Equipo item : lista) {
            if (equipos.getImagen().equals(item.getImagen())) {
                Intent intent = new Intent(getApplicationContext(), ActivityDetalle.class);
                intent.putExtra("name", item.getImagen());
                intent.putExtra("baner", item.getImagen2());
                intent.putExtra("team", item.getNombre());
                intent.putExtra("descripcion", item.getTexto());
                intent.putExtra("idteam", item.getId());
                intent.putExtra("twit", item.getTwit());
                intent.putExtra("face", item.getFace());
                intent.putExtra("web", item.getWeb());
                intent.putExtra("insta", item.getInsta());

                startActivity(intent);
                System.out.println(idteam);
            }
        }
    }


}