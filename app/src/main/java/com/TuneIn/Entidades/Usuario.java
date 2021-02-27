package com.TuneIn.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.TuneIn.Converters.ListConverter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "usuario")
public class Usuario {
    @PrimaryKey
    @NonNull
    private String usuarioId;

    @TypeConverters(ListConverter.class)
    @SerializedName("artistasSeguidosList")
    private List<String> artistasSeguidosList;

    public Usuario(@NonNull String usuarioId ) {
        this.usuarioId = usuarioId;
        this.artistasSeguidosList = new ArrayList<>();
    }


    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<String> getArtistasSeguidosList() {
        return artistasSeguidosList;
    }

    public void setArtistasSeguidosList(List<String> artistasSeguidos) {
        this.artistasSeguidosList = artistasSeguidos;
    }
}