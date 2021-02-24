package com.TuneIn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuneIn.Adapters.ConciertosAdapter;
import com.TuneIn.Entidades.Concierto;
import com.TuneIn.Extra.JSONResponseConcerts;
import com.TuneIn.Interfaces.ArtistaAPI;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilArtistaActivity extends AppCompatActivity {
    TextView tv_nombreArtista, tv_detalleArtista, tv_MasInfo, tv_spotify, tv_sinResultados;
    ImageView imagenArtista, spotify_img;
    String urlArtista, nombreArtista, imgArtista, genres, idArtista;
    DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    ConciertosAdapter adapter;
    static List<Concierto> conciertosList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_artista);

        drawerLayout = findViewById(R.id.drawer_layout);
        imagenArtista = findViewById(R.id.img_artista);
        tv_nombreArtista = findViewById(R.id.tv_nombreArtista);
        tv_MasInfo = findViewById(R.id.tv_MasInfo);
        tv_detalleArtista = findViewById(R.id.tv_detalleArtista);
        tv_spotify = findViewById(R.id.tv_spotify);
        spotify_img = findViewById(R.id.spotify_img);
        mRecyclerView = findViewById(R.id.recyclerview);
        tv_sinResultados = findViewById(R.id.tv_sinResultados);


        Intent i = getIntent();
        nombreArtista = i.getExtras().getString("nombreArtista");
        urlArtista = i.getExtras().getString("urlArtista");
        imgArtista = i.getExtras().getString("imgArtista");
        genres = i.getExtras().getString("generosArtista");
        idArtista = i.getExtras().getString("idArtista");

        tv_nombreArtista.setText(nombreArtista);
        tv_detalleArtista.setText(genres);
        Glide.with(this).load(imgArtista).into(imagenArtista);

        adapter = new ConciertosAdapter(new ConciertosAdapter.AdapterListener() {
            @Override
            public void onComprarClick(Concierto concierto) throws ExecutionException, InterruptedException {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(concierto.getUrl()));
                startActivity(intent);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        conciertosList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.seatgeek.com/2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArtistaAPI artistaAPI = retrofit.create(ArtistaAPI.class);
        Call<JSONResponseConcerts> callArtistaConcierto = artistaAPI.getConciertos(idArtista, 20);

        callArtistaConcierto.enqueue(new Callback<JSONResponseConcerts>() {
            @Override
            public void onResponse(Call<JSONResponseConcerts> call, Response<JSONResponseConcerts> response) {
                JSONResponseConcerts jsonResponseConcerts = response.body();
                conciertosList = new ArrayList<>(jsonResponseConcerts.getConciertosArray());
                if (conciertosList.isEmpty()) {
                    tv_sinResultados.setVisibility(View.VISIBLE);
                    adapter.setConciertos(null);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    tv_sinResultados.setVisibility(View.GONE);
                    adapter.setConciertos(conciertosList);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JSONResponseConcerts> call, Throwable t) {
                Log.d("ROOM", "ERRRRRRRRRRRRRRRRROR ");
            }
        });
    }

    public void clickMasInfo(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlArtista));
        startActivity(intent);
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
}