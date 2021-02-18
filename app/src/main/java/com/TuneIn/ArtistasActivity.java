package com.TuneIn;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.TuneIn.Adapters.ArtistaAdapter;
import com.TuneIn.Entidades.Artista;
import com.TuneIn.Extra.JSONResponse;
import com.TuneIn.Interfaces.ArtistaAPI;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistasActivity extends AppCompatActivity {
    String nombreUsuario, idUsuario;
    Button btn_verArtistas;
    TextView tv_sinResultados;
    RecyclerView recyclerArtistas;
    DrawerLayout drawerLayout;
    ArtistaAdapter adapter;
    List<Artista> artistasList;
    //ArtistaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistas);

        drawerLayout = findViewById(R.id.drawer_layout);
        tv_sinResultados = findViewById(R.id.tv_sinResultados);
        recyclerArtistas = findViewById(R.id.recycler_artistas);

        artistasList = new ArrayList<>();
        recyclerArtistas.setLayoutManager(new LinearLayoutManager(this));
        recyclerArtistas.setHasFixedSize(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.seatgeek.com/2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArtistaAPI artistAPI = retrofit.create(ArtistaAPI.class);
        ArrayList<Artista> resultados = new ArrayList<>();
       // Call<Artista> callSingleArtist = artistAPI.getArtista(266);
        Call<JSONResponse> callAll = artistAPI.getArtistas("concerts", "id.asc", 5000, 1);

        callAll.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                artistasList = new ArrayList<>(jsonResponse.getArtistasArray());
                resultados.addAll(artistasList);

                PutDataIntoRecyclerView(resultados);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });




  /*      callSingleArtist.enqueue(new Callback<Artista>() {
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
        ArtistaAdapter artistaAdapter = new ArtistaAdapter(this, artistasList);
        recyclerArtistas.setLayoutManager(new LinearLayoutManager(this));
        recyclerArtistas.setAdapter(artistaAdapter);
    }
}


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
