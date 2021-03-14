package com.example.practicafinal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.practicafinal.ActivityEquipos;
import com.example.practicafinal.R;
import com.example.practicafinal.adaptador.RecyclerEquipos;
import com.example.practicafinal.utils.Equipo;
import com.example.practicafinal.utils.Ligas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentEquipos extends Fragment {
    TextView texto1,texto2;
    String URL = "https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id=";
    String URL2 = "https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id=4328";

    String ids,ruta,name,baner,team,descripcion,idteam,web,insta,face,twit;
    RecyclerEquipos adaptador;
    ArrayList<Equipo> lista;
    RecyclerView recyclerView;
    ImageView imagen,img;
    Equipo equipo;
    OnselectedListenerEquipos listener;
    Ligas detalleRecuperado;

    public static FragmentEquipos newInstance(Ligas ligas) {
        Bundle args = new Bundle();
        args.putSerializable("key",ligas);
        FragmentEquipos fragment = new FragmentEquipos();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(this.getArguments() != null) {
            detalleRecuperado = (Ligas) this.getArguments().getSerializable("key");
            ids=detalleRecuperado.getLiga();
            ruta = URL+ids;
        }else{
            ruta = URL2;
        }
        lista = new ArrayList();
        adaptador = new RecyclerEquipos(lista,getContext());
        //adaptador = new RecyclerEquipos(getContext());

    }

    public void addEquiposJSON(Ligas ligas){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipos,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = getView().findViewById(R.id.id_recycler_fragment);
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        getApi();
        acciones();

    }

    private void acciones() {


    }
    private void getApi() {

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());

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

                        lista.add(new Equipo(name,baner,team,descripcion,idteam,twit,face,web,insta));
                        //equipo = new Equipo(name,baner,team,descripcion,idteam,twit,face,web,insta);
                        //adaptador.aniadirEquipoLiga(equipo);
                        adaptador.notifyDataSetChanged();


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
    public interface OnselectedListenerEquipos{
        void OnselectedEquipos();
    }


}
