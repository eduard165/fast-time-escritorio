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
public class Envio {

    private Integer idEnvio;
    private Integer idCliente;
    private String numeroGuia;
    private Float costo;
    private String descripcion;
    private Integer idDireccionOrigen;
    private Integer idDireccionDestino;
    private Integer idEstado;
    private Integer idColaborador;
    private String nombreEstado;

    private String nombreClienteCompleto;
    private String telefonoCliente;
    private String correoElectronicoCliente;

    private String calleOrigen;
    private String numeroOrigen;
    private String coloniaOrigen;
    private String codigoPostalOrigen;
    private String ciudadOrigen;
    private String estadoOrigen;

    private String calleDestino;
    private String numeroDestino;
    private String coloniaDestino;
    private String codigoPostalDestino;
    private String ciudadDestino;
    private String estadoDestino;

    private String nombreColaboradorCompleto;
    private String correoElectronicoColaborador;
    
    public Envio() {
    }

    public Envio(Integer idEnvio, Integer idCliente, String numeroGuia, Float costo, String descripcion, Integer idDireccionOrigen, Integer idDireccionDestino, Integer idEstado, Integer idColaborador, String nombreEstado, String nombreClienteCompleto, String telefonoCliente, String correoElectronicoCliente, String calleOrigen, String numeroOrigen, String coloniaOrigen, String codigoPostalOrigen, String ciudadOrigen, String estadoOrigen, String calleDestino, String numeroDestino, String coloniaDestino, String codigoPostalDestino, String ciudadDestino, String estadoDestino, String nombreColaboradorCompleto, String correoElectronicoColaborador) {
        this.idEnvio = idEnvio;
        this.idCliente = idCliente;
        this.numeroGuia = numeroGuia;
        this.costo = costo;
        this.descripcion = descripcion;
        this.idDireccionOrigen = idDireccionOrigen;
        this.idDireccionDestino = idDireccionDestino;
        this.idEstado = idEstado;
        this.idColaborador = idColaborador;
        this.nombreEstado = nombreEstado;
        this.nombreClienteCompleto = nombreClienteCompleto;
        this.telefonoCliente = telefonoCliente;
        this.correoElectronicoCliente = correoElectronicoCliente;
        this.calleOrigen = calleOrigen;
        this.numeroOrigen = numeroOrigen;
        this.coloniaOrigen = coloniaOrigen;
        this.codigoPostalOrigen = codigoPostalOrigen;
        this.ciudadOrigen = ciudadOrigen;
        this.estadoOrigen = estadoOrigen;
        this.calleDestino = calleDestino;
        this.numeroDestino = numeroDestino;
        this.coloniaDestino = coloniaDestino;
        this.codigoPostalDestino = codigoPostalDestino;
        this.ciudadDestino = ciudadDestino;
        this.estadoDestino = estadoDestino;
        this.nombreColaboradorCompleto = nombreColaboradorCompleto;
        this.correoElectronicoColaborador = correoElectronicoColaborador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public Integer getIdDireccionOrigen() {
        return idDireccionOrigen;
    }

    public void setIdDireccionOrigen(Integer idDireccionOrigen) {
        this.idDireccionOrigen = idDireccionOrigen;
    }

    public Integer getIdDireccionDestino() {
        return idDireccionDestino;
    }

    public void setIdDireccionDestino(Integer idDireccionDestino) {
        this.idDireccionDestino = idDireccionDestino;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getNombreClienteCompleto() {
        return nombreClienteCompleto;
    }

    public void setNombreClienteCompleto(String nombreClienteCompleto) {
        this.nombreClienteCompleto = nombreClienteCompleto;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getCorreoElectronicoCliente() {
        return correoElectronicoCliente;
    }

    public void setCorreoElectronicoCliente(String correoElectronicoCliente) {
        this.correoElectronicoCliente = correoElectronicoCliente;
    }

    public String getCalleOrigen() {
        return calleOrigen;
    }

    public void setCalleOrigen(String calleOrigen) {
        this.calleOrigen = calleOrigen;
    }

    public String getNumeroOrigen() {
        return numeroOrigen;
    }

    public void setNumeroOrigen(String numeroOrigen) {
        this.numeroOrigen = numeroOrigen;
    }

    public String getColoniaOrigen() {
        return coloniaOrigen;
    }

    public void setColoniaOrigen(String coloniaOrigen) {
        this.coloniaOrigen = coloniaOrigen;
    }

    public String getCodigoPostalOrigen() {
        return codigoPostalOrigen;
    }

    public void setCodigoPostalOrigen(String codigoPostalOrigen) {
        this.codigoPostalOrigen = codigoPostalOrigen;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(String estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public String getCalleDestino() {
        return calleDestino;
    }

    public void setCalleDestino(String calleDestino) {
        this.calleDestino = calleDestino;
    }

    public String getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(String numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    public String getColoniaDestino() {
        return coloniaDestino;
    }

    public void setColoniaDestino(String coloniaDestino) {
        this.coloniaDestino = coloniaDestino;
    }

    public String getCodigoPostalDestino() {
        return codigoPostalDestino;
    }

    public void setCodigoPostalDestino(String codigoPostalDestino) {
        this.codigoPostalDestino = codigoPostalDestino;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public String getNombreColaboradorCompleto() {
        return nombreColaboradorCompleto;
    }

    public void setNombreColaboradorCompleto(String nombreColaboradorCompleto) {
        this.nombreColaboradorCompleto = nombreColaboradorCompleto;
    }

    public String getCorreoElectronicoColaborador() {
        return correoElectronicoColaborador;
    }

    public void setCorreoElectronicoColaborador(String correoElectronicoColaborador) {
        this.correoElectronicoColaborador = correoElectronicoColaborador;
    }
    

}
