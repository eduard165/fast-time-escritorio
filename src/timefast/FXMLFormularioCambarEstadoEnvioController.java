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
import modelo.pojo.Envio;
import modelo.pojo.EstadoEnvio;
import modelo.pojo.Mensaje;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLFormularioCambarEstadoEnvioController implements Initializable {

    @FXML
    private Label lbErrorSeleccion;
    @FXML
    private ComboBox<EstadoEnvio> cbEstadosEnvio;
    @FXML
    private Label lbErrorDescripcion;
    @FXML
    private TextField tfDescripcion;

    private Envio envioEnviado;
    private NotificadorOperaciones observador;
    private ObservableList<EstadoEnvio> tiposEstados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEnvios();
        cbEstadosEnvio.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getIdEstadoEnvio() == 3 || newValue.getIdEstadoEnvio() == 5) {
                    tfDescripcion.setEditable(true);
                } else {
                    tfDescripcion.setEditable(false);
                    tfDescripcion.setText("Sin cambios");
                }
            }
        });

    }

    @FXML

    private void guardar(ActionEvent event) {
        if (validarCampos()) {
            guardarInformacion();
            Mensaje actualizado = EnviosDAO.actualizarEstadoEnvio(envioEnviado.getIdEnvio(), envioEnviado.getIdEstadoEnvio(), envioEnviado.getDescripcion());

            if (!actualizado.isError()) {
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, actualizado.getContenido(), "Operación exitosa");
                observador.notificacionOperacion("", "");
                cerrarVentana();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, actualizado.getContenido(), "Error");
            }
        }else{
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "CAMPOS NO VALIDOS", "ERROR");
        }
    }

    public void inicializarValores(NotificadorOperaciones observador, Envio envio) {
        this.observador = observador;
        this.envioEnviado = envio;
        
        if (envio.getIdEstadoEnvio() == 3 || envio.getIdEstadoEnvio() == 5) {
            tfDescripcion.setEditable(true);
        } else {
            tfDescripcion.setEditable(false);
        }
        
        tfDescripcion.setText(envio.getDescripcion());
        cbEstadosEnvio.getSelectionModel().select(buscarEstado(envio.getIdEstadoEnvio()));

    }

    private void guardarInformacion() {
        envioEnviado.setIdEstadoEnvio((cbEstadosEnvio.getSelectionModel().getSelectedItem() != null)
                ? cbEstadosEnvio.getSelectionModel().getSelectedItem().getIdEstadoEnvio() : null);
        if (envioEnviado.getIdEstadoEnvio() == 3 || envioEnviado.getIdEstadoEnvio() == 5) {
            if (tfDescripcion.getText().isEmpty()) {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, "Es necesario una descripcion del cambio", "ERROR");
            } else {
                envioEnviado.setDescripcion(tfDescripcion.getText());
            }
        }
    }

    private void cargarEnvios() {
        try {
            List<EstadoEnvio> envios = EnviosDAO.estadosEnvio();
            if (envios != null && !envios.isEmpty()) {
                this.tiposEstados = FXCollections.observableArrayList(envios);
                cbEstadosEnvio.setItems(this.tiposEstados);
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, "Hubo un error al momento de cargar los estados de envío, inténtelo nuevamente.", "Error al cargar");
            }
        } catch (Exception e) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Ocurrió un error al cargar los estados de envío: " + e.getMessage(), "Error");
        }
    }

    private int buscarEstado(int idEstadoEnvio) {
        for (int i = 0; i < this.tiposEstados.size(); i++) {
            if (this.tiposEstados.get(i).getIdEstadoEnvio()== idEstadoEnvio) {

                return i;
            }
        }
        return 0;
    }

    private boolean validarCampos() {
        boolean esValido = true;
        lbErrorDescripcion.setText("");
        lbErrorSeleccion.setText("");

        if (tfDescripcion.getText().trim().isEmpty()) {
            lbErrorDescripcion.setText("La descripción no puede estar vacía.");
            esValido = false;
        }

        if (cbEstadosEnvio.getSelectionModel().getSelectedItem() == null) {
            lbErrorSeleccion.setText("Debe seleccionar un estado.");
            esValido = false;
        }

        return esValido;
    }

    private void cerrarVentana() {
        Stage base = (Stage) tfDescripcion.getScene().getWindow();
        base.close();
    }

}
