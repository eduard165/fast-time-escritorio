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
import modelo.dao.ColaboradorDAO;
import modelo.dao.UnidadesDAO;
import modelo.pojo.Colaborador;
import modelo.pojo.Mensaje;
import modelo.pojo.TipoUnidad;
import modelo.pojo.Unidad;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLFormularioUnidadesController implements Initializable {

    @FXML
    private Label lbErrorMarca;
    @FXML
    private Label lbErrorModelo;
    @FXML
    private Label lbErrorAnio;
    @FXML
    private Label lbErrorVIN;
    @FXML
    private Label lbErrorTipo;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfVIN;
    @FXML
    private Label lbErrorTipoUnidad;
    @FXML
    private ComboBox<TipoUnidad> cbTipoUnidad;

    private boolean isEditable = false;
    private NotificadorOperaciones observador;
    private ObservableList<TipoUnidad> tiposUnidades;
    private Unidad unidad = new Unidad();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTiposRoles();
    }

    @FXML
    private void Guardar(ActionEvent event) {
        if (validarDatos()) {
            Unidad unidadNueva = obtenerDatos();
            if(isEditable){
                editar(unidadNueva);
            }else{
                guardar(unidadNueva);
            }
        } else {

        }
    }

    void inicializarValores(NotificadorOperaciones observador, Unidad unidad) {
        this.observador = observador;
        this.unidad = unidad;
        if (unidad != null) {
            isEditable = true;
            cargarDatos();
        }
    }

    private void cargarDatos() {
        tfAnio.setText(unidad.getAnio() + "");
        tfMarca.setText(unidad.getMarca());
        tfModelo.setText(unidad.getModelo());
        tfVIN.setText(unidad.getVIN());
        cbTipoUnidad.getSelectionModel().select(buscarTipoUnidad(unidad.getIdTipoUnidad()));

    }

    private void cargarTiposRoles() {
        List<TipoUnidad> listasUnidades = UnidadesDAO.obtenerTiposDeUnidades();
        if (listasUnidades != null && !listasUnidades.isEmpty()) {
            tiposUnidades = FXCollections.observableArrayList(listasUnidades);
            cbTipoUnidad.setItems(tiposUnidades);
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Hubo un error al momento de cargar los roles, intentelo nuevamente", "Error al cargar");
        }
    }

    private int buscarTipoUnidad(int idRol) {
        for (int i = 0; i < tiposUnidades.size(); i++) {
            if (tiposUnidades.get(i).getIdTipoUnidad() == idRol) {
                return i;
            }
        }
        return 0;
    }

    private void guardar(Unidad unidad) {
        Mensaje mjs = UnidadesDAO.registrarUnidad(unidad);
        if (!mjs.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION, mjs.getContenido(), "Registro exitoso");
            cerrarVentana();
            observador.notificacionOperacion(" ", "");
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al guardar");
        }
    }

    private void editar(Unidad unidad) {
        Mensaje mjs = UnidadesDAO.editarUnidad(unidad);
        if (!mjs.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION, mjs.getContenido(), "Edicion exitosa");
            cerrarVentana();
            observador.notificacionOperacion(" ", " ");
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al guardar");
        }
    }

    private boolean validarDatos() {
        boolean validar = true;

        if (tfMarca.getText() == null || tfMarca.getText().trim().isEmpty()) {
            lbErrorMarca.setText("La marca es obligatoria.");
            validar = false;
        } else {
            lbErrorMarca.setText("");
        }

        if (tfModelo.getText() == null || tfModelo.getText().trim().isEmpty()) {
            lbErrorModelo.setText("El modelo es obligatorio.");
            validar = false;
        } else {
            lbErrorModelo.setText("");
        }

        try {
            String anioTexto = tfAnio.getText();
            if (anioTexto == null || anioTexto.trim().isEmpty()) {
                lbErrorAnio.setText("El año es obligatorio.");
                validar = false;
            } else {
                int anio = Integer.parseInt(anioTexto);
                if (anio < 1900 || anio > java.time.Year.now().getValue()) {
                    lbErrorAnio.setText("El año debe estar entre 1900 y el actual.");
                    validar = false;
                } else {
                    lbErrorAnio.setText("");
                }
            }
        } catch (NumberFormatException e) {
            lbErrorAnio.setText("El año debe ser un número válido.");
            validar = false;
        }

        if (tfVIN.getText() == null || tfVIN.getText().trim().isEmpty()) {
            lbErrorVIN.setText("El VIN es obligatorio.");
            validar = false;
        } else if (tfVIN.getText().length() != 17) {
            lbErrorVIN.setText("El VIN debe tener exactamente 17 caracteres.");
            validar = false;
        } else {
            lbErrorVIN.setText("");
        }

        if (cbTipoUnidad.getSelectionModel().getSelectedItem() == null) {
            lbErrorTipoUnidad.setText("Debe seleccionar un tipo de unidad.");
            validar = false;
        } else {
            lbErrorTipoUnidad.setText("");
        }

        return validar;
    }

    private void cerrarVentana() {
        Stage base = (Stage) tfAnio.getScene().getWindow();
        base.close();
    }

    private Unidad obtenerDatos() {
        Unidad unidadNueva = new Unidad();
        String anioTexto = tfAnio.getText();
        int anio = Integer.parseInt(anioTexto);
        unidadNueva.setAnio(anio);
        unidadNueva.setMarca(tfMarca.getText());
        unidadNueva.setModelo(tfModelo.getText());
        unidadNueva.setVIN(tfVIN.getText());
        unidadNueva.setIdTipoUnidad((cbTipoUnidad.getSelectionModel().getSelectedItem() != null)
                ? cbTipoUnidad.getSelectionModel().getSelectedItem().getIdTipoUnidad()
                : null);

        return unidadNueva;
    }

}
