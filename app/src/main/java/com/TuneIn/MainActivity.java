package com.TuneIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity<et_email> extends AppCompatActivity {

    Button btn_ingresar;
    EditText et_email, et_contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_ingresar = findViewById(R.id.btn_ingresar);
        et_email = findViewById(R.id.et_email);
        et_contrasenia = findViewById(R.id.et_contraseña);

        //VALIDAR EMAIL
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validarEmail();
                }
            }
        });

        //VALIDAR CONTRASEÑA
        et_contrasenia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validarContrasenia();
                }
            }
        });


        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VALIDAR FINAL
                validar();
            }
        });
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

        //VALIDAR CAMPOS OBLIGATORIOS
        if(et_email.getText().toString().length()==0){
            et_email.startAnimation(shakeError());
            validado = false;
        }
        if(et_contrasenia.getText().toString().isEmpty()){
            et_contrasenia.startAnimation(shakeError());
            validado = false;
        }

        //VALIDAR
        if(validarContrasenia() && validarEmail() && validado){
            // Intent conciertoActivity = new Intent(getApplicationContext(), ConciertoActivity.class);
            // startActivity(conciertoActivity);
        }
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(3));
        return shake;
    }
}