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
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.dao.UnidadesDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Unidad;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLAdministrarUnidadesController implements Initializable, NotificadorOperaciones {

    @FXML
    private TableColumn colMarca;
    @FXML
    private TableColumn colModelo;
    @FXML
    private TableColumn colAnio;
    @FXML
    private TableColumn colVIN;
    @FXML
    private TableColumn colNumeroInterno;
    @FXML
    private TableColumn colTipo;
    @FXML
    private TableColumn colConductor;
    @FXML
    private TableView<Unidad> tbUnidades;
    @FXML
    private TextField tfBuscar;
    @FXML
    private Button btAcitovosInactivo;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAsignarConductor;

    private Boolean activos = true;
    private ObservableList<Unidad> OLunidades;
    private FilteredList<Unidad> listaFiltrada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btAcitovosInactivo.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        configurarTabla();
        cargarInformacionTablaActivos();
        listaFiltrada = new FilteredList<>(OLunidades, p -> true);
        tbUnidades.setItems(listaFiltrada);
    }

    @FXML
    private void Agregar(ActionEvent event) {
        irFormulario(this, null);
    }

    @FXML
    private void Editar(ActionEvent event) {
        Unidad unidad = tbUnidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            irFormulario(this, unidad);
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        Unidad unidad = tbUnidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("Está a punto de eliminar la unidad: " + unidad.getNumeroInterno());
            confirmacion.setContentText("¿Está seguro de que desea continuar?");

            if (confirmacion.showAndWait().orElse(null) == ButtonType.OK) {
                TextInputDialog motivoDialogo = new TextInputDialog();
                motivoDialogo.setTitle("Registrar motivo de baja");
                motivoDialogo.setHeaderText("Ingrese el motivo de la baja de la unidad: " + unidad.getNumeroInterno());
                motivoDialogo.setContentText("Motivo:");

                String motivo = motivoDialogo.showAndWait().orElse(null);
                unidad.setMotivoBaja(motivo);
                if (motivo != null && !motivo.trim().isEmpty()) {
                    Mensaje msj = UnidadesDAO.eliminarUnidad(unidad);
                    if (!msj.isError()) {
                        Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "La unidad se ha eliminado con éxito", "Eliminación exitosa");
                        cargarInformacionTablaActivos();
                    } else {
                        Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error al eliminar");
                    }
                } else {
                    Utilidades.AletaSimple(Alert.AlertType.WARNING, "Debe ingresar un motivo para proceder con la eliminación.", "Motivo requerido");
                }
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Seleccione un elemento en la tabla para continuar", "Error");
        }
    }

    @FXML
    private void Buscar(KeyEvent event) {
        String textoBusqueda = tfBuscar.getText().toLowerCase();
        listaFiltrada.setPredicate(p -> {
            if (textoBusqueda == null || textoBusqueda.isEmpty()) {
                return true;
            }

            return p.getVIN().toLowerCase().contains(textoBusqueda)
                    || p.getNumeroInterno().toLowerCase().contains(textoBusqueda)
                    || p.getMarca().toLowerCase().contains(textoBusqueda);
        });
    }

    @FXML
    private void asignarConductor(ActionEvent event) {
        Unidad unidad = tbUnidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAsignarConductor.fxml"));
                Parent root = loader.load();
                FXMLAsignarConductorController controlador = loader.getController();
                if (unidad.getNombreColaboradorCompleto().equals("Sin asignar")) {
                    controlador.inicializarValores(this, unidad, false);
                } else {
                    controlador.inicializarValores(this, unidad, true);
                }
                Stage escenarioAdministrador = new Stage();
                Scene scene = new Scene(root);
                escenarioAdministrador.setScene(scene);
                escenarioAdministrador.setTitle("Asignacion de conductor");
                escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
                escenarioAdministrador.showAndWait();
            } catch (Exception e) {
                Utilidades.AletaSimple(Alert.AlertType.WARNING, "No existen conductores para asinar", "Error");
            }

        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Seleccione un elemento en la tabla para continuar", "Error");

        }
    }

    private void configurarTabla() {
        colAnio.setCellValueFactory(new PropertyValueFactory("anio"));
        colConductor.setCellValueFactory(new PropertyValueFactory("nombreColaboradorCompleto"));
        colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        colNumeroInterno.setCellValueFactory(new PropertyValueFactory("numeroInterno"));
        colTipo.setCellValueFactory(new PropertyValueFactory("nombreTipo"));
        colVIN.setCellValueFactory(new PropertyValueFactory("VIN"));

    }

    private void cargarInformacionTablaActivos() {
        OLunidades = FXCollections.observableArrayList();
        List<Unidad> listaWS = UnidadesDAO.obtenerUnidadesActivas();
        OLunidades.addAll(listaWS);
        tbUnidades.setItems(OLunidades);
    }

    private void cargarInformacionTablaInactivos() {
        OLunidades = FXCollections.observableArrayList();
        List<Unidad> listaWS = UnidadesDAO.obtenerUnidadesInactivas();
        OLunidades.addAll(listaWS);
        tbUnidades.setItems(OLunidades);
    }

    private void irFormulario(NotificadorOperaciones observador, Unidad unidad) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioUnidades.fxml"));
            Parent root = loader.load();
            FXMLFormularioUnidadesController controlador = loader.getController();
            controlador.inicializarValores(observador, unidad);
            Stage escenarioAdministrador = new Stage();
            Scene scene = new Scene(root);
            escenarioAdministrador.setScene(scene);
            escenarioAdministrador.setTitle("Formulario de Unidades");
            escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
            escenarioAdministrador.showAndWait();
        } catch (Exception e) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, e.getMessage(), "EROR");
        }
    }

    @Override
    public void notificacionOperacion(String tipo, String nombre) {
        cargarInformacionTablaActivos();
    }

    @FXML
    private void ActivosInactivos(ActionEvent event) {
        activos = !activos;
        if (activos) {
            btAcitovosInactivo.setText("ACTIVOS");
            btAcitovosInactivo.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            cargarInformacionTablaActivos();
            btnEditar.setDisable(false);
            btnEliminar.setDisable(false);
            btnAgregar.setDisable(false);
            btnAsignarConductor.setDisable(false);
        } else {
            btAcitovosInactivo.setText("INACTIVOS");
            btAcitovosInactivo.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            cargarInformacionTablaInactivos();
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);
            btnAgregar.setDisable(true);
            btnAsignarConductor.setDisable(true);
        }
    }
}
