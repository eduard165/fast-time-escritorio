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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.dao.ClienteDAO;
import modelo.dao.EnviosDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Envio;
import modelo.pojo.EstadoEnvio;
import modelo.pojo.Mensaje;
import modelo.pojo.Rol;
import observador.NotificadorOperaciones;
import utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author eduar
 */
public class FXMLFormularioEnviosController implements Initializable {

    @FXML
    private TextField tfNumeroGuia;
    @FXML
    private TextField tfCosto;
    @FXML
    private Label lbErrorNumeroGuia;
    @FXML
    private Label lbErrorCosto;
    @FXML
    private ComboBox<Cliente> cbClientes;
    @FXML
    private Label lbErrorClientes;

    ObservableList<Cliente> tiposlientes;
    private NotificadorOperaciones observador;
    private Envio envio = new Envio();
    private boolean isEditable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarClientes();
    }

    @FXML
    private void Guardar(ActionEvent event) {
        if (validarCampos()) {
           this.envio = guardarInformacion();
            if (isEditable) {
                actualizar(this.envio);
            } else {
                guardar(this.envio);
            }

        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "CAMPOS NO VALIDOS", "ERROR");

        }
    }

    void inicializarValores(NotificadorOperaciones observador, Envio envio, boolean isEditable) {
        this.observador = observador;
        this.envio = envio;
        this.isEditable = isEditable;
        if (isEditable) {
            cargarDatos();
        }
    }

    private void cargarDatos() {
        tfCosto.setText(envio.getCosto() + "");
        tfNumeroGuia.setText(envio.getNumeroGuia());
        cbClientes.getSelectionModel().select(buscarCliente(envio.getIdCliente()));
    }

    private void cargarClientes() {
        try {
            List<Cliente> clientes = ClienteDAO.obtenerClientes();
            if (clientes != null && !clientes.isEmpty()) {
                tiposlientes = FXCollections.observableArrayList(clientes);
                cbClientes.setItems(tiposlientes);
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, "Hubo un error al momento de cargar los estados de envío, inténtelo nuevamente.", "Error al cargar");
            }
        } catch (Exception e) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Ocurrió un error al cargar los estados de envío: " + e.getMessage(), "Error");
        }
    }

    private int buscarCliente(Integer idCliente) {
        for (int i = 0; i < tiposlientes.size(); i++) {
            if (tiposlientes.get(i).getIdCliente() == idCliente) {

                return i;
            }
        }
        return 0;
    }

    private boolean validarCampos() {
        boolean esValido = true;

        String numeroGuia = tfNumeroGuia.getText().trim();
        if (numeroGuia.isEmpty() || numeroGuia.length() > 20) {
            lbErrorNumeroGuia.setText("Número de guía inválido");
            esValido = false;
        } else {
            lbErrorNumeroGuia.setText("");
        }

        String costoTexto = tfCosto.getText().trim();
        try {
            float costo = Float.parseFloat(costoTexto);
            if (costo < 0) {
                lbErrorCosto.setText("El costo debe ser positivo");
                esValido = false;
            } else {
                lbErrorCosto.setText("");
            }
        } catch (NumberFormatException e) {
            lbErrorCosto.setText("Costo inválido");
            esValido = false;
        }

        if (cbClientes.getSelectionModel().getSelectedItem() == null) {
            lbErrorClientes.setText("Debe seleccionar un cliente");
            esValido = false;
        } else {
            lbErrorClientes.setText("");
        }

        return esValido;
    }

    private void guardar(Envio envio) {
        Mensaje mjs = EnviosDAO.registrarEnvio(envio);
        if (!mjs.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION, mjs.getContenido(), "Operación exitosa");
            observador.notificacionOperacion("", "");
            cerrarVentana();
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error");
        }

    }

    private void actualizar(Envio envio) {
        Mensaje mjs = EnviosDAO.editarEnvio(envio);
        if (!mjs.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION, mjs.getContenido(), "Operación exitosa");
            observador.notificacionOperacion("", "");
            cerrarVentana();
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error");
        }
    }

    private Envio guardarInformacion() {
        Envio envioNuevo = new Envio();
        envioNuevo.setCosto(Float.parseFloat(tfCosto.getText()));
        envioNuevo.setNumeroGuia(tfNumeroGuia.getText());
        envioNuevo.setIdCliente((cbClientes.getSelectionModel().getSelectedItem() != null)
                ? cbClientes.getSelectionModel().getSelectedItem().getIdCliente() : null);
        return envioNuevo;
    }

    private void cerrarVentana() {
        Stage base = (Stage) tfCosto.getScene().getWindow();
        base.close();
    }
}
