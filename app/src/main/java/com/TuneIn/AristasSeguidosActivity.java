package com.TuneIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.TuneIn.Adapters.ArtistaAdapter;
import com.TuneIn.BDDUsuario.UsuarioViewModel;
import com.TuneIn.BDDUsuario.VMFactory;
import com.TuneIn.Entidades.Artista;
import com.TuneIn.Entidades.Usuario;
import java.util.concurrent.ExecutionException;

public class AristasSeguidosActivity extends AppCompatActivity {

    String nombreUsuario, idUsuario;
    Button btn_verArtistas;
    TextView tv_sinResultados;
    RecyclerView recyclerArtistasSeguidos;
    DrawerLayout drawerLayout;
    ArtistaAdapter adapter;
    UsuarioViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aristas_seguidos);

        tv_sinResultados = findViewById(R.id.tv_sinResultados);

        // Obtener datos del Usario Logeado
        Intent i = getIntent();
        nombreUsuario = i.getExtras().getString("nombreUsuario");
        idUsuario = i.getExtras().getString("idUsuario");

        Log.d("ROOM", "Obtenido desde TabLayout: usuarioID: " + idUsuario);


        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerArtistasSeguidos = findViewById(R.id.recycler_artistasSeguidos);


        // Inicializar el linear layout
        recyclerArtistasSeguidos.setLayoutManager(new LinearLayoutManager(this));
        recyclerArtistasSeguidos.setHasFixedSize(true);

        // Crear Factory para pasarle parametros al ViewModel y poder utilizarlos en la Query
        VMFactory vmFactory = new VMFactory(idUsuario, this.getApplication());
        viewModel = new ViewModelProvider(this, vmFactory).get(UsuarioViewModel.class);
        //https://stackoverflow.com/questions/51829280/how-to-use-a-viewmodelprovider-factory-when-extends-from-androidviewmodel

        // Inicializar el adaptador
        adapter = new ArtistaAdapter(new ArtistaAdapter.AdapterListener() {
            @Override
            public void onSeguirClick(Artista artista) throws ExecutionException, InterruptedException {
                Log.d("ROOM", "SEGUIDO");

                // El usuario se crea en RegistrarseActivity con una lista de artistasSeguidos vacia
                Usuario usuario = viewModel.getUsuarioById(idUsuario);

                // Agregamos el artista seleccionado
                usuario.getArtistasSeguidosList().add(artista.getArtistaId());

                // Actualizamos el usuario
                viewModel.update(usuario);
            }

            @Override
            public void onArtistaClick(Artista artista) {
                Intent i = new Intent(AristasSeguidosActivity.this, PerfilArtistaActivity.class);
                //i.putExtra("image", );
                i.putExtra("nombre", artista.getNombre());

                // IR AL PERFIL DEL ARTISTA
                startActivity(i);
            }
        });

        // Setear adaptador
        recyclerArtistasSeguidos.setAdapter(adapter);

        // // // // // // // // // // // // // // // // // // // // // //
        // DESCOMENTAR Y PONER LA API
        // // // // // // // // // // // // // // // // // // // // // //

        // Setea en el Recycler la lista de artistas seguidos
        /*viewModel.getArtistasDeUsuario().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> listaIdArtistas) {

                //////////////////////////////API??
                ////////////////////////////API??
                ////////////////////////////API??
                ////////////////////////////API??
                ////////////////////////////API??
                ////////////////////////////API??
                ////////////////////////////API??
                //////////////////////////////API??


                if (listaIdArtistas == null ){                              //listaIdArtistas.size() == 0) {
                    tv_sinResultados.setVisibility(View.VISIBLE);
                    recyclerArtistasSeguidos.setVisibility(View.GONE);
                } else {
                    tv_sinResultados.setVisibility(View.GONE);
                    recyclerArtistasSeguidos.setVisibility(View.VISIBLE);
                }

                // Le pasa la lista al recycler
                adapter.setArtistasSeguidos();

            }
        });*/


        // // // // // // // // // // // DE PRUEBA NOMÁS desp vemos si hacemos otra actividad o como

        btn_verArtistas = findViewById(R.id.btn_verArtistas);
        btn_verArtistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarTodosLosArtistas();
            }
        });
        // // // // // // // // // // // // // // // // //

        //TODO nose si hacerlo https://www.youtube.com/watch?v=xPPMygGxiEo

    }

    public void cargarTodosLosArtistas() {
        //////////////////////////
        //////////////////////////
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // DRAWER
    public void clickDrawer(View view) {
        TabActivity.openDrawer(drawerLayout);
    }

    public void clickPerfil(View view) {
        TabActivity.redirectActivity(this, TabActivity.class);
    }

    public void clickArtistas(View view) {
        TabActivity.redirectActivity(this, ArtistasActivity.class);
    }

    public void clickConfiguracion(View view) {
        //TabActivity.redirectActivity(this, ConfiguracionActivity.class);
    }

    public void clickSalir(View view) {
        TabActivity.logout(this);
    }

    protected void onPause() {
        super.onPause();
        TabActivity.closeDrawer(drawerLayout);
    }
}