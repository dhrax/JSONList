package com.example.jsonlistview;

public class Monumento {

    String nombre, descripcion, tipo;
    int puntuacion;

    Monumento(String nombre, String descripcion, String tipo, int puntuacion){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "Monumento{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
