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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.dao.ColaboradorDAO;
import modelo.pojo.Colaborador;
import modelo.pojo.Mensaje;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLAdministrarColaboradorController implements Initializable, NotificadorOperaciones {

    @FXML
    private TextField txfBuscador;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colRol;
    @FXML
    private TableColumn colNumeroPersonal;
    @FXML
    private TableView<Colaborador> tbColaboradores;
    @FXML
    private TableColumn colCorreoElectronico;
    
    private ObservableList<Colaborador> OLcolaboradores;
    private FilteredList<Colaborador> listaFiltrada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        listaFiltrada = new FilteredList<>(OLcolaboradores, p -> true);
        tbColaboradores.setItems(listaFiltrada);
    }

    @FXML
    private void registrar(ActionEvent event) {
        irFormulario(this, null);
    }

    @FXML
    private void buscar(KeyEvent event) {
        String textoBusqueda = txfBuscador.getText().toLowerCase();
        listaFiltrada.setPredicate(p -> {
            if (textoBusqueda == null || textoBusqueda.isEmpty()) {
                return true;
            }

            return p.getNombre().toLowerCase().contains(textoBusqueda)
                    || p.getRol().toLowerCase().contains(textoBusqueda)
                    || p.getNumeroPersonal().toLowerCase().contains(textoBusqueda);
        });
    }

    @FXML
    private void editar(ActionEvent event) {
        Colaborador colaborador = tbColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador != null) {
            irFormulario(this, colaborador);
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        Colaborador colaborador = tbColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador != null) {
            Mensaje mjs = ColaboradorDAO.eliminarColaborador(colaborador.getIdColaborador());
            if (!mjs.isError()) {
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "El colaborador se ha eliminado con exito", "Eliminacion exitosa");
                cargarInformacionTabla();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al eliminar");
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    private void configurarTabla() {
        colNumeroPersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colRol.setCellValueFactory(new PropertyValueFactory("rol"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory("correoElectronico"));
    }

    private void cargarInformacionTabla() {
        OLcolaboradores = FXCollections.observableArrayList();
        List<Colaborador> listaWS = ColaboradorDAO.obtenerColaboradores();
        OLcolaboradores.addAll(listaWS);
        tbColaboradores.setItems(OLcolaboradores);
    }

    private void irFormulario(NotificadorOperaciones observador, Colaborador colaborador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioColaboradores.fxml"));
            Parent root = loader.load();
            FXMLFormularioColaboradoresController controlador = loader.getController();
            controlador.inicializarValores(observador, colaborador);
            Stage escenarioAdministrador = new Stage();
            Scene scene = new Scene(root);
            escenarioAdministrador.setScene(scene);
            escenarioAdministrador.setTitle("Formulario de colaboradores");
            escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
            escenarioAdministrador.showAndWait();
        } catch (Exception e) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, e.getMessage(), "EROR");
        }
    }

    @Override
    public void notificacionOperacion(String tipo, String nombre) {
        cargarInformacionTabla();
    }
}
