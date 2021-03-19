package com.example.practicafinal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.example.practicafinal.R;
import com.example.practicafinal.adaptador.AdaptadorLigas;
import com.example.practicafinal.utils.Ligas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentLigas extends Fragment {

    OnSelectedListenerLigas listener;
    private static String URL ="https://www.thesportsdb.com/api/v1/json/1/all_leagues.php";
    Adapter adapter;
    AdaptadorLigas adaptadorLigas;
    ArrayList<Ligas> datos ;
    ListView listView;
    Ligas ligas;
    TextView texto;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnSelectedListenerLigas) context;
        datos = new ArrayList();
        //adaptadorLigas= new AdaptadorLigas(datos,context);
        adaptadorLigas= new AdaptadorLigas(datos,context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ligas,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getView().findViewById(R.id.lsDatos_fragment);
        getApi();
        acciones();
    }

    private void acciones() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ligas dato = (Ligas) adaptadorLigas.getItem(i);
                adaptadorLigas.notifyDataSetChanged();
                for (Ligas item : datos) {
                    if (dato.getNombre().equals(item.getNombre())) {
                        Ligas ligas = new Ligas(item.getNombre().toString(),item.getLiga().toString());
                        listener.OnSelectedLigas(ligas);
                    }
                }
                adaptadorLigas.notifyDataSetChanged();
            }
        });
    }

    private void getApi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray object = response.getJSONArray("leagues");
                    for (int i = 0; i < object.length(); i++) {
                        JSONObject H = object.getJSONObject(i);

                        String name = H.getString("strLeague");
                        String id = H.getString("idLeague");
                        listView.setAdapter(adaptadorLigas);
                        //datos.add(new Ligas(name, id));
                        ligas = new Ligas(name, id);
                        adaptadorLigas.aniadirLiga(ligas);
                        //adaptadorLigas.notifyDataSetChanged();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse HAY ERROR" + error.getMessage());

            }

        });
        requestQueue.add(request);

    }

    public interface  OnSelectedListenerLigas{
        void OnSelectedLigas(Ligas ligas);
    }


}
