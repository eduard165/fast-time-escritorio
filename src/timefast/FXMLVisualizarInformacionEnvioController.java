/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timefast;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.dao.ClienteDAO;
import modelo.dao.ColaboradorDAO;
import modelo.dao.DireccionesDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Colaborador;
import modelo.pojo.Envio;
import modelo.pojo.respuestasPojos.RespuestaDireccion;

/**
 * FXML Controller class
 *
 * @author eduar
 */
public class FXMLVisualizarInformacionEnvioController implements Initializable {

    @FXML
    private Label lbNumeroGuia;
    @FXML
    private Label lbCosto;
    @FXML
    private Label lbEstado;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbNombreCliente;
    @FXML
    private Label lbCorreoCliente;
    @FXML
    private Label lbTelefonoCliente;
    @FXML
    private Label lbCalleOrigen;
    @FXML
    private Label lbNumeroOrigen;
    @FXML
    private Label lbColoniaOrigen;
    @FXML
    private Label lbCodigoPostalOrigen;
    @FXML
    private Label lbMunicipioOrigen;
    @FXML
    private Label lbEstadoOrigen;
    @FXML
    private Label lbNombreColaborador;
    @FXML
    private Label lbNumeroPersonal;
    @FXML
    private Label lbCorreoColaborador;
    @FXML
    private Label lbEstadoDestino;
    @FXML
    private Label lbMunicipioDestino;
    @FXML
    private Label lbCodigoPostalDestino;
    @FXML
    private Label lbColoniaDestino;
    @FXML
    private Label lbNumeroDestino;
    @FXML
    private Label lbCalleDestino;

    Envio envio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializarInformacion(Envio envio) {
        this.envio = envio;
        datosNoNulos();
        obtenerCliente();
        obtenerColaborar();
        obtenerDireccionDestino();
        obtenerDireccionOrigen();
    }

    @FXML
    private void regresar(MouseEvent event) {
        Stage base = (Stage) lbCalleDestino.getScene().getWindow();
        base.close();
    }

    private void datosNoNulos() {
        lbNumeroGuia.setText(envio.getNumeroGuia());
        lbCosto.setText("$ " + envio.getCosto());
        lbEstado.setText(envio.getNombreEstadoEnvio());
        lbDescripcion.setText(envio.getDescripcion());

    }

    private void obtenerCliente() {
        Cliente cliente = ClienteDAO.obtenerClientesPorId(envio.getIdCliente());
        if (cliente != null) {
            lbNombreCliente.setText(cliente.getNombre() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno());
            lbCorreoCliente.setText(cliente.getCorreoElectronico());
            lbTelefonoCliente.setText(cliente.getTelefono());
        } else {
            lbNombreCliente.setText(envio.getNombreClienteCompleto());
            lbCorreoCliente.setText(envio.getCorreoElectronicoCliente());
            lbTelefonoCliente.setText(envio.getTelefonoCliente());
        }

    }

    private void obtenerColaborar() {
        if (envio.getIdColaborador() != null) {
            Colaborador colaborador = ColaboradorDAO.obtenerColaborador(envio.getIdColaborador());
            if (colaborador != null) {
                lbNombreColaborador.setText(colaborador.getNombre() + " " + colaborador.getApellidoPaterno() + " " + colaborador.getApellidoMaterno());
                lbCorreoColaborador.setText(colaborador.getCorreoElectronico());
                lbNumeroPersonal.setText(colaborador.getNumeroPersonal());
            } else {
                lbNombreColaborador.setText("Sin asignar");
                lbCorreoColaborador.setText("Sin asignar");
                lbNumeroPersonal.setText("Sin asignar");
            }
        } else {
            lbNombreColaborador.setText("Sin asignar");
            lbCorreoColaborador.setText("Sin asignar");
            lbNumeroPersonal.setText("Sin asignar");
        }
    }

    private void obtenerDireccionOrigen() {
        RespuestaDireccion direccion = DireccionesDAO.buscarDireccionOrigen(envio.getIdEnvio());
        if (direccion != null && direccion.getDireccion() != null) {
            lbCalleOrigen.setText(direccion.getDireccion().getCalle());
            lbNumeroOrigen.setText(direccion.getDireccion().getNumero());
            lbColoniaOrigen.setText(direccion.getDireccion().getColonia());
            lbCodigoPostalOrigen.setText(direccion.getDireccion().getCodigoPostal());
            lbMunicipioOrigen.setText(direccion.getDireccion().getMunicipio());
            lbEstadoOrigen.setText(direccion.getDireccion().getEstado());
        } else {
            lbCalleOrigen.setText("Sin asignar");
            lbNumeroOrigen.setText("Sin asignar");
            lbColoniaOrigen.setText("Sin asignar");
            lbCodigoPostalOrigen.setText("Sin asignar");
            lbMunicipioOrigen.setText("Sin asignar");
            lbEstadoOrigen.setText("Sin asignar");
        }
    }

    private void obtenerDireccionDestino() {
        RespuestaDireccion direccion = DireccionesDAO.buscarDireccionDestino(envio.getIdEnvio());
        if (direccion != null && direccion.getDireccion() != null) {
            lbCalleDestino.setText(direccion.getDireccion().getCalle());
            lbNumeroDestino.setText(direccion.getDireccion().getNumero());
            lbColoniaDestino.setText(direccion.getDireccion().getColonia());
            lbCodigoPostalDestino.setText(direccion.getDireccion().getCodigoPostal());
            lbMunicipioDestino.setText(direccion.getDireccion().getMunicipio());
            lbEstadoDestino.setText(direccion.getDireccion().getEstado());
        } else {
            lbCalleDestino.setText("Sin asignar");
            lbNumeroDestino.setText("Sin asignar");
            lbColoniaDestino.setText("Sin asignar");
            lbCodigoPostalDestino.setText("Sin asignar");
            lbMunicipioDestino.setText("Sin asignar");
            lbEstadoDestino.setText("Sin asignar");
        }
    }

}
