package com.TuneIn.Extra;

public class Ubicacion {
    private String city;
    private String country;
    private String name;

    public Ubicacion(String city, String country, String id, String name) {
        this.city = city;
        this.country = country;
        this.name = name;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
