/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timefast;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.dao.EnviosDAO;
import modelo.dao.PaquetesDAO;
import modelo.pojo.Envio;
import modelo.pojo.Mensaje;
import modelo.pojo.Paquete;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLFormularioPaquetesController implements Initializable {

    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfAlto;
    @FXML
    private TextField tfAncho;
    @FXML
    private TextField tfProfundidad;
    @FXML
    private Label lbErrorPeso;
    @FXML
    private Label lbErrorAlto;
    @FXML
    private Label lbErrorAncho;
    @FXML
    private Label lbErrorProfundidad;
    @FXML
    private TextField tfDescripcion;
    @FXML
    private Label lbErrorDescripcion;
    @FXML
    private ComboBox<Envio> cbNumeroGuia;
    @FXML
    private Label lbErrorNumeroGuia;

    private NotificadorOperaciones observador;
    private boolean isEditable = false;
    private Paquete paquete = new Paquete();
    private ObservableList<Envio> guias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarNumerosGuias();
    }

    @FXML
    private void Guardar(ActionEvent event) {
        actualizarInformacionPaquete();
        System.out.println(paquete);
        if (validarCamposInput()) {
            if (isEditable) {
                editarDatosPaquete(paquete);
            } else {
                guardarPaquete(paquete);
            }
        }
    }

    private void cargarNumerosGuias() {
        List<Envio> guia = EnviosDAO.obtenerEnvios();
        if (guia != null && !guia.isEmpty()) {
            this.guias = FXCollections.observableArrayList(guia);
            cbNumeroGuia.setItems(guias);
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Error al cargar", "Hubo un error al momento de cargar los roles, intentelo nuevamente");
        }
    }

    public void inicializarValores(NotificadorOperaciones observador, Paquete paquete) {
        this.observador = observador;
        this.paquete = paquete;

        if (paquete != null) {
            isEditable = true;
            cargarDatosEdicion();
        }

    }

    private boolean validarCamposInput() {
        boolean validacionExitosa = true;

        if (tfPeso.getText().isEmpty() || !esFloat(tfPeso.getText())) {
            lbErrorPeso.setText("El peso debe ser válido.");
            validacionExitosa = false;
        } else {
            lbErrorPeso.setText("");
        }

        if (tfAlto.getText().isEmpty() || !esFloat(tfAlto.getText())) {
            lbErrorAlto.setText("La altura debe ser válida.");
            validacionExitosa = false;
        } else {
            lbErrorAlto.setText("");
        }

        if (tfAncho.getText().isEmpty() || !esFloat(tfAncho.getText())) {
            lbErrorAncho.setText("El ancho debe ser válido.");
            validacionExitosa = false;
        } else {
            lbErrorAncho.setText("");
        }

        if (tfProfundidad.getText().isEmpty() || !esFloat(tfProfundidad.getText())) {
            lbErrorProfundidad.setText("La profundidad debe ser válida.");
            validacionExitosa = false;
        } else {
            lbErrorProfundidad.setText("");
        }

        if (tfDescripcion.getText().isEmpty()) {
            lbErrorDescripcion.setText("La descripción no puede estar vacía.");
            validacionExitosa = false;
        } else {
            lbErrorDescripcion.setText("");
        }
        Integer idEnvio = (cbNumeroGuia.getSelectionModel().getSelectedItem() != null)
                ? cbNumeroGuia.getSelectionModel().getSelectedItem().getIdEnvio() : null;
        if (idEnvio == null) {
            lbErrorNumeroGuia.setText("Debes seleccionar un numero de guia.");
            validacionExitosa = false;
        }
        if (validacionExitosa == false) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "CAMPOS INVALIDOS", "Campos no validos");
        }
        return validacionExitosa;
    }

    private boolean esFloat(String texto) {
        try {
            Float.parseFloat(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void actualizarInformacionPaquete() {
        if (validarCamposInput()) {
            if (!isEditable) {
                paquete = new Paquete();
            }
            paquete.setPeso(Float.parseFloat(tfPeso.getText()));
            paquete.setDimensionesAlto(Float.parseFloat(tfAlto.getText()));
            paquete.setDimensionesAncho(Float.parseFloat(tfAncho.getText()));
            paquete.setDimensionesProfundidad(Float.parseFloat(tfProfundidad.getText()));
            paquete.setDescripcion(tfDescripcion.getText().trim());
            paquete.setIdEnvio((cbNumeroGuia.getSelectionModel().getSelectedItem() != null)
                    ? cbNumeroGuia.getSelectionModel().getSelectedItem().getIdEnvio() : null);

        }
    }

    private void cargarDatosEdicion() {
        tfAlto.setText(paquete.getDimensionesAlto() + "");
        tfAncho.setText(paquete.getDimensionesAncho() + "");
        tfPeso.setText(paquete.getPeso() + "");
        tfProfundidad.setText(paquete.getDimensionesProfundidad() + "");
        tfDescripcion.setText(paquete.getDescripcion());
        cbNumeroGuia.getSelectionModel().select(buscarEnvio(paquete.getIdEnvio()));

    }

    private int buscarEnvio(int idEnvio) {
        for (int i = 0; i < guias.size(); i++) {
            if (guias.get(i).getIdEnvio() == idEnvio) {
                return i;
            }
        }
        return 0;
    }

    private void guardarPaquete(Paquete paquete) {
        Mensaje mjs = PaquetesDAO.registrarPaquete(paquete);
        if (!mjs.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "Registro exitoso", "El colaborador se ha registrado con exito");
            cerrarVentana();
            observador.notificacionOperacion("Nuevo registro", "");
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al guardar");
        }
    }

    private void editarDatosPaquete(Paquete paquete) {
        Mensaje mjs = PaquetesDAO.editarPaquete(paquete);
        if (!mjs.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "El colaborador se ha editado con exito", "Edicion exitosa");
            cerrarVentana();
            observador.notificacionOperacion("Nueva edicion", paquete.getNumeroGuia());
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al guardar");
        }
    }

    private void cerrarVentana() {
        Stage base = (Stage) tfAlto.getScene().getWindow();
        base.close();
    }
}
