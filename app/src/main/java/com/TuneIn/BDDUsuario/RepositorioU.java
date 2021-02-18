package com.TuneIn.BDDUsuario;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import com.TuneIn.Entidades.Usuario;
import com.TuneIn.Entidades.UsuarioConArtistas;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RepositorioU {

    private static  com.TuneIn.BDDUsuario.RepositorioU INSTANCE;
    private LiveData<List<Integer>> listaArtistaDeUsuario;
    private UsuarioDAO usuarioDAO;
    private UsuarioDatabase database;
    private String usuarioId;

    public  RepositorioU(Application application, String uId){
        this.database = UsuarioDatabase.getInstance(application);
        this.usuarioDAO = database.usuarioDAO();
        this.usuarioId = uId;
        this.listaArtistaDeUsuario = usuarioDAO.getArtistasSeguidos(uId);
    }

    /////
    public void insert(Usuario usuario){
        new RepositorioU.InsertUsuarioAsyncTask(usuarioDAO).execute(usuario);
    }

    public void update(Usuario usuario){
        new RepositorioU.UpdateUsuarioAsyncTask(usuarioDAO).execute(usuario);
    }

    public void deleteAll(){
        new RepositorioU.DeleteAllUsuarioAsyncTask(usuarioDAO).execute();
    }

    public void delete(Usuario usuario){
        new RepositorioU.DeleteUsuarioAsyncTask(usuarioDAO).execute(usuario);
    }

    public List<Usuario> getAllUsuarios() throws ExecutionException, InterruptedException {return new RepositorioU.GetUsuariosIdAsyncTask(usuarioDAO).execute().get(); }


    public Usuario getUsuarioById(String idUsuario) throws ExecutionException, InterruptedException {
        String id = idUsuario;
        return new GetUsuarioByIdAsyncTask(usuarioDAO, id).execute().get();
    }

    public LiveData<List<Integer>> getArtistasSeguidos() {
        Log.d("ROOM", "getArtistasDeUsuario REPOSITORIO: "+ usuarioId);
        return listaArtistaDeUsuario; }

    /////

    private static class InsertUsuarioAsyncTask extends AsyncTask<Usuario, Void, Void> {

        private final UsuarioDAO usuarioDAO;

        public InsertUsuarioAsyncTask(UsuarioDAO usuarioDAO) {
            this.usuarioDAO = usuarioDAO;
        }

        @Override
        protected Void doInBackground(Usuario... usuarios) {
            usuarioDAO.insertU(usuarios[0]);
            return null;
        }
    }

    private static class UpdateUsuarioAsyncTask extends AsyncTask<Usuario, Void, Void>{

        private final UsuarioDAO usuarioDAO;

        public UpdateUsuarioAsyncTask(UsuarioDAO usuarioDAO) {
            this.usuarioDAO = usuarioDAO;
        }

        @Override
        protected Void doInBackground(Usuario... usuarios) {
            usuarioDAO.updateU(usuarios[0]);
            return null;
        }
    }

    private static class DeleteUsuarioAsyncTask extends AsyncTask<Usuario, Void, Void>{

        private final UsuarioDAO usuarioDAO;

        public DeleteUsuarioAsyncTask(UsuarioDAO usuarioDAO) {
            this.usuarioDAO = usuarioDAO;
        }

        @Override
        protected Void doInBackground(Usuario... usuarios) {
            usuarioDAO.deleteU(usuarios[0]);
            return null;
        }
    }

    private static class DeleteAllUsuarioAsyncTask extends AsyncTask<Void, Void, Void>{

        private final UsuarioDAO usuarioDAO;

        public DeleteAllUsuarioAsyncTask(UsuarioDAO usuarioDAO) {
            this.usuarioDAO = usuarioDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            usuarioDAO.deleteAllU();
            return null;
        }

    }


    private static class GetUsuarioByIdAsyncTask extends AsyncTask<Void, Void, Usuario> {

        private final UsuarioDAO usuarioDAO;
        private String id;

        public GetUsuarioByIdAsyncTask(UsuarioDAO usuarioDAO, String id) {
            this.usuarioDAO = usuarioDAO;
            this.id = id;
        }

        @Override
        protected Usuario doInBackground(Void... voids) {
            return usuarioDAO.getUsuarioById(id);
        }
    }




    private static class  GetUsuariosIdAsyncTask extends AsyncTask<Void, Void, List<Usuario>> {

        private final UsuarioDAO usuarioDAO;


        public  GetUsuariosIdAsyncTask(UsuarioDAO usuarioDAO) {
            this.usuarioDAO = usuarioDAO;

        }
        @Override
        protected List<Usuario> doInBackground(Void... voids) {

            return usuarioDAO.getAllUsuarios();
        }
    }
}
