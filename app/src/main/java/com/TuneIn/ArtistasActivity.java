package com.TuneIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuneIn.Adapters.ArtistaAdapter;
import com.TuneIn.Entidades.Artista;
import com.TuneIn.Entidades.Usuario;
import com.TuneIn.Entidades.UsuarioArtistasEntity;
import com.TuneIn.Entidades.UsuarioConArtistas;

import java.util.List;

public class ArtistasActivity extends AppCompatActivity {

    String nombreUsuario, idUsuario;
    Button btn_verArtistas;
    TextView tv_sinResultados;
    RecyclerView recyclerArtistas;
    DrawerLayout drawerLayout;
    ArtistaAdapter adapter;
    //ArtistaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistas);

        tv_sinResultados = findViewById(R.id.tv_sinResultados);

        // Obtener datos del Usario Logeado
        /*Intent i = getIntent();
        nombreUsuario = i.getExtras().getString("nombreUsuario");
        idUsuario = i.getExtras().getString("idUsuario");

        Log.d("ROOM", "onCreate: usuarioID: " + idUsuario);

        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout);

        recyclerArtistas = findViewById(R.id.recycler_artistas);
        // Inicializar el linear layout
        recyclerArtistas.setLayoutManager(new LinearLayoutManager(this));
        recyclerArtistas.setHasFixedSize(true);

        // Inicializar el adaptador
        /*adapter = new ArtistaAdapter(new ArtistaAdapter.AdapterListener() {
            @Override
            public void onSeguirClick(Artista artista) {
                Log.d("ROOM", "SEGUIDO");

                UsuarioArtistasEntity usuarioArtistasEntity = new UsuarioArtistasEntity(idUsuario, artista.getArtistaId());
                viewModel.insertUA(usuarioArtistasEntity);

                Log.d("ROOM", "onSeguirClick: UsuarioArtistasEntity UsuarioId: "
                        + usuarioArtistasEntity.usuarioId + " Artista id: " + usuarioArtistasEntity.artistaId);
            }

            @Override
            public void onArtistaClick(Artista artista) {
                Intent i = new Intent(ArtistasActivity.this, PerfilArtistaActivity.class);
                //i.putExtra("image", );
                i.putExtra("nombre", artista.getNombre());
                startActivity(i);
            }
        });

        recyclerArtistas.setAdapter(adapter);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
            .getInstance(this.getApplication())).get(ArtistaViewModel.class);





        viewModel.getArtistasDeUsuario().observe(this, new Observer<UsuarioConArtistas>() {
            @Override
            public void onChanged(UsuarioConArtistas usuarioConArtistas) {
                // Se llamará cada vez que los datos en el LiveData Object cambie
                // Actualizar RecyclerView
                Log.d("ROOM", "resultado getArtistasDe = idUsuario:  " + usuarioConArtistas.getUsuario());
                adapter.setArtistasSeguidos(usuarioConArtistas);

            }
        });

        btn_verArtistas = findViewById(R.id.btn_verArtistas);
        btn_verArtistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarArtistasSeguidos();
            }
        });


        //TODO nose si hacerlo https://www.youtube.com/watch?v=xPPMygGxiEo

    }

    public void cargarArtistasSeguidos() {
        viewModel.getArtistasDeUsuario(idUsuario).observe(this, new Observer<UsuarioConArtistas>() {
            @Override
            public void onChanged(UsuarioConArtistas usuarioConArtistas) {
                // Se llamará cada vez que los datos en el LiveData Object cambie
                // Actualizar RecyclerView
                if (usuarioConArtistas == null) {
                    tv_sinResultados.setVisibility(View.VISIBLE);
                    recyclerArtistas.setVisibility(View.GONE);
                } else {
                    tv_sinResultados.setVisibility(View.GONE);
                    recyclerArtistas.setVisibility(View.VISIBLE);
                }
                adapter.setArtistasSeguidos(usuarioConArtistas);

            }
        });

        viewModel.getAllArtistas().observe(this, new Observer<List<Artista>>() {
            @Override
            public void onChanged(List<Artista> artistas) {
                // Se llamará cada vez que los datos en el LiveData Object cambie
                // Actualizar RecyclerView
                adapter.setArtistas(artistas);

            }
        });
    }


    /////////////////////////////////////////////////////////////////////////////////////////////
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
    */
    }
}