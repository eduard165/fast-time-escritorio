/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.pojo.respuestasPojos;

import modelo.pojo.Colaborador;
import modelo.pojo.Colaborador;

/**
 *
 * @author eduar
 */
public class RespuestaColaborador {
    private boolean error;
    private String contenido;
    private Colaborador colaborador;

    public RespuestaColaborador() {
    }

    public RespuestaColaborador(boolean error, String contenido, Colaborador colaborador) {
        this.error = error;
        this.contenido = contenido;
        this.colaborador = colaborador;
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

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
    
}
