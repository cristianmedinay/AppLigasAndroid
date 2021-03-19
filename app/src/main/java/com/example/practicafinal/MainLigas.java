package com.example.practicafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.practicafinal.adaptador.AdaptadorLigas;
import com.example.practicafinal.utils.Ligas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainLigas extends AppCompatActivity {


    private static String URL ="https://www.thesportsdb.com/api/v1/json/1/all_leagues.php";
    Adapter adapter;
    AdaptadorLigas adaptadorLigas;
    ArrayList<Ligas> datos ;
    ListView listView;
    Ligas liga;
    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instancias();
        //datos = new ArrayList<String>();
        //texto = findViewById(R.id.texto);
        getApi();

    }

    private void instancias() {
        datos = new ArrayList();
        listView = findViewById(R.id.lsDatos);
        //adaptadorLigas= new AdaptadorLigas(datos,getApplicationContext());
        //adaptadorLigas= new AdaptadorLigas(getApplicationContext());

    }

    private void getApi() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray object=response.getJSONArray("leagues");
                    for (int i = 0; i < object.length() ; i++) {
                        JSONObject H = object.getJSONObject(i);
                        String name = H.getString("strLeague");
                        String id = H.getString("idLeague");
                    //System.out.println(name);
                        //texto.setText(name);
                        listView.setAdapter(adaptadorLigas);
                        //datos.add(new Ligas(name,id));
                        //liga = new Ligas(name,id);
                        //adaptadorLigas.notifyDataSetChanged();
                        //adaptadorLigas.aniadirLiga(liga);

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

        /*
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //if(response.length()>0){
                        try {
                            JSONArray object=response.getJSONArray("leagues");
                            for (int i = 0; i < object.length() ; i++) {
                                JSONObject H = object.getJSONObject(i);
                                String name = H.getString("idLeague");
                                System.out.println(name);
                            }
                              //String a =object.get("leagues").toString();
                            //Equipos equipos = new Equipos();
                            //equipos.setNombre(object.getString("w").toString());
                            //JSONObject o = object.getJSONObject("leagues");
                            //JSONArray arrayRecord = object.getJSONArray("leagues");
                            //String o = object.getString("idLeague");
                            //texto.setText(equipos.getNombre());
                           // datos.add(object.get("idLeague").toString());
                            //System.out.println(o);
                           // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,datos);
                           // listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                //}



        }; new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onErrorResponse HAY ERROR"+error.getMessage());
            }
        });
        requestQueue.add(request);

         */
    }



}