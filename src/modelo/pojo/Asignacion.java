/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.pojo;

/**
 *
 * @author eduar
 */
public class Asignacion {

    private Integer idAsignacion;
    private Integer idUnidad;
    private Integer idColaborador;


    public Asignacion() {
    }

    public Asignacion(Integer idAsignacion, Integer idUnidad, Integer idColaborador) {
        this.idAsignacion = idAsignacion;
        this.idUnidad = idUnidad;
        this.idColaborador = idColaborador;
    }

    public Integer getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(Integer idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    @Override
    public String toString() {
        return "Asignacion{" + "idAsignacion=" + idAsignacion + ", idUnidad=" + idUnidad + ", idColaborador=" + idColaborador + '}';
    }


    
}
