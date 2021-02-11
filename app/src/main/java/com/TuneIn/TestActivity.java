package com.TuneIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TestActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        drawerLayout= findViewById(R.id.drawer_layout);

    }

    public void clickDrawer(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void clickPerfil(View view){
        redirectActivity(this, OtraActivity.class);
    }

    public void clickArtistas(View view){
        //redirectActivity(this, ArtistasActivity.class);
    }

    public void clickConfiguracion(View view){
       //redirectActivity(this, ConfiguracionActivity.class);
    }

    public void clickSalir(View view){
        logout(this);
    }

    public static void logout(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Salir de la aplicación");
        builder.setMessage("¿Está seguro que desea salir?");
        builder.setPositiveButton("Sí", (dialogInterface, i) -> {
            activity.finishAffinity();
            System.exit(0);
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class thisClass){
        Intent intent = new Intent(activity, thisClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}