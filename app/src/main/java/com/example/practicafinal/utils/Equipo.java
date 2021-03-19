package com.example.practicafinal.utils;

import java.io.Serializable;

public class Equipo implements Serializable {

    String imagen,imagen2,nombre,texto,id,twit,face,web,insta;

    public Equipo(String imagen, String imagen2, String nombre, String texto, String id, String twit, String face, String web, String insta) {
        this.imagen = imagen;
        this.imagen2 = imagen2;
        this.nombre = nombre;
        this.texto = texto;
        this.id = id;
        this.twit = twit;
        this.face = face;
        this.web = web;
        this.insta = insta;
    }
    public Equipo(){

    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTwit() {
        return twit;
    }

    public void setTwit(String twit) {
        this.twit = twit;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "imagen='" + imagen + '\'' +
                ", imagen2='" + imagen2 + '\'' +
                ", nombre='" + nombre + '\'' +
                ", texto='" + texto + '\'' +
                ", id='" + id + '\'' +
                ", twit='" + twit + '\'' +
                ", face='" + face + '\'' +
                ", web='" + web + '\'' +
                ", insta='" + insta + '\'' +
                '}';
    }
}
