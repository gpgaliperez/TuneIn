package com.TuneIn.Entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import androidx.room.PrimaryKey;

//TODO https://medium.com/mindorks/using-room-database-android-jetpack-675a89a0e942

@Entity(tableName = "artista")
public class Artista {
    @PrimaryKey(autoGenerate = true)
    private String artistaId;
    // @ColumnInfo(name = "idAPI")
    // private int idAPI;
    @ColumnInfo(name = "nombre")
    private String nombre;


    /*@ColumnInfo(name= "imagen")
    private String imagen;
    @ColumnInfo(name= "urlSpotify")
    private String urlSpotify;*/



    public Artista( String nombre) {
        //this.idAPI = idAPI;
        this.nombre = nombre;
        /*this.imagen = imagen;
        this.urlSpotify = urlSpotify;*/
    }

   /* public int getIdAPI() {
        return idAPI;
    }*/

    public String getArtistaId() {
        return artistaId;
    }

    public void setArtistaId(String artistaId) {
        this.artistaId = artistaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
