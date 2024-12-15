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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.dao.EnviosDAO;
import modelo.pojo.Envio;

import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLAdministrarEnviosController implements Initializable, NotificadorOperaciones {

    @FXML
    private TableView<Envio> tblGestionEnvios;
    @FXML
    private TableColumn colNumeroGuia;
    @FXML
    private TableColumn colCosto;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TextField tfBuscador;

    private ObservableList<Envio> OLenvios;
    private FilteredList<Envio> listaFiltrada;
    @FXML
    private TableColumn colNombreColaborador;
    @FXML
    private TableColumn colNombreCliente;
    @FXML
    private TableColumn colOrigen;
    @FXML
    private TableColumn colDestino;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        listaFiltrada = new FilteredList<>(OLenvios, p -> true);
        tblGestionEnvios.setItems(listaFiltrada);
    }

    @FXML
    private void Buscar(KeyEvent event
    ) {
        String textoBusqueda = tfBuscador.getText().toLowerCase();
        listaFiltrada.setPredicate(p -> {
            if (textoBusqueda == null || textoBusqueda.isEmpty()) {
                return true;
            }

            return p.getDescripcion().toLowerCase().contains(textoBusqueda)
                    || p.getCodigoPostalDestino().toLowerCase().contains(textoBusqueda)
                    || p.getCodigoPostalOrigen().toLowerCase().contains(textoBusqueda);
        });
    }

    @FXML
    private void cambiarStatus(ActionEvent event) {
        Envio envio = new Envio();
        envio = tblGestionEnvios.getSelectionModel().getSelectedItem();
        if (envio != null) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioCambarEstadoEnvio.fxml"));
                Parent root = loader.load();

                FXMLFormularioCambarEstadoEnvioController controlador = loader.getController();
                controlador.inicializarValores(this, envio);

                Stage escenarioAdministrador = new Stage();
                Scene scene = new Scene(root);
                escenarioAdministrador.setScene(scene);
                escenarioAdministrador.setTitle("Cambio de estado");
                escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
                escenarioAdministrador.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                Utilidades.AletaSimple(Alert.AlertType.ERROR, "ERROR AL CARGAR EL FORMULARIO", "EROR");
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        Envio envio = tblGestionEnvios.getSelectionModel().getSelectedItem();
        if (envio != null) {
            irFormulario(this, envio, true);
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    @FXML
    private void agregarOrigen(ActionEvent event) {
        Envio envio = tblGestionEnvios.getSelectionModel().getSelectedItem();

        if (envio != null) {
            irFormularioDireccion(this, envio.getIdEnvio(), null);
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    @FXML
    private void agregarDestino(ActionEvent event) {
        Envio envio = tblGestionEnvios.getSelectionModel().getSelectedItem();

        if (envio != null) {
            irFormularioDireccion(this, null, envio.getIdEnvio());
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    @FXML
    private void registrarEnvio(ActionEvent event) {
        irFormulario(this, null, false);

    }

    @FXML
    private void asignarConductor(ActionEvent event) {
        Envio envio = new Envio();
        envio = tblGestionEnvios.getSelectionModel().getSelectedItem();
        if (envio != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAsignarConductor.fxml"));
                Parent root = loader.load();
                FXMLFormularioAsignarConductorController controlador = loader.getController();
                if (envio.getIdColaborador() == null) {
                    controlador.inicializarValores(this, envio, false);

                } else {
                    controlador.inicializarValores(this, envio, true);

                }
                Stage escenarioAdministrador = new Stage();
                Scene scene = new Scene(root);
                escenarioAdministrador.setScene(scene);
                escenarioAdministrador.setTitle("Formulario de colaboradores");
                escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
                escenarioAdministrador.showAndWait();
            } catch (Exception e) {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, e.getMessage(), "EROR");
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    private void configurarTabla() {
        colCosto.setCellValueFactory(new PropertyValueFactory("costo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colDestino.setCellValueFactory(new PropertyValueFactory("municipioDestino"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("nombreEstadoEnvio"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory("nombreClienteCompleto"));
        colNombreColaborador.setCellValueFactory(new PropertyValueFactory("nombreColaboradorCompleto"));
        colNumeroGuia.setCellValueFactory(new PropertyValueFactory("numeroGuia"));
        colOrigen.setCellValueFactory(new PropertyValueFactory("municipioOrigen"));
    }

    private void cargarInformacionTabla() {
        OLenvios = FXCollections.observableArrayList();
        List<Envio> listaWS = EnviosDAO.obtenerEnvios();
        OLenvios.addAll(listaWS);
        tblGestionEnvios.setItems(OLenvios);
    }

    private void irFormulario(NotificadorOperaciones observador, Envio envio, boolean isEditable) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioEnvios.fxml"));
            Parent root = loader.load();
            FXMLFormularioEnviosController controlador = loader.getController();
            controlador.inicializarValores(observador, envio, isEditable);
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

    private void irFormularioDireccion(NotificadorOperaciones observador, Integer idOrigen, Integer idDestino) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioDireccion.fxml"));
            Parent root = loader.load();
            FXMLFormularioDireccionController controlador = loader.getController();
            controlador.incializarValoresCliente(this, null, idOrigen, idDestino);

            Stage escenarioAdministrador = new Stage();
            Scene scene = new Scene(root);
            escenarioAdministrador.setScene(scene);
            escenarioAdministrador.setTitle("Formulario Direccion");
            escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
            escenarioAdministrador.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.AletaSimple(Alert.AlertType.ERROR, e.getMessage(), "Error");
        }
    }

    @Override
    public void notificacionOperacion(String tipo, String nombre) {
        cargarInformacionTabla();
    }

    @FXML
    private void verDetalles(ActionEvent event) {
        Envio envio = new Envio();
        envio = tblGestionEnvios.getSelectionModel().getSelectedItem();
        if (envio != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLVisualizarInformacionEnvio.fxml"));
                Parent root = loader.load();
                FXMLVisualizarInformacionEnvioController controlador = loader.getController();
                controlador.inicializarInformacion(envio);
                Stage escenarioAdministrador = new Stage();
                Scene scene = new Scene(root);
                escenarioAdministrador.setScene(scene);
                escenarioAdministrador.setTitle("Visualizar informacion");
                escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
                escenarioAdministrador.showAndWait();
            } catch (Exception e) {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, e.getMessage(), "EROR");
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }
}
