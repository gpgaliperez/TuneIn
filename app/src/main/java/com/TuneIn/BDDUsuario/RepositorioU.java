package com.TuneIn.BDDUsuario;


import android.app.Application;
import android.os.AsyncTask;

import com.TuneIn.Entidades.Usuario;

import java.util.List;

public class RepositorioU {


    private List<String> listaArtistasDeUsuario;
    private static UsuarioDAO usuarioDAO;
    private UsuarioDatabase database;
    private static OnResultCallback result;

    //https://stackoverflow.com/questions/6053602/what-arguments-are-passed-into-asynctaskarg1-arg2-arg3

    public RepositorioU(Application application, OnResultCallback context) {
        this.database = UsuarioDatabase.getInstance(application);
        usuarioDAO = database.usuarioDAO();
        result = context;

    }


    /////////////////////////////////////////////////////////

    public interface OnResultCallback{
        void onResultBusquedaUsuario(Usuario usuario);
        void onResultBusquedaArtistas(List<String> artistas);
    }

    /////////////////////////////////////////////////////////


    public void insert(Usuario usuario) {
        new InsertarU().execute(usuario);
    }

    public void getAllUsuarios() {
        new getAllUsuarios().execute();
    }

    public void getArtistasSeguidos(String idU) {
        new getArtistasSeguidos().execute(idU);
    }

    public void getUsuarioById(String idU) {
        new getUsuarioById().execute(idU);
    }

    public void deleteAll() {
        new deleteAll().execute();
    }

    public void update(Usuario usuario) { new UpdateU().execute(usuario); }


    private static class getUsuarioById extends AsyncTask<String, Void, Usuario> {

        @Override
        protected Usuario doInBackground(String... strings) {
            String idUsuario = strings[0];
            return usuarioDAO.getUsuarioById(idUsuario);
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            super.onPostExecute(usuario);
            result.onResultBusquedaUsuario(usuario);
        }
    }

    private static class getArtistasSeguidos extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... strings) {
            String idUsuario = strings[0];
            return usuarioDAO.getArtistasSeguidos(idUsuario);
        }

        @Override
        protected void onPostExecute(List<String> artistas) {
            super.onPostExecute(artistas);
            result.onResultBusquedaArtistas(artistas);
        }
    }

    private class getAllUsuarios extends AsyncTask<Void, Void, List<Usuario>> {

        @Override
        protected List<Usuario> doInBackground(Void... Void) {
            List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
            return usuarios;
        }

        @Override
        protected void onPostExecute(List<Usuario> usuarios) {
            super.onPostExecute(usuarios);
        }
    }


    private class InsertarU extends AsyncTask<Usuario, Void, Void> {
        @Override
        protected Void doInBackground(Usuario... usuarios) {
            usuarioDAO.insertU(usuarios[0]);
            return null;
        }
    }

    private class UpdateU extends AsyncTask<Usuario, Void, Void> {
        @Override
        protected Void doInBackground(Usuario... usuarios) {
            usuarioDAO.updateU(usuarios[0]);
            return null;
        }
    }



    private class deleteAll extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            usuarioDAO.deleteAllU();
            return null;
        }
    }
}