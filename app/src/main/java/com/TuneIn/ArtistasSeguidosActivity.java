package com.TuneIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.TuneIn.Adapters.SeguidosAdapter;
import com.TuneIn.BDDUsuario.RepositorioU;
import com.TuneIn.Converters.ListConverter;
import com.TuneIn.Entidades.Artista;
import com.TuneIn.Entidades.Usuario;
import com.TuneIn.Interfaces.ArtistaAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistasSeguidosActivity extends AppCompatActivity implements RepositorioU.OnResultCallback{
    String nombreUsuario, idUsuario;
    Button btn_verArtistas;
    TextView tv_sinResultados;
    RecyclerView recyclerArtistasSeguidos;
    DrawerLayout drawerLayout;
    SeguidosAdapter adapter;
    Usuario usuarioActual;
    List<Artista> artistasList = new ArrayList<>();
    List<String> artistasUSUARIO = new ArrayList<>();

    RepositorioU repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aristas_seguidos);

        tv_sinResultados = findViewById(R.id.tv_sinResultados);
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerArtistasSeguidos = findViewById(R.id.recycler_artistasSeguidos);

        // Obtener datos del Usario Logeado
        Intent i = getIntent();
        nombreUsuario = i.getExtras().getString("nombreUsuario");
        idUsuario = i.getExtras().getString("idUsuario");
        TextView nombreUsuarioDrawer = findViewById(R.id.nombreUsuarioDrawer);
        nombreUsuarioDrawer.setText(nombreUsuario);

        // Crear Repositorio
        repositorio = new RepositorioU(getApplication(), this);
        repositorio.getUsuarioById(idUsuario);


//--------------------------- DE PRUEBA NOMÁS desp vemos si hacemos otra actividad o como-------------------------//
        btn_verArtistas = findViewById(R.id.btn_verArtistas);
        btn_verArtistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarTodosLosArtistas();
            }
        });
    }

    public void cargarTodosLosArtistas() {
        Intent i = new Intent(ArtistasSeguidosActivity.this, ArtistasActivity.class);
        i.putExtra("nombreUsuario", nombreUsuario);
        i.putExtra("idUsuario", idUsuario);
        startActivity(i);
    }

    public void clickDrawer(View view) {
        TabActivity.openDrawer(drawerLayout);
    }
    public void clickPerfil(View view) {
        TabActivity.redirectActivity(this, TabActivity.class);
    }
    public void clickArtistas(View view) {
        TabActivity.redirectActivity(this, ArtistasSeguidosActivity.class);
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

    @Override
    public void onResultBusquedaUsuario(Usuario usuario) {
        Log.d("ROOM", "onResultBusquedaUsuario ARTISTAS SEGUIDOS: " + usuario.getUsuarioId() + " " + usuario.getArtistasSeguidosList());
        usuarioActual = usuario;
        artistasUSUARIO = usuario.getArtistasSeguidosList();
        Log.d("ROOM", " ARTISTAS SEGUIDOS: " + artistasUSUARIO);
        Log.d("ROOM", " ARTISTAS SEGUIDOS TAMAÑO: " + artistasUSUARIO.size());

        ejecutar();

    }

    @Override
    public void onResultBusquedaArtistas(List<String> artistas) {
        //artistasListROOM = ListConverter.toIntegerList((artistas.get(0)));
        //Log.d("ROOM", " ARTISTAS SEGUIDOS: " + lista);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        repositorio.getUsuarioById(idUsuario);
        ejecutar();
    }


    public void ejecutar(){

        //repositorio.getArtistasSeguidos(idUsuario);

        adapter = new SeguidosAdapter(new SeguidosAdapter.AdapterListener() {
            @Override
            public void onSeguirClick(String artistaId) {
                /////////////////////////
                ////SACAR ARTISTA DE LA LISTA
            }

            @Override
            public void onArtistaClick(Artista artista) {
                Intent i = new Intent(ArtistasSeguidosActivity.this, PerfilArtistaActivity.class);
                i.putExtra("nombreArtista", artista.getNombre());
                i.putExtra("nombreUsuario", nombreUsuario);
                startActivity(i);
                // finish(); //////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////
            }
        });

        recyclerArtistasSeguidos.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerArtistasSeguidos.setHasFixedSize(true);
        recyclerArtistasSeguidos.setAdapter(adapter);


        if (artistasUSUARIO.size() < 1) {  //listaIdArtistas == null){listaIdArtistas.size() == 0
            Log.d("ROOM", "ENTRO A SIZE < 1 ");
            recyclerArtistasSeguidos.setVisibility(View.GONE);
            tv_sinResultados.setVisibility(View.VISIBLE);
            adapter.setArtistasSeguidos(null);

        } else {
            recyclerArtistasSeguidos.setVisibility(View.VISIBLE);
            tv_sinResultados.setVisibility(View.GONE);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.seatgeek.com/2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ArtistaAPI artistaAPI = retrofit.create(ArtistaAPI.class);

            for (String idArtista : artistasUSUARIO) {
                if (idArtista != null) {
                    Call<Artista> callSingleArtist = artistaAPI.getArtista(idArtista);
                    callSingleArtist.enqueue(new Callback<Artista>() {
                        @Override
                        public void onResponse(Call<Artista> call, Response<Artista> response) {
                            Artista artista = response.body();
                            artistasList.add(artista);
                            adapter.setArtistasSeguidos(artistasList);
                        }

                        @Override
                        public void onFailure(Call<Artista> call, Throwable t) {
                            Log.d("RETROFIT", "onFailure: error artistasAPI");
                        }
                    });
                }
            }
        }
    }
}
