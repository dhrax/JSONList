package com.example.jsonlistview;

public class Monumento {

    private String titulo;
    private String link;
    private float latitud, longitud;

    Monumento(String titulo, String link, float latitud, float longitud){
        this.titulo = titulo;
        this.link = link;
        this.latitud = latitud;
        this.longitud = longitud;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Monumento{" +
                "titulo='" + titulo + '\'' +
                ", link='" + link + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
