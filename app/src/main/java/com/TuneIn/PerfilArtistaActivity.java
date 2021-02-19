package com.TuneIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class PerfilArtistaActivity extends AppCompatActivity {
    TextView tv_nombrePerfilA, url;
    ImageView imagenArtista;
    String nombreUsuario, nombreArtista;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_artista);

        drawerLayout = findViewById(R.id.drawer_layout);
        tv_nombrePerfilA = findViewById(R.id.tv_nombrePerfiA);
        imagenArtista = findViewById(R.id.imagen_PerfilArtista);


        // Extraer datas del recycler
        Intent i = getIntent();
        nombreUsuario = i.getExtras().getString("nombreUsuario");
        nombreArtista = i.getExtras().getString("nombreArtista");

        tv_nombrePerfilA.setText(i.getExtras().getString("nombreArtista"));
    }


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