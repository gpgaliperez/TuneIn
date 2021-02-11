package com.TuneIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;



public class MainActivity extends AppCompatActivity {

    Button button_ingresar;

    Button btn_ingresarFinal;
    EditText et_email, et_contrasenia;
    TextView tv_registrarse, tv_olvidasteC;
    FirebaseAuth fAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización
        btn_ingresarFinal = findViewById(R.id.btn_ingresar);
        et_email = findViewById(R.id.et_email);
        et_contrasenia = findViewById(R.id.et_contrasenia);
        tv_registrarse = findViewById(R.id.tv_registrarseBtn);
        tv_olvidasteC = findViewById(R.id.tv_olvidasteContraBtn);

        fAuth = FirebaseAuth.getInstance();

        inflater = this.getLayoutInflater();
        reset_alert = new AlertDialog.Builder(this);

        // Ir a REGISTRARSEACTIVITY
        tv_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrarse = new Intent(getApplicationContext(), RegistrarseActivity.class);
                startActivity(registrarse);
            }
        });

        // CAMBIAR CONTRASEÑA
        tv_olvidasteC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dialogo de alerta

                View view = inflater.inflate(R.layout.reset_contrasenia, null);

                reset_alert
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText email = view.findViewById(R.id.email);

                                if(email.getText().toString().isEmpty()){
                                    email.setError("Complete su Email");
                                    return;
                                }

                                fAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Mensaje enviado", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancelar", null)
                        .setView(view)
                        .create().show();
            }
        });

        // VALIDAR EMAIL
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validarEmail();
                }
            }
        });

        // VALIDAR CONTRASEÑA
        et_contrasenia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validarContrasenia();
                }
            }
        });

        // VALIDAR FINAL
        btn_ingresarFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // SI YA ESTAN LOGUEADOS = entra directamente

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent yaTieneCuenta = new Intent(getApplicationContext(), Algo.class);
            startActivity(yaTieneCuenta);
            finish();
        }
    }


    public boolean validarEmail() {
        Pattern pattern_email = Patterns.EMAIL_ADDRESS;
        if(!pattern_email.matcher(et_email.getText().toString()).matches() && !et_email.getText().toString().isEmpty()){
            et_email.setError(getString(R.string.error_email));
            return false;
        }else return true;
    }

    public boolean validarContrasenia() {
        if(!(et_contrasenia.getText().length() == 0) && et_contrasenia.getText().length() <= 4 ) {
            et_contrasenia.setError(getString(R.string.error_contrasenia));
            return false;
        } else return true;
    }

    public void validar(){
        boolean validado = true;

        // VALIDAR CAMPOS OBLIGATORIOS
        if(et_email.getText().toString().length()==0){
            et_email.startAnimation(shakeError());
            validado = false;
        }
        if(et_contrasenia.getText().toString().isEmpty()){
            et_contrasenia.startAnimation(shakeError());
            validado = false;
        }

        // VALIDAR
        if(validarContrasenia() && validarEmail() && validado){

            String email = et_email.getText().toString();
            String password =  et_contrasenia.getText().toString();

            // FIREBASE
            fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MainActivity.this, getString(R.string.exito), Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), TestActivity.class);
                    startActivity(i);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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