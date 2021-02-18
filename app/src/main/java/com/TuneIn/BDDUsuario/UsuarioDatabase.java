package com.TuneIn.BDDUsuario;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.TuneIn.Entidades.Usuario;


@Database(entities = {Usuario.class},  exportSchema = false, version = 1)

public abstract class UsuarioDatabase extends RoomDatabase {

    private static final String BDD_NAME = "artista.db";
    private static volatile com.TuneIn.BDDUsuario.UsuarioDatabase INSTANCE;

    public static com.TuneIn.BDDUsuario.UsuarioDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (com.TuneIn.BDDUsuario.UsuarioDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            com.TuneIn.BDDUsuario.UsuarioDatabase.class, BDD_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract UsuarioDAO usuarioDAO();
}
