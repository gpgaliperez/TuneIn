package com.TuneIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuneIn.Adapters.ArtistaAdapter;
import com.TuneIn.BDD.ArtistaViewModel;
import com.TuneIn.Entidades.Artista;

import java.util.List;

public class ArtistasActivity extends AppCompatActivity{

    RecyclerView recyclerArtistas;
    DrawerLayout drawerLayout;
    ArtistaAdapter adapter;


    private ArtistaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistas);


        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout);

        recyclerArtistas = findViewById(R.id.recycler_artistas);
        // Inicializar el linear layout
        recyclerArtistas.setLayoutManager(new LinearLayoutManager(this));
        recyclerArtistas.setHasFixedSize(true);

        // Inicializar el adaptador
        adapter = new ArtistaAdapter();
        recyclerArtistas.setAdapter(adapter);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ArtistaViewModel.class);
        viewModel.getAllArtistas().observe(this, new Observer<List<Artista>>() {
            @Override
            public void onChanged(List<Artista> artistas) {
                // Se llamará cada vez que los datos en el LiveData Object cambie
                // Actualizar RecyclerView
                adapter.setArtistas(artistas);
            }
        });

        adapter.setOnArtistaClickListener(new ArtistaAdapter.OnArtistaClickListener() {
            @Override
            public void onArtistaClick(Artista artista) {
                Intent i = new Intent(ArtistasActivity.this, PerfilArtistaActivity.class);
                //i.putExtra("image", );
                i.putExtra("nombre", artista.getNombre());
                startActivity(i);
            }
        });

        //TODO nose si hacerlo https://www.youtube.com/watch?v=xPPMygGxiEo


    }


    /////////////////////////////////////////////////////////////////////////////////////////////
    // DRAWER
    public void clickDrawer(View view){
        TabActivity.openDrawer(drawerLayout);
    }
    public void clickPerfil(View view){
        TabActivity.redirectActivity(this, TabActivity.class);
    }
    public void clickArtistas(View view){
        TabActivity.redirectActivity(this, ArtistasActivity.class);
    }
    public void clickConfiguracion(View view){
        //TabActivity.redirectActivity(this, ConfiguracionActivity.class);
    }
    public void clickSalir(View view){
        TabActivity.logout(this);
    }
    protected void onPause(){
        super.onPause();
        TabActivity.closeDrawer(drawerLayout);
    }
}