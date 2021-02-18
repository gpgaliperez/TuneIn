package com.TuneIn.BDDUsuario;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;



public class VMFactory implements ViewModelProvider.Factory {
    String idU;
    Application app;

    public VMFactory(String idU, Application app) {
        this.idU = idU;
        this.app = app;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(UsuarioViewModel.class)){
            return (T) new UsuarioViewModel(app,idU);

        }
        throw new IllegalArgumentException("ViewModel not found");
    }
}