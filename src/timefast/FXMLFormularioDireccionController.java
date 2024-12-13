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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionEstados();
        configuracionSeleccionEstado();
    }

    @FXML
    private void Guardar(ActionEvent event) {
        obtenerDatos();
        if (validarCampos()) {
            if (isEditable) {
                editarDireccion(direccionEdicion);
            } else {
                registrarDireccion(direccionEdicion);
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Error en la validacion de los datos, porfavor ingrese nuevamente la informacion", "ERROR");

        }
    }

    public void incializarValoresCliente(Integer idCliente, Integer idEnvioOrigen, Integer idDIreccionDestino) {
        if (idCliente != 0) {
            obtenerDireccionCliente(idCliente);
            if (direccionEdicion != null) {
                isEditable = true;
            }
        }
        if (idEnvioOrigen != 0) {
            obtenerDireccionOrigen(idEnvioOrigen);
            if (direccionEdicion != null) {
                isEditable = true;
            }
        }
        if (idEnvioOrigen != 0) {
            obtenerDireccionDestino(idDIreccionDestino);
            if (direccionEdicion != null) {
                isEditable = true;
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
        direccionEdicion = rest.getDireccion();
        direccionEdicion.setIdCliente(idCliente);
        cargarDatos(direccionEdicion);

    }

    private void obtenerDireccionOrigen(Integer idenvioOrigen) {
        RespuestaDireccion rest = DireccionesDAO.buscarDireccionOrigen(idenvioOrigen);
        direccionEdicion = rest.getDireccion();
        direccionEdicion.setIdEnvioOrigen(idenvioOrigen);
        cargarDatos(direccionEdicion);

    }

    private void obtenerDireccionDestino(Integer idenvioDestino) {
        RespuestaDireccion rest = DireccionesDAO.buscarDireccionDestino(idenvioDestino);
        direccionEdicion = rest.getDireccion();
        direccionEdicion.setIdEnvioDestino(idenvioDestino);
        cargarDatos(direccionEdicion);
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
        limpiarCamposError();
        if (direccionEdicion.getCalle().isEmpty() || direccionEdicion.getCalle().length() > 100) {
            lbErrorCalle.setText("campo no valido");
            valido = true;
        }
        if (direccionEdicion.getCodigoPostal().isEmpty() || direccionEdicion.getCodigoPostal().length() > 5) {
            lbErrorCodigoPostal.setText("campo no valido");
            valido = true;
        }
        if (direccionEdicion.getColonia() == null || direccionEdicion.getColonia().length() > 100) {
            lbErrorColonia.setText("campo no valido");
            valido = true;
        }
        if (direccionEdicion.getNumero().isEmpty() || direccionEdicion.getNumero().length() > 10) {
            lbErrorNumero.setText("campo no valido");
            valido = true;
        }
        if (direccionEdicion.getIdMunicipio() == null || direccionEdicion.getIdMunicipio() <= 0) {
            lbErrorMunicipio.setText("campo no valido");
            valido = true;
        }
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

    private void registrarDireccion(Direccion direccion) {
        Mensaje msj = new Mensaje();

        if (direccionEdicion.getIdCliente() != null) {
            msj = DireccionesDAO.insertarDireccionCliente(direccion);
            if (!msj.isError()) {
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "La direccion se ha regiustrado con exito", "Registro exitoso");
                cerrarVentana();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error al registrar");
            }
        }
        if (direccionEdicion.getIdEnvioDestino() != null) {
            msj = DireccionesDAO.insertarDireccionDestino(direccion);
            if (!msj.isError()) {
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "La direccion se ha regiustrado con exito", "Registro exitoso");
                cerrarVentana();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error al registrar");
            }
        }
        if (direccionEdicion.getIdEnvioOrigen() != null) {
            msj = DireccionesDAO.insertarDireccionOrigen(direccion);
            if (!msj.isError()) {
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "La direccion se ha regiustrado con exito", "Registro exitoso");
                cerrarVentana();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error al registrar");
            }
        }
    }

    private void editarDireccion(Direccion direccion) {
        Mensaje msj = new Mensaje();
        if (direccionEdicion.getIdCliente() != null) {
            msj = DireccionesDAO.editarDireccionCliente(direccion);
            if (!msj.isError()) {
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "Ladireccion se ha editado con exito", "Edicion exitosa");
                cerrarVentana();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error al actualizar");
            }
        }
        if (direccionEdicion.getIdEnvioDestino() != null) {
            msj = DireccionesDAO.editarDireccionDestino(direccion);
            if (!msj.isError()) {
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "Ladireccion se ha editado con exito", "Edicion exitosa");
                cerrarVentana();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error al actualizar");
            }
        }
        if (direccionEdicion.getIdEnvioOrigen() != null) {
            msj = DireccionesDAO.editarDireccionOrigen(direccion);
            if (!msj.isError()) {
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "Ladireccion se ha editado con exito", "Edicion exitosa");
                cerrarVentana();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, msj.getContenido(), "Error al actualizar");
            }
        }
    }
}
