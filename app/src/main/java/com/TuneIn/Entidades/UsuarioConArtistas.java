package com.TuneIn.Entidades;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;
// TODO https://stackoverflow.com/questions/44361824/how-can-i-represent-a-many-to-many-relation-with-android-room-when-column-name/44428451#44428451
public class UsuarioConArtistas {
    @Embedded
    public Usuario usuario;

    @Relation(
            parentColumn = "usuarioId",
            //entity = Artista.class,
            entityColumn = "artistaId",
            associateBy = @Junction(
                    value = UsuarioArtistasEntity.class)
                   // parentColumn = "usuarioId",
                   // entityColumn = "artistaId")
    )
    public List<Artista> artistas;

    public UsuarioConArtistas(Usuario usuario) {
        this.usuario = usuario;
        this.artistas = null;
    }

    public List<Artista> getArtistas() {
        return artistas;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setArtistas(List<Artista> artistas) {
        this.artistas = artistas;
    }
}

