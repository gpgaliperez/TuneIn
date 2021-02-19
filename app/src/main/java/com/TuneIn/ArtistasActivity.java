package com.TuneIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuneIn.Adapters.AllArtistasAdapter;
import com.TuneIn.Adapters.SeguidosAdapter;
import com.TuneIn.Entidades.Artista;
import com.TuneIn.Entidades.Usuario;
import com.TuneIn.Extra.JSONResponse;
import com.TuneIn.Interfaces.ArtistaAPI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistasActivity extends AppCompatActivity {
    String nombreUsuario, idUsuario, nombreArtista, imagenArtista;
    TextView tv_sinResultados;
    RecyclerView recyclerArtistas;
    DrawerLayout drawerLayout;
    AllArtistasAdapter adapter;
    List<Artista> artistasList;
    List<Artista> listaArtistas; //gali


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistas);

        drawerLayout = findViewById(R.id.drawer_layout);
        tv_sinResultados = findViewById(R.id.tv_sinResultados);
        recyclerArtistas = findViewById(R.id.recycler_artistas);

        Intent i = getIntent();
        nombreUsuario = i.getExtras().getString("nombreUsuario");
        idUsuario = i.getExtras().getString("idUsuario");
        TextView nombreUsuarioDrawer = findViewById(R.id.nombreUsuarioDrawer);
        nombreUsuarioDrawer.setText(nombreUsuario);

        listaArtistas = new ArrayList<>();
        artistasList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.seatgeek.com/2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArtistaAPI artistaAPI = retrofit.create(ArtistaAPI.class);
       //Call<Artista> callSingleArtist = artistAPI.getArtista(266); Esto se podría usar en Perfil Artista para traer todos los datos
        Call<JSONResponse> callAll = artistaAPI.getArtistas("concerts", "id.asc", 5000, 1);

        callAll.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                artistasList = new ArrayList<>(jsonResponse.getArtistasArray());
                PutDataIntoRecyclerView(artistasList);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });

  /* Esto se podría usar en Perfil Artista para traer todos los datos
   callSingleArtist.enqueue(new Callback<Artista>() {
            @Override
            public void onResponse(Call<Artista> call, Response<Artista> response) {
                Artista artista = response.body();
                resultados.add(artista);
                PutDataIntoRecyclerView(resultados);
            }

            @Override
            public void onFailure(Call<Artista> call, Throwable t) {
            }
        });
*/
    }

    private void PutDataIntoRecyclerView(List<Artista> artistasList) {
        adapter = new AllArtistasAdapter(this, artistasList, new SeguidosAdapter.AdapterListener() {
            @Override
            public void onSeguirClick(Artista artista) throws ExecutionException, InterruptedException {
                // El usuario se crea en RegistrarseActivity con una lista de artistasSeguidos vacia
                Usuario usuario = AristasSeguidosActivity.viewModel.getUsuarioById(idUsuario);

                // Agregamos el artista seleccionado
                usuario.getArtistasSeguidosList().add(artista.getArtistaId());
                Log.d("ROOM", "SEGUIDO");
                // Actualizamos el usuario
                AristasSeguidosActivity.viewModel.update(usuario);
            }

            @Override
            public void onArtistaClick(Artista artista) {
                Intent i = new Intent(ArtistasActivity.this, PerfilArtistaActivity.class);
                i.putExtra("nombreArtista", artista.getNombre());
                startActivity(i);
            }
        });

        recyclerArtistas.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerArtistas.setHasFixedSize(true);
        recyclerArtistas.setAdapter(adapter);
    }

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

