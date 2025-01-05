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
    @FXML
    private Button btAcitovosInactivo;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    private ObservableList<Colaborador> OLcolaboradores;
    private FilteredList<Colaborador> listaFiltrada;
    private Boolean activos = true;
    private Colaborador colaboradorSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btAcitovosInactivo.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        configurarTabla();
        cargarInformacionTablaActivos();
        listaFiltrada = new FilteredList<>(OLcolaboradores, p -> true);
        tbColaboradores.setItems(listaFiltrada);
    }

    public void inicializarValores(Colaborador colaborador) {
        this.colaboradorSesion = colaborador;
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
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Selecciones un elemento en la tabla para continuar", "Error");
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        Colaborador colaborador = tbColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador != null) {
            if (colaborador.getIdColaborador() == this.colaboradorSesion.getIdColaborador()) {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, "No se puede suspender el usuario en sesión", "Error al eliminar");
            } else {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmar eliminación");
                confirmacion.setHeaderText("Está a punto de suspender al colaborador: " + colaborador.getNombre() + " " + colaborador.getApellidoPaterno());
                confirmacion.setContentText("¿Está seguro de que desea continuar?");

                if (confirmacion.showAndWait().orElse(null) == ButtonType.OK) {
                    Mensaje mjs = ColaboradorDAO.eliminarColaborador(colaborador.getIdColaborador());
                    if (!mjs.isError()) {
                        Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "El colaborador se ha suspendido con éxito", "Suspensión exitosa");
                        cargarInformacionTablaActivos();
                    } else {
                        Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al suspender");
                    }
                }
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

    private void cargarInformacionTablaActivos() {
        OLcolaboradores = FXCollections.observableArrayList();
        List<Colaborador> listaWS = ColaboradorDAO.obtenerColaboradoresActivos();
        OLcolaboradores.addAll(listaWS);
        tbColaboradores.setItems(OLcolaboradores);
    }

    private void cargarInformacionTablaInactivos() {
        OLcolaboradores = FXCollections.observableArrayList();
        List<Colaborador> listaWS = ColaboradorDAO.obtenerColaboradoresInactivos();
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
        cargarInformacionTablaActivos();
    }

    @FXML
    private void ActivosInactivos(ActionEvent event) {
        activos = !activos;
        if (activos) {
            btAcitovosInactivo.setText("ACTIVOS");
            btAcitovosInactivo.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5; -fx-border-color:  #388E3C; -fx-border-radius: 5; -fx-border-width: 2;" );
            cargarInformacionTablaActivos();
            btnEditar.setDisable(false);
            btnEliminar.setDisable(false);
            btnRegistrar.setDisable(false);
        } else {
            btAcitovosInactivo.setText("INACTIVOS");
            btAcitovosInactivo.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5; -fx-border-color: #D32F2F; -fx-border-radius: 5; -fx-border-width: 2;" );
            cargarInformacionTablaInactivos();
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);
            btnRegistrar.setDisable(true);
        }
    }
}
