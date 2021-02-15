package com.TuneIn.BDD;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.TuneIn.Entidades.Artista;
import java.util.List;

public class Repositorio {
    private ArtistaDao artistaDao;
    private LiveData<List<Artista>> listaArtistas;

    public Repositorio(Application application) {
        ArtistaDatabase database = ArtistaDatabase.getInstance(application);
        artistaDao = database.artistaDao();
        listaArtistas = artistaDao.getArtistaList();
    }

    public void insert(Artista artista){
        new InsertArtistaAsyncTask(artistaDao).execute(artista);
    }

    public LiveData<List<Artista>> getArtistaList() {
        return listaArtistas;
    }

    public void update(Artista artista){
        new UpdateArtistaAsyncTask(artistaDao).execute(artista);
    }

    public void deleteAll(){
        new DeleteAllArtistaAsyncTask(artistaDao).execute();
    }

    public void delete(Artista artista){
        new DeleteArtistaAsyncTask(artistaDao).execute();
    }



    private static class InsertArtistaAsyncTask extends AsyncTask<Artista, Void, Void>{

        private ArtistaDao artistaDao;

        public InsertArtistaAsyncTask(ArtistaDao artistaDao) {
            this.artistaDao = artistaDao;
        }

        @Override
        protected Void doInBackground(Artista... artistas) {
            artistaDao.insert(artistas[0]);
            return null;
        }
    }

    private static class UpdateArtistaAsyncTask extends AsyncTask<Artista, Void, Void>{

        private ArtistaDao artistaDao;

        public UpdateArtistaAsyncTask(ArtistaDao artistaDao) {
            this.artistaDao = artistaDao;
        }

        @Override
        protected Void doInBackground(Artista... artistas) {
            artistaDao.update(artistas[0]);
            return null;
        }
    }

    private static class DeleteArtistaAsyncTask extends AsyncTask<Artista, Void, Void>{

        private ArtistaDao artistaDao;

        public DeleteArtistaAsyncTask(ArtistaDao artistaDao) {
            this.artistaDao = artistaDao;
        }

        @Override
        protected Void doInBackground(Artista... artistas) {
            artistaDao.delete(artistas[0]);
            return null;
        }
    }

    private static class DeleteAllArtistaAsyncTask extends AsyncTask<Void, Void, Void>{

        private ArtistaDao artistaDao;

        public DeleteAllArtistaAsyncTask(ArtistaDao artistaDao) {
            this.artistaDao = artistaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            artistaDao.deleteAll();
            return null;
        }


    }

}
