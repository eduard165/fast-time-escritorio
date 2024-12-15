package timefast;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import modelo.dao.DireccionesDAO;
import modelo.pojo.Direccion;
import modelo.pojo.Estado;
import modelo.pojo.Mensaje;
import modelo.pojo.Municipio;
import modelo.pojo.respuestasPojos.RespuestaDireccion;
import observador.NotificadorOperaciones;
import utils.Utilidades;

public class FXMLFormularioDireccionController implements Initializable {

    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfColonia;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private ComboBox<Municipio> cbMunicipios;
    @FXML
    private ComboBox<Estado> cbEstados;
    @FXML
    private Label lbErrorCalle;
    @FXML
    private Label lbErrorNumero;
    @FXML
    private Label lbErrorColonia;
    @FXML
    private Label lbErrorCodigoPostal;
    @FXML
    private Label lbErrorMunicipio;
    @FXML
    private Label lbErrorEstado;

    private Direccion direccionEdicion = new Direccion();
    private boolean isEditable = false;
    ObservableList<Municipio> municipios;
    ObservableList<Estado> estados;
    private NotificadorOperaciones observador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionEstados();
        configuracionSeleccionEstado();
    }

    @FXML
    private void Guardar(ActionEvent event) {
        if (!validarCampos()) {
            obtenerDatos();
            System.out.println(isEditable);

            if (isEditable) {
                System.out.println(direccionEdicion.toString());
                editarDireccion(direccionEdicion);
                observador.notificacionOperacion("", "");

            } else {
                System.out.println(direccionEdicion.toString());
                registrarDireccion(direccionEdicion);
                observador.notificacionOperacion("", "");

            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Error en la validacion de los datos, porfavor ingrese nuevamente la informacion " + validarCampos(), "ERROR");

        }
    }

    public void incializarValoresCliente(NotificadorOperaciones observador, Integer idCliente, Integer idEnvioOrigen, Integer idEnvioDestino) {
        this.observador = observador;

        if (idCliente != null) {
            obtenerDireccionCliente(idCliente);
            if (direccionEdicion.getIdDireccion() != null) {
                isEditable = true;
            }
        }
        if (idEnvioOrigen != null) {
            obtenerDireccionOrigen(idEnvioOrigen);
            if (direccionEdicion.getIdDireccion() != null) {
                isEditable = true;
                System.out.println("ENTREEEEE");
            }
        }
        if (idEnvioDestino != null) {
            obtenerDireccionDestino(idEnvioDestino);
            if (direccionEdicion.getIdDireccion() != null) {
                isEditable = true;
                System.out.println("ENTREEEEE");

            }
        }
    }

    private void cargarInformacionEstados() {
        estados = FXCollections.observableArrayList();
        List<Estado> info = DireccionesDAO.obtenerEstados();
        estados.addAll(info);
        cbEstados.setItems(estados);

    }

    private void cargarInformacionMunicipios(int idEstado) {
        municipios = FXCollections.observableArrayList();
        List<Municipio> inf = DireccionesDAO.obtenerMunicipios(idEstado);
        municipios.addAll(inf);
        cbMunicipios.setItems(municipios);
    }

    private void configuracionSeleccionEstado() {
        cbEstados.valueProperty().addListener(new ChangeListener<Estado>() {
            @Override
            public void changed(ObservableValue<? extends Estado> observable, Estado oldValue, Estado newValue) {
                cargarInformacionMunicipios(newValue.getIdEstado());
            }
        });
    }

    private void obtenerDireccionCliente(Integer idCliente) {
        RespuestaDireccion rest = DireccionesDAO.buscarDireccionCliente(idCliente);
        if (rest.getDireccion() != null) {
            direccionEdicion = rest.getDireccion();
            cargarDatos(direccionEdicion);
        }
        direccionEdicion.setIdCliente(idCliente);

    }

    private void obtenerDireccionOrigen(Integer idenvioOrigen) {
        RespuestaDireccion rest = DireccionesDAO.buscarDireccionOrigen(idenvioOrigen);
        if (rest.getDireccion() != null) {
            direccionEdicion = rest.getDireccion();
            cargarDatos(direccionEdicion);
        }
        direccionEdicion.setIdEnvioOrigen(idenvioOrigen);

    }

    private void obtenerDireccionDestino(Integer idenvioDestino) {
        RespuestaDireccion rest = DireccionesDAO.buscarDireccionDestino(idenvioDestino);
        if (rest.getDireccion() != null) {
            direccionEdicion = rest.getDireccion();
            cargarDatos(direccionEdicion);
        }
        direccionEdicion.setIdEnvioDestino(idenvioDestino);

    }

    private void cargarDatos(Direccion direccion) {
        if (direccion != null) {
            tfCalle.setText(direccion.getCalle());
            tfCodigoPostal.setText(direccion.getCodigoPostal());
            tfColonia.setText(direccion.getColonia());
            tfNumero.setText(direccion.getNumero());

            int estadoIndex = buscarEstado(direccion.getIdEstado());
            if (estadoIndex != -1) {
                cbEstados.getSelectionModel().select(estadoIndex);
                cargarInformacionMunicipios(direccion.getIdEstado());

                int municipioIndex = buscarMunicipioInt(direccion.getIdMunicipio());
                if (municipioIndex != -1) {
                    cbMunicipios.getSelectionModel().select(municipioIndex);
                }
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "ERROR AL CARGAR LA INFORMACION", "ERROR");
        }
    }

    private int buscarMunicipioInt(int idMunicipio) {
        for (int i = 0; i < municipios.size(); i++) {
            if (municipios.get(i).getIdMunicipio() == idMunicipio) {
                return i;
            }
        }
        return 0;
    }

    private int buscarEstado(int idEstado) {
        for (int i = 0; i < estados.size(); i++) {
            if (estados.get(i).getIdEstado() == idEstado) {
                return i;
            }
        }
        return 0;
    }

    private void limpiarCamposError() {
        lbErrorCalle.setText("");
        lbErrorCodigoPostal.setText("");
        lbErrorColonia.setText("");
        lbErrorMunicipio.setText("");
        lbErrorNumero.setText("");
        lbErrorEstado.setText("");
    }

    private boolean validarCampos() {
        boolean valido = false;

        // Validación de campos de texto
        if (tfCalle.getText() == null || tfCalle.getText().isEmpty()) {
            lbErrorCalle.setText("La calle no puede estar vacía.");
            valido = true;
        }

        if (tfCodigoPostal.getText() == null || !tfCodigoPostal.getText().matches("\\d{5}")) {
            lbErrorCodigoPostal.setText("Código Postal debe contener exactamente 5 números.");
            valido = true;
        } else {
            try {
                Integer.parseInt(tfCodigoPostal.getText());  // Asegura que sea un entero válido
            } catch (NumberFormatException e) {
                lbErrorCodigoPostal.setText("Código Postal debe ser un número válido.");
                valido = true;
            }
        }

        if (tfNumero.getText() == null || tfNumero.getText().isEmpty()) {
            lbErrorNumero.setText("El número no puede estar vacío.");
            valido = true;
        } else {
            try {
                Integer.parseInt(tfNumero.getText());  // Asegura que el número sea válido
            } catch (NumberFormatException e) {
                lbErrorNumero.setText("El número debe ser un valor entero.");
                valido = true;
            }
        }

        if (tfColonia.getText() == null || tfColonia.getText().isEmpty()) {
            lbErrorColonia.setText("La colonia no puede estar vacía.");
            valido = true;
        }

        // Validación de ComboBox
        if (cbMunicipios.getSelectionModel().isEmpty()) {
            lbErrorMunicipio.setText("Debe seleccionar un municipio.");
            valido = true;
        }

        if (cbEstados.getSelectionModel().isEmpty()) {
            lbErrorEstado.setText("Debe seleccionar un cliente.");
            valido = true;
        }

        // Devuelve `false` si todos los campos son correctos
        return valido;
    }

    private void obtenerDatos() {
        direccionEdicion.setCalle(tfCalle.getText());
        direccionEdicion.setCodigoPostal(tfCodigoPostal.getText());
        direccionEdicion.setColonia(tfColonia.getText());
        direccionEdicion.setNumero(tfNumero.getText());
        direccionEdicion.setIdMunicipio((cbMunicipios.getSelectionModel().getSelectedItem() != null)
                ? cbMunicipios.getSelectionModel().getSelectedItem().getIdMunicipio() : null);

    }

    private void cerrarVentana() {
        Stage base = (Stage) tfCalle.getScene().getWindow();
        base.close();
    }

    private void registrarDireccionCliente(Direccion direccion) {
        Mensaje msj = DireccionesDAO.insertarDireccionCliente(direccion);
        System.out.println(msj.isError());
        procesarMensaje(msj, "La dirección se ha registrado con éxito");
    }

    private void registrarDireccionDestino(Direccion direccion) {
        Mensaje msj = DireccionesDAO.insertarDireccionDestino(direccion);
        System.out.println(msj.isError());
        procesarMensaje(msj, "La dirección se ha registrado con éxito");
    }

    private void registrarDireccionOrigen(Direccion direccion) {
        Mensaje msj = DireccionesDAO.insertarDireccionOrigen(direccion);
        System.out.println(msj.isError());
        procesarMensaje(msj, "La dirección se ha registrado con éxito");
    }

    private void registrarDireccion(Direccion direccion) {
        if (direccionEdicion.getIdCliente() != null) {
            registrarDireccionCliente(direccion);
        }
        if (direccionEdicion.getIdEnvioDestino() != null) {
            registrarDireccionDestino(direccion);
        }
        if (direccionEdicion.getIdEnvioOrigen() != null) {
            registrarDireccionOrigen(direccion);
        }
    }

    private void editarDireccionCliente(Direccion direccion) {
        Mensaje msj = DireccionesDAO.editarDireccionCliente(direccion);
        procesarMensaje(msj, "La dirección se ha editado con éxito");
    }

    private void editarDireccionDestino(Direccion direccion) {
        Mensaje msj = DireccionesDAO.editarDireccionDestino(direccion);
        procesarMensaje(msj, "La dirección se ha editado con éxito");
    }

    private void editarDireccionOrigen(Direccion direccion) {
        Mensaje msj = DireccionesDAO.editarDireccionOrigen(direccion);
        procesarMensaje(msj, "La dirección se ha editado con éxito");
    }

    private void editarDireccion(Direccion direccion) {
        if (direccionEdicion.getIdCliente() != null) {
            editarDireccionCliente(direccion);
        }
        if (direccionEdicion.getIdEnvioDestino() != null) {
            editarDireccionDestino(direccion);

        }
        if (direccionEdicion.getIdEnvioOrigen() != null) {
            editarDireccionOrigen(direccion);

        }
    }

    private void procesarMensaje(Mensaje msj, String mensajeExitoso) {
        if (!msj.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION, msj.getContenido(), "Operación exitosa");
            System.err.println(msj.getContenido());
            cerrarVentana();
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error al registrar/editar");
        }
    }

}
