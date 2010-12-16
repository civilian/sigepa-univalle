package Reportes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;

/**
 *
 * @author kelebra
 */

public class Reporte implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private Integer cantidad;

    public Reporte() {
    }

    public Reporte(String nombre) {
        this.nombre = nombre;
    }

    public Reporte(String nombre,Integer cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
