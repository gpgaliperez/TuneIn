package com.TuneIn.BDD;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.TuneIn.Entidades.Artista;

import java.util.List;


public class ArtistaViewModel extends AndroidViewModel {

    private Repositorio repositorio;
    private LiveData<List<Artista>> allNotes;

    public ArtistaViewModel(@NonNull Application application) {
        super(application);
        repositorio = new Repositorio(application);
        allNotes = repositorio.getArtistaList();
    }


    public void insert(Artista artista) {
        repositorio.insert(artista);
    }

    public void update(Artista artista) {
        repositorio.update(artista);
    }

    public void delete(Artista artista) {
        repositorio.delete(artista);
    }

    public void deleteAll() {
        repositorio.deleteAll();
    }

    public LiveData<List<Artista>> getAllArtistas() {
        return allNotes;
    }
}