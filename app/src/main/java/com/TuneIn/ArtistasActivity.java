package com.TuneIn;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.TuneIn.Adapters.AllArtistasAdapter;
import com.TuneIn.Adapters.SeguidosAdapter;
import com.TuneIn.BDDUsuario.RepositorioU;
import com.TuneIn.Entidades.Artista;
import com.TuneIn.Entidades.Usuario;
import com.TuneIn.Extra.Artista.Genero;
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

public class ArtistasActivity extends AppCompatActivity implements RepositorioU.OnResultCallback {
    String nombreUsuario, idUsuario, nombreArtista, imagenArtista;
    EditText search;
    TextView tv_sinResultados;
    RecyclerView recyclerArtistas;
    DrawerLayout drawerLayout;
    AllArtistasAdapter adapter;
    List<Artista> artistasList;
    List<String> artistasUSUARIO;
    Usuario usuarioActual;
    RepositorioU repositorio;
    String generos = "";


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
        TextView homeDrawer = findViewById(R.id.tv_2);
        ImageView homeDrawerImage = findViewById(R.id.iv_2);
        homeDrawer.setText(getString(R.string.drawerHome));
        homeDrawerImage.setImageResource(R.drawable.ic_baseline_home_24);
        nombreUsuarioDrawer.setText(nombreUsuario);

        // Crear Repositorio
        repositorio = new RepositorioU(getApplication(), this);
        repositorio.getUsuarioById(idUsuario);

        artistasUSUARIO = new ArrayList<>();
        artistasList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.seatgeek.com/2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArtistaAPI artistaAPI = retrofit.create(ArtistaAPI.class);
        Call<JSONResponse> callAll = artistaAPI.getArtistas("concerts", "id.asc", 5000, 1);

        callAll.enqueue(new Callback<JSONResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                artistasList = new ArrayList<>(jsonResponse.getArtistasArray());
                ////
                artistasList.removeIf(artista -> artista.getArtistaId().equals("33"));
                for(String idArtista: artistasUSUARIO){
                    artistasList.removeIf(artista -> artista.getArtistaId().equals(idArtista));
                }
                ////
                PutDataIntoRecyclerView(artistasList);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });

        // BUSQUEDA
        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });

    }

    private void filter(String text){
        List<Artista> artistasResultado = new ArrayList();
        for(Artista a: artistasList){
            if(a.getNombre().toLowerCase().contains(text.toLowerCase())){
                artistasResultado.add(a);
            }
        }
        //update recyclerview
        adapter.updateList(artistasResultado);
    }

    private void PutDataIntoRecyclerView(List<Artista> artistasList) {
        findViewById(R.id.tv_cargando).setVisibility(View.GONE);
        findViewById(R.id.search).setVisibility(View.VISIBLE);
        adapter = new AllArtistasAdapter(this, artistasList, new SeguidosAdapter.AdapterListener() {
            @Override
            public void onSeguirClick(Artista a) throws ExecutionException, InterruptedException {

                if(!usuarioActual.getArtistasSeguidosList().contains(a.getArtistaId())){
                    usuarioActual.getArtistasSeguidosList().add(a.getArtistaId());

                    Log.d("ROOM", "Artista de id " + a.getArtistaId() +" SEGUIDO");

                    Toast.makeText(getApplicationContext(),  "Ahora sigues a " + a.getNombre() , Toast.LENGTH_LONG).show();

                    Log.d("ROOM", "Artista de id " + usuarioActual.getArtistasSeguidosList());
                    repositorio.update(usuarioActual);

                }
            }

            @Override
            public void onArtistaClick(Artista artista) {
                Intent i = new Intent(ArtistasActivity.this, PerfilArtistaActivity.class);
                i.putExtra("nombreArtista", artista.getNombre());
                i.putExtra("urlArtista", artista.getUrlTickets());
                i.putExtra("imgArtista", artista.getImage());
                i.putExtra("idArtista", artista.getArtistaId());
                boolean first = true;
                for(Genero genero : artista.getGenres()){
                    if(first) generos = genero.getName();
                    else generos = generos + ", " + genero.getName();
                    first = false;
                }
                i.putExtra("generosArtista", generos);
                startActivity(i);
            }
        });

        recyclerArtistas.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerArtistas.setHasFixedSize(true);
        recyclerArtistas.setAdapter(adapter);
    }


    // DRAWER
    public void clickDrawer(View view) {
        TabActivity.openDrawer(drawerLayout);
    }
    public void clickPerfil(View view) {
        TabActivity.redirectActivity(this, PerfilUsuarioActivity.class);
    }
    public void clickArtistas(View view) {
        TabActivity.redirectActivity(this, TabActivity.class);
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

    //////////

    // ROOM
    @Override
    public void onResultBusquedaUsuario(Usuario usuario) {
        usuarioActual = usuario;
        artistasUSUARIO = usuario.getArtistasSeguidosList();
    }

    @Override
    public void onResultBusquedaArtistas(List<String> artistas) {

    }
    //////////


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ArtistasActivity.this, ArtistasSeguidosActivity.class);
        i.putExtra("nombreUsuario", nombreUsuario);
        i.putExtra("idUsuario", idUsuario);
        startActivity(i);
    }
}

