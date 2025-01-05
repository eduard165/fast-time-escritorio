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
public class Unidad {

    private Integer idUnidad;
    private String marca;
    private String modelo;
    private Integer anio;
    private String VIN;
    private Integer idTipoUnidad;
    private String numeroInterno;
    private String nombreTipo;
    private String nombreColaboradorCompleto;
    private Boolean activo;
    private String motivoBaja;

    public Unidad() {
    }

    public Unidad(Integer idUnidad, String marca, String modelo, Integer anio, String VIN, Integer idTipoUnidad, String numeroInterno, String nombreTipo, String nombreColaboradorCompleto, Boolean activo, String motivoBaja) {
        this.idUnidad = idUnidad;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.VIN = VIN;
        this.idTipoUnidad = idTipoUnidad;
        this.numeroInterno = numeroInterno;
        this.nombreTipo = nombreTipo;
        this.nombreColaboradorCompleto = nombreColaboradorCompleto;
        this.activo = activo;
        this.motivoBaja = motivoBaja;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public String getNombreColaboradorCompleto() {
        return nombreColaboradorCompleto;
    }

    public void setNombreColaboradorCompleto(String nombreColaboradorCompleto) {
        this.nombreColaboradorCompleto = nombreColaboradorCompleto;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public Integer getIdTipoUnidad() {
        return idTipoUnidad;
    }

    public void setIdTipoUnidad(Integer idTipoUnidad) {
        this.idTipoUnidad = idTipoUnidad;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }
}
