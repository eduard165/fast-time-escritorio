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
import javafx.stage.Stage;
import modelo.dao.AsignacionesDAO;
import modelo.dao.ColaboradorDAO;
import modelo.pojo.Asignacion;
import modelo.pojo.Colaborador;
import modelo.pojo.Mensaje;
import modelo.pojo.Unidad;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLAsignarConductorController implements Initializable {

    @FXML
    private ComboBox<Colaborador> cbConductores;
    @FXML
    private Label lbErrorConductores;

    private ObservableList<Colaborador> tiposColaboradore;
    private boolean isEditable = false;
    private NotificadorOperaciones observador;
    private Unidad unidad = new Unidad();
    private Asignacion nuevaAsignacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarColaboradores();
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (validarComboBox()) {

            if (isEditable) {
                Mensaje msj = AsignacionesDAO.editarAsignacion(cargarCampor());
                if (!msj.isError()) {
                    Utilidades.AletaSimple(Alert.AlertType.INFORMATION, msj.getContenido(), "Operación exitosa");
                    observador.notificacionOperacion("", "");
                    cerrarVentana();
                } else {
                    Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error");
                    cerrarVentana();
                }
            } else {
                Mensaje msj = AsignacionesDAO.registrarAsignacion(cargarCampor());
                if (!msj.isError()) {
                    Utilidades.AletaSimple(Alert.AlertType.INFORMATION, msj.getContenido(), "Operación exitosa");
                    observador.notificacionOperacion("", "");
                    cerrarVentana();
                } else {
                    Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error");
                    cerrarVentana();
                }
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Error en la validacion de los datos, porfavor ingrese nuevamente la informacion", "ERROR");
        }
    }

    void inicializarValores(NotificadorOperaciones observador, Unidad unidad, boolean edicion) {
        this.observador = observador;
        this.unidad = unidad;
        this.isEditable = edicion;
        if (edicion) {
            isEditable = true;
            nuevaAsignacion = AsignacionesDAO.obtenerAsignacion(unidad.getIdUnidad());
            cbConductores.getSelectionModel().select(buscarConductor(nuevaAsignacion.getIdColaborador()));
        }
    }

    private Asignacion cargarCampor() {
        Asignacion nueva = new Asignacion();
        Colaborador seleccionado = cbConductores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "¡Por favor selecciona un conductor!", "Error al guardar");
            return null;
        }
        nueva.setIdColaborador(seleccionado.getIdColaborador());
        nueva.setIdUnidad(unidad.getIdUnidad());
        if (isEditable && nuevaAsignacion != null) {
            nueva.setIdAsignacion(nuevaAsignacion.getIdAsignacion());
        }
        return nueva;
    }

    private void cargarColaboradores() {

        List<Colaborador> colab = ColaboradorDAO.obtenerConductores();
        if (colab != null && !colab.isEmpty()) {
            this.tiposColaboradore = FXCollections.observableArrayList(colab);
            cbConductores.setItems(this.tiposColaboradore);
        } else {
            cerrarVentana();
        }
    }

    private int buscarConductor(Integer idColaborador) {
        for (int i = 0; i < tiposColaboradore.size(); i++) {
            if (tiposColaboradore.get(i).getIdColaborador() == idColaborador) {
                return i;
            }
        }
        return 0;
    }

    private boolean validarComboBox() {
        boolean isValido = true;
        if (cbConductores.getSelectionModel().getSelectedItem() == null) {
            lbErrorConductores.setText("Selecciona un elemento.");
            isValido = false;
        }
        return isValido;
    }

    private void cerrarVentana() {
        Stage base = (Stage) cbConductores.getScene().getWindow();
        base.close();
    }

}
