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
import modelo.dao.ColaboradorDAO;
import modelo.dao.EnviosDAO;
import modelo.pojo.Colaborador;
import modelo.pojo.Envio;
import modelo.pojo.Mensaje;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLFormularioAsignarConductorController implements Initializable {

    @FXML
    private Label lbErrorSeleccion;
    @FXML
    private ComboBox<Colaborador> cbOpciones;

    private Envio envioEnviado;
    private NotificadorOperaciones observador;
    private ObservableList<Colaborador> tiposColaboradore;
    private boolean isEditable = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarColaboradores();
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (validaCampo()) {
            envioEnviado.setIdColaborador((cbOpciones.getSelectionModel().getSelectedItem() != null)
                    ? cbOpciones.getSelectionModel().getSelectedItem().getIdColaborador() : null);
            Mensaje msj = EnviosDAO.asignarConductor(envioEnviado.getIdEnvio(), envioEnviado.getIdColaborador());
            if (!msj.isError()) {
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, msj.getContenido(), "Operación exitosa");
                observador.notificacionOperacion(" ", "");
                cerrarVentana();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error");
                cerrarVentana();
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Error en la validacion de los datos, porfavor ingrese nuevamente la informacion", "ERROR");

        }

    }

    public void inicializarValores(NotificadorOperaciones observador, Envio envio, boolean isEditable) {
        this.observador = observador;
        this.envioEnviado = envio;
        this.isEditable = isEditable;
        if (isEditable) {
            cbOpciones.getSelectionModel().select(buscarColaborador(envio.getIdColaborador()));
        }

    }

    private void cargarColaboradores() {
        List<Colaborador> colab = ColaboradorDAO.obtenerConductores();
        if (colab != null && !colab.isEmpty()) {
            this.tiposColaboradore = FXCollections.observableArrayList(colab);
            cbOpciones.setItems(this.tiposColaboradore);
        } else {
            cerrarVentana();
        }

    }

    private int buscarColaborador(int idColaborador) {
        for (int i = 0; i < this.tiposColaboradore.size(); i++) {
            if (this.tiposColaboradore.get(i).getIdColaborador() == idColaborador) {
                return i;
            }
        }
        return 0;
    }

    private boolean validaCampo() {
        boolean esValido = true;

        if (cbOpciones.getSelectionModel().getSelectedItem() == null) {
            lbErrorSeleccion.setText("Debe seleccionar un conductor");
            esValido = false;
        }
        return esValido;
    }

    private void cerrarVentana() {
        Stage base = (Stage) cbOpciones.getScene().getWindow();
        base.close();
    }
}
