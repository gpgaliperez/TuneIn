package com.TuneIn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.TuneIn.fragmentos.ConciertoFragment;
import com.TuneIn.fragmentos.FragmentoMapa;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TabActivity extends AppCompatActivity {
    private final int NUM_PAGES = 2;
    public ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private String[] titles = new String[]{"Conciertos", "Mapa"};

    private static FirebaseUser user;
    private static String nombreUsuario;
    private static String idUsuario;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        // Firebase User
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        idUsuario = user.getUid();
        Log.d("ROOM", "idUsuario en TabActivity desde Firebase " + user.getUid());

        // Drawer
        drawerLayout= findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.mypager);
        pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> tab.setText(titles[position])).attach();
    }

    private class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(FragmentActivity fa) {
            super(fa);
        }


        @Override
        public Fragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return ConciertoFragment.newInstance("Acá van los rtdos de la busqueda");
                }
                case 1: {

                    return FragmentoMapa.newInstance("fragment 2");
                }

                default:
                    return ConciertoFragment.newInstance("fragment 1, Default");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }


   @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
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
        redirectActivity(this, AristasSeguidosActivity.class);
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
        intent.putExtra("nombreUsuario", user.getDisplayName());
        intent.putExtra("idUsuario", idUsuario);

        activity.startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

}