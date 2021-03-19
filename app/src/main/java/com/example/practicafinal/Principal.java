package com.example.practicafinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        RecyclerFavoritos.OnFavoritoSelected{

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
    Ligas LigaNombres;
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
        //textoToolbar.setText("");

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_uno,new FragmentFavoritos(),"list");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_uno,new FragmentLigas(),"uno");
        fragmentTransaction.addToBackStack("uno");
        fragmentTransaction.commit();
        if(fragmentDos!=null) {
            fragmentManager = getSupportFragmentManager();
            //FragmentEquipos fragment = (FragmentEquipos) fragmentManager.findFragmentByTag("dos");
            fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.add(R.id.fragment_dos,fragment,"dos");
            fragmentTransaction.add(R.id.fragment_dos,new FragmentEquipos(),"dos");
            fragmentTransaction.addToBackStack(null);
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
                }

            }
        });

    }

    private void acciones() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_opcion_uno:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.fragment_uno,new FragmentLigas(),"uno");
                        fragmentTransaction.addToBackStack("todos");
                        getSupportActionBar().setTitle("Todas las Ligas");
                        break;
                    case R.id.menu_opcion_dos:
                        fragmentManager = getSupportFragmentManager();
                        FragmentFavoritos fragment = (FragmentFavoritos) fragmentManager.findFragmentByTag("list");
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_uno,fragment,"list");
                        fragmentTransaction.addToBackStack("favorito");
                        getSupportActionBar().setTitle("Favoritos");

                        break;
                    case R.id.menu_opcion_tres:
                        System.exit(0);
                        break;
                }

                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            System.exit(0);
        }else {
           switch (getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1).getName()) {

               case "todos":
                   getSupportActionBar().setTitle("Todas las Ligas ");
                   break;
               case "favorito":
                   getSupportActionBar().setTitle("Favoritos");
                   break;
               case "uno":
                    getSupportActionBar().setTitle("Todas las Ligas");
                    break;

                case "dos":
                    getSupportActionBar().setTitle(LigaNombres.getNombre());
                    break;
            }
        }
       // Log.v("prueba", "Nº nombre: "+String.valueOf(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1).getName()));
       // Log.v("prueba", "Nº Fragments: " + String.valueOf(getSupportFragmentManager().getFragments().size()));
       // Log.v("prueba", "Nº Estados: " + String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
       // Log.v("prueba", "Nº tag: " + String.valueOf(getSupportFragmentManager().findFragmentByTag("list")));
    }


    private void personalizarBarra() {
        setSupportActionBar(toolbar);
        //SI BORRO EL TOGGLE ME FUNCIONA EN LAND ESCAPE
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
            // CARGO LOS DATOS DE LIGAS AL FRAGMENT EQUIPOS
            fragmentTransaction.add(R.id.fragment_uno, FragmentEquipos.newInstance(ligas), "dos");
            fragmentTransaction.addToBackStack("dos");
            //MANDO LOS DATOS AL TOOLBAR
            getSupportActionBar().setTitle(ligas.getNombre());
            LigaNombres = ligas;
            /*
            FragmentEquipos fragment = (FragmentEquipos) getSupportFragmentManager().findFragmentByTag("dos");
            fragment.addEquiposJSON(ligas);
            */
        }else{
            fragmentTransaction.replace(R.id.fragment_dos, FragmentEquipos.newInstance(ligas), "dos");
            fragmentTransaction.addToBackStack(null);

        }
        fragmentTransaction.commit();

    }
    @Override
    public void OnselectedEquipos() {


    }

    @Override
    public void onEquipoLigaSelected(Equipo equipos) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(fragmentDos==null) {
            // EL FRAGMENT EQUIPO DETALLE SE CARGA MEDIANTE EL FRAGMENT EQUIPO
            fragmentTransaction.add(R.id.fragment_uno, FragmentEquipoDetalle.newInstance(equipos), "tres");
            fragmentTransaction.addToBackStack(null);
            getSupportActionBar().setTitle(equipos.getNombre());
        }else{
            // FUNCIONA EN LAND ESCAPE SI BORRO EL TOGGLE
            fragmentTransaction.replace(R.id.fragment_dos, FragmentEquipoDetalle.newInstance(equipos), "tres");
            fragmentTransaction.addToBackStack(null);

        }
        fragmentTransaction.commit();

    }


    @Override
    public void OnSelecterEquipoDetalle(Equipo equipo) {
        // LLAMO AL DIALOGO REDES DESDE EL FRAGMENT EQUIPO
        DialogoRedes dialogoRedes = DialogoRedes.newInstance(equipo);
        dialogoRedes.show(getSupportFragmentManager(),"key3");

    }

    @Override
    public void OnSelecterEquipoFavorito(Equipo equipo) {

        //MANDO AL FRAGMENT FAVORITO EL EQUIPO SELECCIONADO
        FragmentFavoritos fragment = (FragmentFavoritos) getSupportFragmentManager().findFragmentByTag("list");
        String equipoNombre = equipo.getNombre();
        DatabaseReference databaseReferenceEscritura = firebaseDatabase.getReference().child("usuarios").child(nombre);
        databaseReferenceEscritura.child("equipos").child("favoritos").child(equipoNombre).setValue(equipo);

        //SE MANDA AL SERVIDOR EL EQUIPO SELECCIONADO
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
                    }
                }
            }
        });
        /*FragmentFavoritos fragment = (FragmentFavoritos) getSupportFragmentManager().findFragmentByTag("list");
        fragment.addEquipo(equipo);*/
    }


    @Override
    public void onEquipoFavoritosSelected(Equipo equipos) {

        //ELIMINA EL EL EQUIPO SELECCIONADO
        DatabaseReference databa = firebaseDatabase.getReference().child("usuarios").child(nombre).child("equipos").child("favoritos").child(equipos.getNombre());
        databa.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Equipo equipo = task.getResult().getValue(Equipo.class);
                if(equipo.getNombre().equals(equipos.getNombre())){
                    FragmentFavoritos fragments = (FragmentFavoritos) getSupportFragmentManager().findFragmentByTag("list");
                    databa.setValue(null);
                    Toast.makeText(getApplicationContext(),"borro",Toast.LENGTH_SHORT).show();
                    fragments.eliminaEquipo(equipos);

                }
            }
        });
        /*FragmentFavoritos fragment = (FragmentFavoritos) getSupportFragmentManager().findFragmentByTag("list");
        fragment.eliminaEquipo(equipos);*/

    }
}