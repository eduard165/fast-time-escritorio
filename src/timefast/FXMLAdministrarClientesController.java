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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.dao.ClienteDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLAdministrarClientesController implements Initializable, NotificadorOperaciones {

    private ObservableList<Cliente> OLclientes;
    private FilteredList<Cliente> listaFiltrada;
    private Cliente cliente = new Cliente();
    private TextField txfBuscador;
    private Boolean activos = true;

    @FXML
    private TableView<Cliente> tbClientes;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colCorreoElectronico;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private Button btAcitovosInactivo;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnDireccion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btAcitovosInactivo.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        configurarTabla();
        cargarInformacionTablaActivos();
        listaFiltrada = new FilteredList<>(OLclientes, p -> true);
        tbClientes.setItems(listaFiltrada);
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
                    || p.getCorreoElectronico().toLowerCase().contains(textoBusqueda)
                    || p.getTelefono().toLowerCase().contains(textoBusqueda);
        });
    }

    @FXML
    private void editar(ActionEvent event) {
        this.cliente = tbClientes.getSelectionModel().getSelectedItem();
        if (this.cliente != null) {
            irFormulario(this, this.cliente);
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        this.cliente = tbClientes.getSelectionModel().getSelectedItem();
        if (this.cliente != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("Está a punto de eliminar al cliente: " + cliente.getNombre() + " " + cliente.getApellidoPaterno());
            confirmacion.setContentText("¿Está seguro de que desea continuar?");

            if (confirmacion.showAndWait().orElse(null) == ButtonType.OK) {
                Mensaje mjs = ClienteDAO.eliminarCliente(cliente.getIdCliente());
                if (!mjs.isError()) {
                    Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "El cliente se ha eliminado con éxito", "Eliminación exitosa");
                    cargarInformacionTablaActivos();
                } else {
                    Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al eliminar");
                }
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    @FXML
    private void agregarDireccion(ActionEvent event) {
        this.cliente = tbClientes.getSelectionModel().getSelectedItem();

        if (this.cliente != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioDireccion.fxml"));
                Parent root = loader.load();
                FXMLFormularioDireccionController controlador = loader.getController();
                controlador.incializarValoresCliente(this, cliente.getIdCliente(), null, null);

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
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "SELECCIONE UN ELEMENTO EN LA TABLA PARA CONTINUAR", "Error");
        }
    }

    private void configurarTabla() {
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory("correoElectronico"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("municipio"));
    }

    private void cargarInformacionTablaActivos() {
        OLclientes = FXCollections.observableArrayList();
        List<Cliente> listaWS = ClienteDAO.obtenerClientesActivos();
        OLclientes.addAll(listaWS);
        tbClientes.setItems(OLclientes);
    }

    private void cargarInformacionTablaInactivos() {
        OLclientes = FXCollections.observableArrayList();
        List<Cliente> listaWS = ClienteDAO.obtenerClientesInactivos();
        OLclientes.addAll(listaWS);
        tbClientes.setItems(OLclientes);
    }

    private void irFormulario(NotificadorOperaciones observador, Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioClientes.fxml"));
            Parent root = loader.load();
            FXMLFormularioClientesController controlador = loader.getController();
            controlador.inicializarValores(observador, cliente);

            Stage escenarioAdministrador = new Stage();
            Scene scene = new Scene(root);
            escenarioAdministrador.setScene(scene);
            escenarioAdministrador.setTitle("Formulario Cliente");
            escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
            escenarioAdministrador.showAndWait();
        } catch (Exception e) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, e.getMessage(), "Error");
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
            btnRegistrar.setDisable(false);
            btnEditar.setDisable(false);
            btnEliminar.setDisable(false);
            btnDireccion.setDisable(false);
        } else {
            btAcitovosInactivo.setText("INACTIVOS");
            btAcitovosInactivo.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            cargarInformacionTablaInactivos();
            btnRegistrar.setDisable(true);
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);
            btnDireccion.setDisable(true);
        }
    }

}
