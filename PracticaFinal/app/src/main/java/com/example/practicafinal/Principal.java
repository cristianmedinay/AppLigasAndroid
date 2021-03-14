package com.example.practicafinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practicafinal.adaptador.RecyclerEquipos;
import com.example.practicafinal.adaptador.RecyclerFavoritos;
import com.example.practicafinal.dialogo.DialogoRedes;
import com.example.practicafinal.fragment.FragmentEquipoDetalle;
import com.example.practicafinal.fragment.FragmentEquipos;
import com.example.practicafinal.fragment.FragmentFavoritos;
import com.example.practicafinal.fragment.FragmentLigas;
import com.example.practicafinal.utils.Equipo;
import com.example.practicafinal.utils.Ligas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;

public class Principal extends AppCompatActivity implements
        FragmentLigas.OnSelectedListenerLigas,
        FragmentEquipos.OnselectedListenerEquipos,
        RecyclerEquipos.OnEquiposSelected,
        FragmentEquipoDetalle.OnSelectedListenerEquipoDetalle,
        RecyclerFavoritos.OnFavoritoSelected {

    private NavigationView navigationView;
    ArrayList<Equipo> lista;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FrameLayout fragmentUno,fragmentDos,fragmentTres;
    Toolbar toolbar;
    TextView textoToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Equipo equipo;
    FirebaseDatabase firebaseDatabase;
    String nombre,correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        instancias();
        nombre = getIntent().getStringExtra("texto");
        correo = getIntent().getStringExtra("correo");
        escribirNodo();
        acciones();
        personalizarBarra();
        textoToolbar.setText("");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_uno,new FragmentFavoritos(),"list");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_uno,new FragmentLigas(),"uno");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if(fragmentDos!=null) {
            fragmentManager = getSupportFragmentManager();
            //FragmentEquipos fragment = (FragmentEquipos) fragmentManager.findFragmentByTag("dos");
            fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.add(R.id.fragment_dos,fragment,"dos");
            fragmentTransaction.add(R.id.fragment_dos,new FragmentEquipos(),"dos");

            fragmentTransaction.commit();
        }


    }
    private void escribirNodo() {

        DatabaseReference databaseReference =firebaseDatabase.getReference().child("usuarios").child(nombre);
        databaseReference.child("correo").setValue(correo);
        String nombrecortado = correo.substring(0,correo.indexOf('@'));
        databaseReference.child("nombre").setValue(nombrecortado);


        databaseReference = firebaseDatabase.getReference().child("usuarios").child(nombre).child("equipos").child("favoritos");
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    //DataSnapshot hijo = iterator.next();
                    //Usuario usuario = hijo.getValue(Usuario.class);
                    Equipo favorito1 = iterator.next().getValue(Equipo.class);
                    FragmentFavoritos fragments = (FragmentFavoritos) getSupportFragmentManager().findFragmentByTag("list");

                    fragments.addEquipo(favorito1);

                    /*if(favorito1.getNombre().equals("Barcelona")) {
                        fragments.addEquipo(favorito1);
                    }*/
                    /*if(favorito1.getNombre().equals("Barcelona")) {
                        fragments.addEquipo(favorito1);
                    } */                           /*Iterator<DataSnapshot> itera = dataSnapshot.getChildren().iterator();
                            while (iterator.hasNext()) {
                                DataSnapshot h = iterator.next();
                                Usuario u = h.getValue(Usuario.class);
                                Toast.makeText(getApplicationContext(),u.toString(),Toast.LENGTH_SHORT).show();

                            }*/
                    Log.v("aaaaaaaasaa",String.valueOf(favorito1));

                    Toast.makeText(getApplicationContext(),favorito1.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    private void acciones() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //fragmentManager = getSupportFragmentManager();
                //fragmentTransaction = fragmentManager.beginTransaction();
               //FragmentFavoritos fragment = (FragmentFavoritos) fragmentManager.findFragmentByTag("list");
                //fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.menu_opcion_dos:
                        //fragmentTransaction.replace(R.id.fragment_uno, FragmentFavoritos.newInstance(equipo), "uno");
                        //Toast.makeText(getApplicationContext(),"Pulsado",Toast.LENGTH_SHORT).show();
                        fragmentManager = getSupportFragmentManager();
                        FragmentFavoritos fragment = (FragmentFavoritos) fragmentManager.findFragmentByTag("list");
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_uno,fragment,"list");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        break;

                    case R.id.menu_opcion_tres:
                        System.exit(0);
                        break;
                }


                drawerLayout.closeDrawers();

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }

    }
    private void personalizarBarra() {
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(Principal.this,drawerLayout,toolbar,R.string.open_draw,R.string.close_draw);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    private void instancias() {
        toolbar = findViewById(R.id.toolbar_fragment_m);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.menu_navigation);
        fragmentUno = findViewById(R.id.fragment_uno);
        fragmentDos = findViewById(R.id.fragment_dos);
        //fragmentTres = findViewById(R.id.fragment_tres)
        lista = new ArrayList<>();
        textoToolbar = findViewById(R.id.texto_toolbar);
        firebaseDatabase =FirebaseDatabase.getInstance("https://ejemplo-46978-default-rtdb.europe-west1.firebasedatabase.app/");

        //setSupportActionBar(toolbar);
    }
    @Override
    public void OnSelectedLigas(Ligas ligas) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(fragmentDos==null) {
            fragmentTransaction.replace(R.id.fragment_uno, FragmentEquipos.newInstance(ligas), "dos");
            fragmentTransaction.addToBackStack(null);
            textoToolbar.setText(ligas.getNombre());

            /*
            FragmentEquipos fragment = (FragmentEquipos) getSupportFragmentManager().findFragmentByTag("dos");
            fragment.addEquiposJSON(ligas);
            */

        }else{
            fragmentTransaction.replace(R.id.fragment_dos, FragmentEquipos.newInstance(ligas), "dos");

        }
        fragmentTransaction.commit();

        //Toast.makeText(getApplicationContext(),ligas.getLiga(),Toast.LENGTH_SHORT).show();
    }
    @Override
    public void OnselectedEquipos() {


    }

    @Override
    public void onEquipoLigaSelected(Equipo equipos) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(fragmentDos==null) {
            fragmentTransaction.replace(R.id.fragment_uno, FragmentEquipoDetalle.newInstance(equipos), "tres");
            fragmentTransaction.addToBackStack(null);
        }else{
            fragmentTransaction.replace(R.id.fragment_dos, FragmentEquipoDetalle.newInstance(equipos), "tres");

        }
                fragmentTransaction.commit();

    }


    @Override
    public void OnSelecterEquipoDetalle(Equipo equipo) {
        //Toast.makeText(getApplicationContext(),"pulsado",Toast.LENGTH_SHORT).show();
        DialogoRedes dialogoRedes = DialogoRedes.newInstance(equipo);
        dialogoRedes.show(getSupportFragmentManager(),"key3");

    }

    @Override
    public void OnSelecterEquipoFavorito(Equipo equipo) {

        //Toast.makeText(getApplicationContext(),equipo.toString(),Toast.LENGTH_SHORT).show();
        FragmentFavoritos fragment = (FragmentFavoritos) getSupportFragmentManager().findFragmentByTag("list");
        String equipoNombre = equipo.getNombre();
        DatabaseReference databaseReferenceEscritura = firebaseDatabase.getReference().child("usuarios").child(nombre);
        databaseReferenceEscritura.child("equipos").child("favoritos").child(equipoNombre).setValue(equipo);

        DatabaseReference databaseRefeceTodos = firebaseDatabase.getReference().child("usuarios").child(nombre).child("equipos").child("favoritos");
        databaseRefeceTodos.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    //DataSnapshot hijo = iterator.next();
                    //Usuario usuario = hijo.getValue(Usuario.class);
                    Equipo favorito1 = iterator.next().getValue(Equipo.class);
                    if(favorito1.getNombre().equals(equipo.getNombre())) {
                        fragment.addEquipo(favorito1);
                    }                            /*Iterator<DataSnapshot> itera = dataSnapshot.getChildren().iterator();
                            while (iterator.hasNext()) {
                                DataSnapshot h = iterator.next();
                                Usuario u = h.getValue(Usuario.class);
                                Toast.makeText(getApplicationContext(),u.toString(),Toast.LENGTH_SHORT).show();

                            }*/
                    Log.v("aaaaaaaaaa",String.valueOf(favorito1));

                    Toast.makeText(getApplicationContext(),favorito1.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*FragmentFavoritos fragment = (FragmentFavoritos) getSupportFragmentManager().findFragmentByTag("list");
        fragment.addEquipo(equipo);*/
    }

    @Override
    public void onEquipoFavoritosSelected(Equipo equipos) {
        FragmentFavoritos fragment = (FragmentFavoritos) getSupportFragmentManager().findFragmentByTag("list");
        fragment.eliminaEquipo(equipos);

    }
}