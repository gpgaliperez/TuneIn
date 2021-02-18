package com.TuneIn.Entidades;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys={"usuarioId", "artistaId"})
public class UsuarioArtistasEntity {
    @NonNull
    @ColumnInfo(name = "usuarioId")
    public String usuarioId;
    @NonNull
    @ColumnInfo(name= "artistaId")
    public int artistaId;

    public UsuarioArtistasEntity(@NonNull String usuarioId, int artistaId) {
        this.usuarioId = usuarioId;
        this.artistaId = artistaId;
    }

    @NonNull
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(@NonNull String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getArtistaId() {
        return artistaId;
    }

    public void setArtistaId(int artistaId) {
        this.artistaId = artistaId;
    }
}