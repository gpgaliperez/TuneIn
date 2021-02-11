package com.TuneIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.View;

public class OtraActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Para poder usar drawer:
        1 - Las activities tienen que ser DrawerLayout, adentro tienen un linear o lo que sea
        2 - Adentro del Linear, deben tener un "include" para la toolbar. Dsps iría toodo lo que tiene la actividad
        3 - El drawer en el XML debe tener el mismo id
        4 - Dsps del linear, va un Relative que solo tiene el include al navigation drawer
        5 - DrawerLayout drawerLayout y drawerLayout = findViewById(R.id.drawer_layout);
        6 - Los 6 métodos de click en el drawer llamando a los ya definidos, copiar y pegar
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra);

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void clickDrawer(View view){
        TabActivity.openDrawer(drawerLayout);
    }
    public void clickPerfil(View view){
        TabActivity.redirectActivity(this, TabActivity.class);
    }
    public void clickArtistas(View view){
        //TabActivity.redirectActivity(this, ArtistasActivity.class);
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