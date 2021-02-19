package com.TuneIn.BDDUsuario;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.TuneIn.Entidades.Usuario;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ViewModelGeneral extends AndroidViewModel {
    private final RepositorioU repositorioU;

    public ViewModelGeneral(@NonNull Application application) {
        super(application);
        repositorioU = new RepositorioU(application,null);
    }

    public void insert(Usuario usuario) {
        repositorioU.insert(usuario);
    }

    public List<Usuario> getAllUsuarios() throws ExecutionException, InterruptedException {
        return repositorioU.getAllUsuarios(); }


}