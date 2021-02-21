package com.TuneIn.Extra;

import com.TuneIn.Entidades.Artista;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class JSONResponse {
    @SerializedName("performers")
    @Expose
    private ArrayList<Artista> artistasArray;

    public ArrayList<Artista> getArtistasArray() {
        return artistasArray;
    }
    public void setArtistasArray(ArrayList<Artista> artistasArray) {
        this.artistasArray = artistasArray;
    }

    public ArrayList<Artista> metodoParaFiltrar() {
        /*
        ArrayList<Artist> artistsArray = new ArrayList<>();
        for(Artist artist : performersArray){
           boolean isMusician = false;
           for(PerformerType taxonomy : artist.getTaxonomies()){
               if(taxonomy.getName().equals("concert") || taxonomy.getName().equals("concerts")) isMusician = true;
           }
            if(isMusician) artistsArray.add(artist);
        }
        return artistsArray;
        */
        return artistasArray;
    }
}
