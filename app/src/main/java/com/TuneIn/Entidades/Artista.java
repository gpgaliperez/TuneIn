package com.TuneIn.Entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.TuneIn.Extra.Link;
import com.TuneIn.Extra.PerformerType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//TODO https://medium.com/mindorks/using-room-database-android-jetpack-675a89a0e942

@Entity(tableName = "artista")
public class Artista {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private String artistaId;
    // @ColumnInfo(name = "idAPI")
    // private int idAPI;
    @ColumnInfo(name = "nombre")
    @SerializedName("name")
    private String nombre;
    @SerializedName("url")
    @Expose
    private String urlTickets;
    private String image;
    private Link[] links;
    private PerformerType[] taxonomies;
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

    public Artista(String artistaId, String nombre, String urlTickets, String image, Link[] links, PerformerType[] taxonomies) {
        this.artistaId = artistaId;
        this.nombre = nombre;
        this.urlTickets = urlTickets;
        this.image = image;
        this.links = links;
        this.taxonomies = taxonomies;
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
    public String getUrlTickets() {
        return urlTickets;
    }
    public void setUrlTickets(String urlTickets) {
        this.urlTickets = urlTickets;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Link[] getLinks() {
        return links;
    }
    public void setLinks(Link[] links) {
        this.links = links;
    }
    public PerformerType[] getTaxonomies() {
        return taxonomies;
    }
    public void setTaxonomies(PerformerType[] taxonomies) {
        this.taxonomies = taxonomies;
    }
}
