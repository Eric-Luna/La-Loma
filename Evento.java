package com.tabian.tabfragments;

import static android.os.Build.ID;

/**
 * Created by usuario on 01/08/2017.
 */

public class Evento {

    private String nombre;
    private String descripcion;
    private String fecha="";
    private int hora;
    public int minutos;
    private String rama;

    public Evento(String nombre, String descripcion, String fecha, int hora, int minutos, String rama) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.minutos = minutos;
        this.rama = rama;
    }

    public Evento(){};



    public String getNombre()
    {return nombre;}

    public String getDescripcion() {

        return descripcion;
    }

    public int getHora() {
        return hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public String getRama() {
        return rama;
    }

    public String getFecha() {
        return fecha;
    }
}
