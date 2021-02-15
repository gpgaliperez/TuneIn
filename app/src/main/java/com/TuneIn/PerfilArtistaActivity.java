package com.TuneIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class PerfilArtistaActivity extends AppCompatActivity {
    TextView tv_nombrePerfil;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_artista);

        tv_nombrePerfil = findViewById(R.id.tv_nombrePerfil);

        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout);

        // Extraer data del recycler
        Intent intent = getIntent();
        tv_nombrePerfil.setText(intent.getStringExtra("nombre"));



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