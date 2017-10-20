package com.tabian.tabfragments;

/**
 * Created by usuario on 04/08/2017.
 */

public class Aviso {

    private String nombre;
    private String descripcion;


    public Aviso() {
    }

    public String getNombre() {

        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }



    public Aviso(String nombre, String descripcion) {

        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
