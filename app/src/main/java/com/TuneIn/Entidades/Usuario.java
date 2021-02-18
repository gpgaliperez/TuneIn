package com.TuneIn.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.TuneIn.Converters.ListConverter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "usuario")
public class Usuario {
    @PrimaryKey
    @NonNull
    private String usuarioId;
    @TypeConverters({ListConverter.class})
    private List<Integer> artistasSeguidosList;

    public Usuario(@NonNull String usuarioId) {
        this.usuarioId = usuarioId;
        this.artistasSeguidosList = new ArrayList<>();
    }

    @NotNull
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Integer> getArtistasSeguidosList() {
        return artistasSeguidosList;
    }

    public void setArtistasSeguidosList(List<Integer> artistasSeguidos) {
        this.artistasSeguidosList = artistasSeguidos;
    }
}