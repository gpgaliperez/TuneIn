package com.TuneIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.TuneIn.BDDUsuario.ViewModelGeneral;
import com.TuneIn.Entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class RegistrarseActivity extends AppCompatActivity {

    Button btn_registrarseFinal;
    EditText et_emailR, et_contraseniar, et_contraseniaR, et_usuarioR;
    TextView tv_iniciarSesBtn;
    ViewModelGeneral viewModel;

    //private static String nombreUsuario;
    // private static String idUsuario;

    FirebaseAuth fAuthR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        // Inicialización

        btn_registrarseFinal = findViewById(R.id.btn_registrarse);
        et_emailR = findViewById(R.id.et_email);
        et_usuarioR = findViewById(R.id.et_usuario);
        et_contraseniar = findViewById(R.id.et_contrasenia);
        et_contraseniaR = findViewById(R.id.et_contraseniaR);
        tv_iniciarSesBtn = findViewById(R.id.tv_iniciarSesBtn);
        // Firebase
        fAuthR = FirebaseAuth.getInstance();
        // ViewModel
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ViewModelGeneral.class);

        // Ir a MAINACTIVITY
        tv_iniciarSesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciarSesion = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(iniciarSesion);
            }
        });


        // VALIDAR EMAIL
        et_emailR.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validarEmail();
                }
            }
        });

        // HABILITAR REPETIR CONTRASEÑA UNA VEZ SE COMPLETÓ CONTRASEÑA
        et_contraseniar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password_string = et_contraseniar.getText().toString();

                //Si se había cargado la segunda contraseña y luego se actualiza la primera, borra el texto
                if (!et_contraseniaR.getText().toString().isEmpty() && password_string.isEmpty())
                    et_contraseniaR.setText("");

                //Des/habilitar entrada de segunda contraseña
                et_contraseniaR.setEnabled(!password_string.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        // VALIDAR CONTRASEÑAS
        et_contraseniaR.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validarContrasenia();
                }
            }
        });

        // VALIDAR FINAL REGISTRARSE
        btn_registrarseFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validar();
            }
        });
    }


    public boolean validarEmail() {
        Pattern pattern_email = Patterns.EMAIL_ADDRESS;
        if (!pattern_email.matcher(et_emailR.getText().toString()).matches() && !et_emailR.getText().toString().isEmpty()) {
            et_emailR.setError(getString(R.string.error_email));
            return false;
        } else return true;
    }

    public boolean validarContrasenia() {
        if (!et_contraseniar.getText().toString().equals(et_contraseniaR.getText().toString())) {
            et_contraseniaR.setError(getString(R.string.error_no_coinciden));
            return false;
        } else return true;
    }

    public void validar() {
        boolean validado = true;

        // VALIDAR CAMPOS OBLIGATORIOS
        if (et_emailR.getText().toString().length() == 0) {
            et_emailR.startAnimation(shakeError());
            validado = false;
        }
        if (et_contraseniar.getText().toString().isEmpty()) {
            et_contraseniar.startAnimation(shakeError());
            et_contraseniaR.startAnimation(shakeError());
            validado = false;
        }

        // VALIDAR FINAL
        if (validarContrasenia() && validarEmail() && validado) {
            String email = et_emailR.getText().toString();
            String password = et_contraseniar.getText().toString();

            // FIREBASE
            fAuthR.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {


                    //TODO PARA MODIFICAR FOTO Y NOMBRE USUARIO
                    ///https://firebase.google.com/docs/auth/android/manage-users?hl=es

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(et_usuarioR.getText().toString())
                            //.setPhotoUri()
                            .build();

                    assert user != null;
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("ROOM", "User profile updated.");
                                    }
                                }

                            });

                    // AGREGAR USUARIO A ROOM
                    Usuario u = new Usuario(user.getUid());
                    viewModel.insert(u);
                    Log.d("ROOM", "RegistrarseA - Nuevo Usuario en ROOM " + u.getUsuarioId());

                    try {
                        Usuario uObtenido = viewModel.getAllUsuarios().get(0);
                        Log.d("ROOM", " RegistrarseA - getAllUsuarios idUsuario:" + uObtenido.getUsuarioId() + " Artistas seguidos: " + uObtenido.getArtistasSeguidosList());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }


                    //

                    Toast.makeText(RegistrarseActivity.this, getString(R.string.exito), Toast.LENGTH_LONG).show();


                    Intent tabActivity = new Intent(getApplicationContext(), TabActivity.class);
                    startActivity(tabActivity);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegistrarseActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(3));
        return shake;
    }
}