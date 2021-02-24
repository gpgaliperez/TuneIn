package com.TuneIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilUsuarioActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView tv_nombreUsuario, tv_emailUsuario, tv_cambiarC, tv_cerrarS;
    AlertDialog.Builder builderCerrarSesion, builderCambiarContra;
    LayoutInflater inflater;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        // Inicialización
        drawerLayout = findViewById(R.id.drawer_layout);
        tv_cambiarC = findViewById(R.id.cambiarContra);
        tv_cerrarS = findViewById(R.id.cerrarSesion);
        tv_nombreUsuario = findViewById(R.id.tv_nombreUsuario);
        tv_emailUsuario = findViewById(R.id.tv_emailUsuario);
        inflater = this.getLayoutInflater();
        builderCerrarSesion = new AlertDialog.Builder(this);
        builderCambiarContra = new AlertDialog.Builder(this);
        user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        String nombreUsuario = user.getDisplayName();
        String emailUsuario = user.getEmail();
        tv_nombreUsuario.setText(nombreUsuario);
        tv_emailUsuario.setText(emailUsuario);

        TextView nombreUsuarioDrawer = findViewById(R.id.nombreUsuarioDrawer);
        TextView homeDrawer = findViewById(R.id.tv_1);
        ImageView homeDrawerImage = findViewById(R.id.iv_1);
        homeDrawer.setText(getString(R.string.drawerHome));
        homeDrawerImage.setImageResource(R.drawable.ic_baseline_home_24);
        nombreUsuarioDrawer.setText(nombreUsuario);


        //TODO PARA CERRAR SESIÓN

        // CERRAR SESIÓN
        tv_cerrarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builderCerrarSesion.setTitle(getString(R.string.cerrarSesion))
                        .setMessage(getString(R.string.cerrarSesionSub))
                        .setPositiveButton(getString(R.string.btnConfirmar), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }).setNegativeButton(getString(R.string.btnCancelar), null)
                        .create()
                        .show();
            }
        });

        // CAMBIAR CONTRASEÑA
        tv_cambiarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dialogo de alerta

                View view = inflater.inflate(R.layout.cambiar_contrasenia, null);

                builderCambiarContra.setTitle(getString(R.string.cambiarCTitulo))
                        .setView(view)
                        .setNegativeButton(getString(R.string.btnCancelar), null)
                        .setPositiveButton(getString(R.string.btnConfirmar), null);

                AlertDialog cambiarC_alert = builderCambiarContra.create();
                cambiarC_alert.show();
                cambiarC_alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText password = view.findViewById(R.id.contraseniaNueva);
                        EditText passwordR = view.findViewById(R.id.contraseniaNuevaR);


                        if (password.getText().length() < 6) {
                            password.setError(getString(R.string.error_contrasenia));
                            password.requestFocus();
                            return;
                        }
                        if (!(password.getText().toString()).equals(passwordR.getText().toString())) {
                            password.setError(getString(R.string.error_no_coinciden));
                            password.requestFocus();
                            return;
                        }

                        password.setError(null);


                        user.updatePassword(password.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(PerfilUsuarioActivity.this, getString(R.string.exitoCambioC), Toast.LENGTH_LONG).show();
                                cambiarC_alert.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PerfilUsuarioActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                cambiarC_alert.dismiss();
                            }
                        });

                    }
                });

            }
        });

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DRAWER
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