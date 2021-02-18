package com.TuneIn.BDDUsuario;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.TuneIn.Entidades.Usuario;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsuarioViewModel  extends AndroidViewModel {

    private final RepositorioU repositorioU;
    private LiveData<List<Integer>> listaArtistasSeguidos;
    private String usuarioId;

    public UsuarioViewModel(Application application, String uId){
        super(application);
        this.repositorioU = new RepositorioU(application, uId);
        this.usuarioId = uId;
        this.listaArtistasSeguidos = repositorioU.getArtistasSeguidos();
    }

    ////

    public void insert(Usuario usuario) {
        repositorioU.insert(usuario);
    }

    public void update(Usuario usuario) { repositorioU.update(usuario);  }

    public void delete(Usuario usuario) {
        repositorioU.delete(usuario);
    }

    public void deleteAllU() {
        repositorioU.deleteAll();
    }

    public Usuario getUsuarioById(String idUsuario) throws ExecutionException, InterruptedException {
        return repositorioU.getUsuarioById(idUsuario); }

    public List<Usuario> getAllUsuarios() throws ExecutionException, InterruptedException {
        return repositorioU.getAllUsuarios(); }


    public LiveData<List<Integer>> getListaArtistasSeguidos() { return listaArtistasSeguidos; }


}
