/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.pojo.respuestasPojos;

import modelo.pojo.Direccion;
import modelo.pojo.Direccion;

/**
 *
 * @author eduar
 */
public class RespuestaDireccion {

    private boolean error;
    private String contenido;
    private Direccion direccion;

    public RespuestaDireccion() {
    }

    public RespuestaDireccion(boolean error, String contenido, Direccion direccion) {
        this.error = error;
        this.contenido = contenido;
        this.direccion = direccion;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
    
    
}
