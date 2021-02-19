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

import com.TuneIn.Adapters.SeguidosAdapter;
import com.TuneIn.BDDUsuario.UsuarioViewModel;
import com.TuneIn.BDDUsuario.VMFactory;
import com.TuneIn.Entidades.Artista;
import com.TuneIn.Entidades.Usuario;
import com.TuneIn.Extra.JSONResponse;
import com.TuneIn.Interfaces.ArtistaAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AristasSeguidosActivity extends AppCompatActivity {
    String nombreUsuario, idUsuario;
    Button btn_verArtistas;
    TextView tv_sinResultados;
    RecyclerView recyclerArtistasSeguidos;
    DrawerLayout drawerLayout;
    SeguidosAdapter adapter;
    List<Artista> artistasList;
    public static UsuarioViewModel viewModel;

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

        // Crear Factory para pasarle parametros al ViewModel y poder utilizarlos en la Query
        VMFactory vmFactory = new VMFactory(idUsuario, this.getApplication());
        viewModel = new ViewModelProvider(this, vmFactory).get(UsuarioViewModel.class);

        // Inicializar el adaptador
        adapter = new SeguidosAdapter(new SeguidosAdapter.AdapterListener() {
            @Override
            public void onSeguirClick(Artista artista) throws ExecutionException, InterruptedException {
                /////////////////////////
                /////////////////////////
                ////SACAR ARTISTA DE LA LISTA
            }

            @Override
            public void onArtistaClick(Artista artista) {
                Intent i = new Intent(AristasSeguidosActivity.this, PerfilArtistaActivity.class);
                i.putExtra("nombreArtista", artista.getNombre());
                i.putExtra("nombreUsuario", nombreUsuario);
                startActivity(i);
            }
        });

        recyclerArtistasSeguidos.setLayoutManager(new LinearLayoutManager(this));
        recyclerArtistasSeguidos.setHasFixedSize(true);
        recyclerArtistasSeguidos.setAdapter(adapter);

        artistasList = new ArrayList<>();
        viewModel.getListaArtistasSeguidos().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> listaIdArtistas) {
                try {
                    Log.d("ROOM", " ARTISTAS SEGUIDOS ACTIVITY: idUsuario:" + idUsuario + " Artistas seguidos: " + viewModel.getUsuarioById(idUsuario).getArtistasSeguidosList());
                    Log.d("ROOM", " ARTISTAS SEGUIDOS ACTIVITY: Tamaño LISTA SEGUIDOS " + listaIdArtistas.size());
                    Log.d("ROOM", " ARTISTAS SEGUIDOS ACTIVITY: Primero en la lista:" + listaIdArtistas.get(0));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (listaIdArtistas == null) {  //listaIdArtistas == null){listaIdArtistas.size() == 0
                    tv_sinResultados.setVisibility(View.VISIBLE);
                    adapter.setArtistasSeguidos(null);
                    recyclerArtistasSeguidos.setVisibility(View.GONE);
                } else {
                    tv_sinResultados.setVisibility(View.GONE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.seatgeek.com/2/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ArtistaAPI artistaAPI = retrofit.create(ArtistaAPI.class);

                    for (String idArtista : listaIdArtistas) {
                        if (idArtista != null) {
                            Call<Artista> callSingleArtist = artistaAPI.getArtista(idArtista);


                            callSingleArtist.enqueue(new Callback<Artista>() {
                                @Override
                                public void onResponse(Call<Artista> call, Response<Artista> response) {
                                    Artista artista = response.body();
                                    artistasList.add(artista);
                                }

                                @Override
                                public void onFailure(Call<Artista> call, Throwable t) {
                                }
                            });
                        }
                    }


                    adapter.setArtistasSeguidos(artistasList);
                    recyclerArtistasSeguidos.setVisibility(View.VISIBLE);
                }
            }
        });


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
        Intent i = new Intent(AristasSeguidosActivity.this, ArtistasActivity.class);
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
