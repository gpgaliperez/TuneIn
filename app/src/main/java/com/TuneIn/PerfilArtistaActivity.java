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

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PerfilArtistaActivity extends AppCompatActivity {
    TextView tv_nombreArtista, tv_detalleArtista, tv_MasInfo, tv_spotify;
    ImageView imagenArtista, spotify_img;
    String urlArtista, nombreArtista,imgArtista, genres;
    DrawerLayout drawerLayout;


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


        Intent i = getIntent();
        nombreArtista = i.getExtras().getString("nombreArtista");
        urlArtista = i.getExtras().getString("urlArtista");
        imgArtista = i.getExtras().getString("imgArtista");
        genres = i.getExtras().getString("generosArtista");

        tv_nombreArtista.setText(nombreArtista);
        tv_detalleArtista.setText(genres);
        Glide.with(this).load(imgArtista).into(imagenArtista);
    }

    public void clickMasInfo(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlArtista));
        startActivity(intent);
    }
    public void clickDrawer(View view){
        TabActivity.openDrawer(drawerLayout);
    }
    public void clickPerfil(View view){
        TabActivity.redirectActivity(this, TabActivity.class);
    }
    public void clickArtistas(View view){
        TabActivity.redirectActivity(this, AristasSeguidosActivity.class);
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