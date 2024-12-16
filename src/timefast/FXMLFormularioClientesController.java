package timefast;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.dao.ClienteDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLFormularioClientesController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCorreoElectronico;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorApellidoPaterno;
    @FXML
    private Label lbErrorApellidoMaterno;
    @FXML
    private Label lbErrorCorreoElectronico;
    @FXML
    private Label lbErrorTelefono;

    private Cliente clienteEdicion;
    private NotificadorOperaciones observador;
    private boolean isEditable = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void guardar(ActionEvent event) {
        Cliente cliente = new Cliente();
        cliente = obtenerDatos();
        if (validarDatos(cliente)) {
            if (isEditable) {
                cliente.setIdCliente(this.clienteEdicion.getIdCliente());
                editarDatosCliente(cliente);
            } else {
                registrarCliente(cliente);
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Error en la validacion de los datos, porfavor ingrese nuevamente la informacion", "ERROR");
        }
    }

    public void inicializarValores(NotificadorOperaciones observador, Cliente clienteEdicion) {
        this.observador = observador;
        this.clienteEdicion = clienteEdicion;
        if (clienteEdicion != null) {
            this.isEditable = true;
            cargarDatosEdicion();
        }
    }

    private void cargarDatosEdicion() {
        tfNombre.setText(clienteEdicion.getNombre());
        tfApellidoMaterno.setText(clienteEdicion.getApellidoMaterno());
        tfApellidoPaterno.setText(clienteEdicion.getApellidoPaterno());
        tfCorreoElectronico.setText(clienteEdicion.getCorreoElectronico());
        tfTelefono.setText(clienteEdicion.getTelefono());
    }

    private void limpiarErrores() {
        lbErrorNombre.setText("");
        lbErrorApellidoPaterno.setText("");
        lbErrorApellidoMaterno.setText("");
        lbErrorCorreoElectronico.setText("");
        lbErrorTelefono.setText("");
    }

    private boolean validarDatos(Cliente cliente) {
        boolean valido = true;
        limpiarErrores();
        if (cliente.getNombre().isEmpty() || cliente.getNombre().length() > 50) {
            lbErrorNombre.setText("El nombre no es valido");
            valido = false;
        }
        if (cliente.getApellidoPaterno().isEmpty() || cliente.getApellidoPaterno().length() > 50) {
            lbErrorApellidoPaterno.setText("El apellido paterno no es valido");
            valido = false;
        }

        if (cliente.getApellidoMaterno().isEmpty() || cliente.getApellidoMaterno().length() > 50) {
            lbErrorApellidoMaterno.setText("El apellido materno no es valido");
            valido = false;
        }

        if (cliente.getTelefono().isEmpty() || cliente.getTelefono().length() > 15) {
            lbErrorTelefono.setText("El teléfono no es valido");
            valido = false;
        }

        if (cliente.getCorreoElectronico().isEmpty() || !cliente.getCorreoElectronico().matches("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")) {
            lbErrorCorreoElectronico.setText("Correo electrónico inválido.");
            valido = false;
        }
        return valido;
    }

    private void cerrarVentana() {
        Stage base = (Stage) tfApellidoMaterno.getScene().getWindow();
        base.close();
    }

    private void registrarCliente(Cliente cliente) {
        Mensaje mjs = ClienteDAO.registrarCliente(cliente);
        if (!mjs.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION,  "El colaborador se ha registrado con exito", "Registro exitoso");
            cerrarVentana();
            observador.notificacionOperacion("Nuevo registro", cliente.getNombre());
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al guardar");
        }
    }

    private void editarDatosCliente(Cliente cliente) {
        Mensaje mjs = ClienteDAO.editarCliente(cliente);
        if (!mjs.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "El colaborador se ha editado con exito", "Edicion exitosa");
            cerrarVentana();
            observador.notificacionOperacion("Nueva edicion", cliente.getNombre());
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al guardar");
        }
    }

    private Cliente obtenerDatos() {
        Cliente cliente = new Cliente();
        cliente.setNombre(tfNombre.getText());
        cliente.setApellidoPaterno(tfApellidoPaterno.getText());
        cliente.setApellidoMaterno(tfApellidoMaterno.getText());
        cliente.setCorreoElectronico(tfCorreoElectronico.getText());
        cliente.setTelefono(tfTelefono.getText());
        return cliente;
    }
}
