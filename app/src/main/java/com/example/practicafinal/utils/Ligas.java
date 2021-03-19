package com.example.practicafinal.utils;

import java.io.Serializable;

public class Ligas implements Serializable {


    String nombre,liga;

    public Ligas(String nombre, String liga) {
        this.nombre = nombre;
        this.liga = liga;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }


    @Override
    public String toString() {
        return "Ligas{" +
                "nombre='" + nombre + '\'' +
                ", liga='" + liga + '\'' +
                '}';
    }
}
