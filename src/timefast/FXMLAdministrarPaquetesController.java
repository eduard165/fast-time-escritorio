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
import modelo.dao.PaquetesDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Paquete;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLAdministrarPaquetesController implements Initializable, NotificadorOperaciones {

    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colPeso;
    @FXML
    private TableColumn colAlto;
    @FXML
    private TableColumn colAncho;
    @FXML
    private TableColumn colProfundidad;
    @FXML
    private TableView<Paquete> tbPaqutes;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableColumn colNumeroGuia;

    private ObservableList<Paquete> OLpaquetes;
    private FilteredList<Paquete> listaFiltrada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        listaFiltrada = new FilteredList<>(OLpaquetes, p -> true);
        tbPaqutes.setItems(listaFiltrada);
    }

    @FXML
    private void Agregar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioPaquetes.fxml"));
            Parent root = loader.load();
            FXMLFormularioPaquetesController controlador = loader.getController();
            controlador.inicializarValores(this, null);

            Stage escenarioAdministrador = new Stage();
            Scene scene = new Scene(root);
            escenarioAdministrador.setScene(scene);
            escenarioAdministrador.setTitle("Formulario Paquete");
            escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
            escenarioAdministrador.showAndWait();
        } catch (Exception e) {
            Utilidades.AletaSimple(Alert.AlertType.WARNING,"No hay envios registrados" , "EROR");
        }
    }

    @FXML
    private void Editar(ActionEvent event) {
        Paquete paquete = tbPaqutes.getSelectionModel().getSelectedItem();
        System.out.println(paquete);
        if (paquete != null) {
            irFormulario(this, paquete);
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Selecciones un elemento en la tabla para continuar", "Error");
        }
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        Paquete paquete = tbPaqutes.getSelectionModel().getSelectedItem();
        if (paquete != null) {
            Mensaje mjs = PaquetesDAO.eliminarPaquete(paquete.getIdPaquete());
            if (!mjs.isError()) {
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "El paquete se ha eliminado con exito", "Eliminacion exitosa");
                cargarInformacionTabla();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al eliminar");
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Selecciones un elemento en la tabla para continuar", "Error");
        }
    }

    @FXML
    private void Buscar(KeyEvent event) {
        String textoBusqueda = tfBuscar.getText().toLowerCase();
        listaFiltrada.setPredicate(p -> {
            if (textoBusqueda == null || textoBusqueda.isEmpty()) {
                return true;
            }
            return p.getNumeroGuia().toLowerCase().contains(textoBusqueda);
        });
    }

    private void configurarTabla() {
        colAlto.setCellValueFactory(new PropertyValueFactory("dimensionesAlto"));
        colAncho.setCellValueFactory(new PropertyValueFactory("dimensionesAncho"));
        colPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        colProfundidad.setCellValueFactory(new PropertyValueFactory("dimensionesProfundidad"));
        colNumeroGuia.setCellValueFactory(new PropertyValueFactory("numeroGuia"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
    }

    private void cargarInformacionTabla() {
        OLpaquetes = FXCollections.observableArrayList();
        List<Paquete> listaWS = PaquetesDAO.obtenerPaquetes();
        OLpaquetes.addAll(listaWS);
        tbPaqutes.setItems(OLpaquetes);
    }

    private void irFormulario(NotificadorOperaciones observador, Paquete paquete) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioPaquetes.fxml"));
            Parent root = loader.load();
            FXMLFormularioPaquetesController controlador = loader.getController();
            controlador.inicializarValores(observador, paquete);

            Stage escenarioAdministrador = new Stage();
            Scene scene = new Scene(root);
            escenarioAdministrador.setScene(scene);
            escenarioAdministrador.setTitle("Formulario Paquete");
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
