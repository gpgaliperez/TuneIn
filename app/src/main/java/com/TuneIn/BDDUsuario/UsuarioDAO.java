package com.TuneIn.BDDUsuario;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.TuneIn.Converters.ListConverter;
import com.TuneIn.Entidades.Usuario;

import java.util.List;
@Dao
public interface UsuarioDAO {

    @Query("DELETE FROM usuario")
    void deleteAllU();

    @Insert
    void insertU(Usuario usuario);

    @Update
    void updateU(Usuario usuario);

    @Delete
    void deleteU(Usuario usuario);

    @Query("SELECT * FROM usuario WHERE usuarioId = :id")
    Usuario getUsuarioById(String id);

    @TypeConverters(ListConverter.class)
    @Query("Select artistasSeguidosList from usuario WHERE usuarioId= :id")
    List<String> getArtistasSeguidos(String id);

    @Query("SELECT * FROM usuario")
    List<Usuario> getAllUsuarios();

}
