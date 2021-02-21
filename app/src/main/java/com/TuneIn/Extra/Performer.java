package com.TuneIn.Extra;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Performer {
    @SerializedName("name")
    @Expose
    private String nombre;
    private String id;

    public Performer(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}

