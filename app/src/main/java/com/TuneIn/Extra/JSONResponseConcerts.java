package com.TuneIn.Extra;

import com.TuneIn.Entidades.Concierto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class JSONResponseConcerts {
    @SerializedName("events")
    @Expose
    private ArrayList<Concierto> conciertosArray;

    public ArrayList<Concierto> getConciertosArray() {
        return conciertosArray;
    }
    public void setConciertosArray(ArrayList<Concierto> conciertosArray) {
        this.conciertosArray = conciertosArray;
    }

    public ArrayList<Concierto> metodoParaFiltrar() {
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
        return conciertosArray;
    }
    //Tal vez no es necesario, solo trae upcoming events?
}
