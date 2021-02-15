package com.TuneIn.BDD;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.TuneIn.Entidades.Artista;


@Database(entities = Artista.class, exportSchema = false, version = 1)

public abstract class ArtistaDatabase extends RoomDatabase {

    private static final String BDD_NAME = "artista.db";
    private static volatile ArtistaDatabase INSTANCE;



    public static ArtistaDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArtistaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArtistaDatabase.class, BDD_NAME)
                            .addCallback(roomCallnack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallnack = new RoomDatabase.Callback(){
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new LLenarBDDAsyncTask(INSTANCE).execute();
        }
    };

    private static class LLenarBDDAsyncTask extends AsyncTask<Void, Void, Void>{
        private ArtistaDao artistaDao;

        public LLenarBDDAsyncTask(ArtistaDatabase db) {
            this.artistaDao = db.artistaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            artistaDao.insert(new Artista("Harry STasdasdyles"));
            artistaDao.insert(new Artista("Niallasd asdasdHOran"));
            artistaDao.insert(new Artista("Perasdl JaasdasdasM"));
            artistaDao.insert(new Artista("The asdKillErs"));
            artistaDao.insert(new Artista("Ladyasdas GagA"));
            artistaDao.insert(new Artista("Chrisasdas CoOrnell"));
            artistaDao.insert(new Artista("Harry SasdasTyles"));
            artistaDao.insert(new Artista("Niallasd HOran"));
            artistaDao.insert(new Artista("Perasdl JasdM"));
            artistaDao.insert(new Artista("The KasdaillErs"));
            artistaDao.insert(new Artista("Ladyasd GagA"));
            artistaDao.insert(new Artista("Chris CasdoOrnell"));
            artistaDao.insert(new Artista("Harry asdSTyles"));
            artistaDao.insert(new Artista("Niallaasds HOran"));
            artistaDao.insert(new Artista("Perl JdaaM"));
            artistaDao.insert(new Artista("The KasdaillErs"));
            artistaDao.insert(new Artista("Lady GagA"));
            artistaDao.insert(new Artista("Chris doOrnell"));
            artistaDao.insert(new Artista("Harryasdasda asdSTyles"));
            artistaDao.insert(new Artista("Niall HasdasdOasd"));
            artistaDao.insert(new Artista("The KilasdasdlErs"));
            artistaDao.insert(new Artista("Harry STasdasdyles"));
            artistaDao.insert(new Artista("Niallasd asdasdHOran"));
            artistaDao.insert(new Artista("Perasdl JaasdasdasM"));
            artistaDao.insert(new Artista("The asdKillErs"));
            artistaDao.insert(new Artista("Ladyasdas GagA"));
            artistaDao.insert(new Artista("Chrisasdas CoOrnell"));
            artistaDao.insert(new Artista("Harry SasdasTyles"));
            artistaDao.insert(new Artista("Niallasd HOran"));
            artistaDao.insert(new Artista("Perasdl JasdM"));
            artistaDao.insert(new Artista("The KasdaillErs"));
            artistaDao.insert(new Artista("Ladyasd GagA"));
            artistaDao.insert(new Artista("Chris CasdoOrnell"));
            artistaDao.insert(new Artista("Harry asdSTyles"));
            artistaDao.insert(new Artista("Niallaasds HOran"));
            artistaDao.insert(new Artista("Perl JdaaM"));
            artistaDao.insert(new Artista("The KasdaillErs"));
            artistaDao.insert(new Artista("Lady GagA"));
            artistaDao.insert(new Artista("Chris doOrnell"));
            artistaDao.insert(new Artista("Harryasdasda asdSTyles"));
            artistaDao.insert(new Artista("Niall HasdasdOasd"));
            artistaDao.insert(new Artista("The KilasdasdlErs"));
            return null;
        }
    }
    public abstract ArtistaDao artistaDao();
}
