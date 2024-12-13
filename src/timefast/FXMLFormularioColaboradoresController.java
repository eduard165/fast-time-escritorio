/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timefast;

import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import modelo.dao.ColaboradorDAO;
import modelo.dao.RolDAO;
import modelo.pojo.Colaborador;
import modelo.pojo.Mensaje;
import modelo.pojo.Rol;
import observador.NotificadorOperaciones;
import utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author eduar
 */
public class FXMLFormularioColaboradoresController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfCURP;
    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private ComboBox<Rol> cbRol;
    @FXML
    private TextField tfCorreoElectronico;
    @FXML
    private ImageView imFoto;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorApellidoMaterno;
    @FXML
    private Label lbErrorApellidoPaterno;
    @FXML
    private Label lbErrorCorreoElectronico;
    @FXML
    private Label lbErrorCURP;
    @FXML
    private Label lbErrorNumeroPersonal;
    @FXML
    private Label lbErrorContraseña;
    @FXML
    private Label lbErrorRol;

    private NotificadorOperaciones observador;
    private Colaborador colaboradorEdicion;
    private boolean isEditable = false;
    private File fotografia;
    ObservableList<Rol> tiposColaboradores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTiposUsuario();
    }

    public void inicializarValores(NotificadorOperaciones observador, Colaborador colaboradorEdicion) {
        this.observador = observador;
        this.colaboradorEdicion = colaboradorEdicion;
        if (colaboradorEdicion != null) {
            isEditable = true;
            cargarDatosEdicion();
            cargarFotoEdicion();
        }

    }

    @FXML
    private void registrarColaborador(ActionEvent event) {
        Colaborador colaborador = new Colaborador();
        colaborador = obtenerDatos();
        if (fotografia != null) {
            subirFotoColaborador(fotografia);
        }
        if (validarCampos(colaborador)) {
            if (isEditable) {
                colaborador.setIdColaborador(this.colaboradorEdicion.getIdColaborador());
                editarDatosColaborador(colaborador);
            } else {
                guardarDatos(colaborador);
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.WARNING, "Error en la validacion de los datos, porfavor ingrese nuevamente la informacion", "ERROR");
        }
    }

    @FXML
    private void registrarFoto(ActionEvent event) {
        fotografia = mostrarDialogoSeleccionado();
        if (fotografia != null) {
            mostrarImagen(fotografia);

        }
    }

    private void subirFotoColaborador(File foto) {
        try {
            BufferedImage buffer = ImageIO.read(foto);
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            ImageIO.write(buffer, "png", byteArrayOutputStream);
            byte[] fotoBytes = byteArrayOutputStream.toByteArray();

            Mensaje mensaje = ColaboradorDAO.subirFotoColaborador(isEditable ? colaboradorEdicion.getIdColaborador() : null, fotoBytes);

            if (!mensaje.isError()) {
                mostrarImagen(foto);
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, "Error al subir la foto", mensaje.getContenido());
            }
        } catch (IOException e) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Error", "Hubo un error al procesar la imagen seleccionada.");
        }
    }

    private void cargarDatosEdicion() {
        tfNombre.setText(colaboradorEdicion.getNombre());
        tfApellidoMaterno.setText(colaboradorEdicion.getApellidoMaterno());
        tfApellidoPaterno.setText(colaboradorEdicion.getApellidoPaterno());
        tfCURP.setText(colaboradorEdicion.getCURP());
        tfCorreoElectronico.setText(colaboradorEdicion.getCorreoElectronico());
        tfNumeroPersonal.setText(colaboradorEdicion.getNumeroPersonal());
        tfNumeroPersonal.setEditable(false);
        tfPassword.setText(colaboradorEdicion.getPassword());
        cbRol.getSelectionModel().select(buscarRol(colaboradorEdicion.getIdRol()));
    }

    private int buscarRol(int idRol) {
        for (int i = 0; i < tiposColaboradores.size(); i++) {
            if (tiposColaboradores.get(i).getIdRol() == idRol) {
                return i;
            }
        }
        return 0;
    }

    private void cargarTiposUsuario() {
        List<Rol> roles = RolDAO.obtenerRol();
        if (roles != null && !roles.isEmpty()) {
            tiposColaboradores = FXCollections.observableArrayList(roles);
            cbRol.setItems(tiposColaboradores);
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Error al cargar", "Hubo un error al momento de cargar los roles, intentelo nuevamente");
        }
    }

    private void limpiarErrores() {
        lbErrorNombre.setText("");
        lbErrorApellidoPaterno.setText("");
        lbErrorApellidoMaterno.setText("");
        lbErrorCURP.setText("");
        lbErrorCorreoElectronico.setText("");
        lbErrorContraseña.setText("");
        lbErrorRol.setText("");
        lbErrorNumeroPersonal.setText("");

    }

    private boolean validarCampos(Colaborador colaborador) {
        boolean valido = true;
        limpiarErrores();

        if (colaborador.getNombre().isEmpty() || colaborador.getNombre().length() > 50) {
            lbErrorNombre.setText("El nombre debe tener hasta 50 caracteres.");
            valido = false;
        }

        if (colaborador.getApellidoPaterno().isEmpty() || colaborador.getApellidoPaterno().length() > 50) {
            lbErrorApellidoPaterno.setText("El apellido paterno debe tener hasta 50 caracteres.");
            valido = false;
        }

        if (colaborador.getApellidoMaterno().isEmpty() || colaborador.getApellidoMaterno().length() > 50) {
            lbErrorApellidoMaterno.setText("El apellido materno debe tener hasta 50 caracteres.");
            valido = false;
        }

        if (colaborador.getCURP().isEmpty() || colaborador.getCURP().length() > 18) {
            lbErrorCURP.setText("El CURP debe tener hasta 18 caracteres.");
            valido = false;
        }

        if (colaborador.getCorreoElectronico().isEmpty()
                || !colaborador.getCorreoElectronico().matches("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")) {
            lbErrorCorreoElectronico.setText("Correo electrónico inválido.");
            valido = false;
        }

        if (colaborador.getPassword().isEmpty()
                || !colaborador.getPassword().matches("^[a-zA-Z0-9]{8,}$")) {
            lbErrorContraseña.setText("La contraseña debe tener al menos 8 caracteres y solo letras y números.");
            valido = false;
        }

        if (colaborador.getIdRol() == null || colaborador.getIdRol() <= 0) {
            lbErrorRol.setText("El rol debe ser un valor válido.");
            valido = false;
        }

        if (!isEditable) {
            if (colaborador.getNumeroPersonal().isEmpty() || colaborador.getNumeroPersonal().length() > 20) {
                lbErrorNumeroPersonal.setText("El número personal debe tener hasta 20 caracteres.");
                valido = false;
            }
        }

        return valido;
    }

    private void guardarDatos(Colaborador colaborador) {
        Mensaje mjs = ColaboradorDAO.registrarColaborador(colaborador);
        if (!mjs.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "Registro exitoso", "El colaborador se ha registrado con exito");
            cerrarVentana();
            observador.notificacionOperacion("Nuevo registro", colaborador.getNombre());
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al guardar");
        }
    }

    private void editarDatosColaborador(Colaborador colaborador) {
        Mensaje mjs = ColaboradorDAO.editarColaborador(colaborador);
        if (!mjs.isError()) {
            Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "El colaborador se ha editado con exito", "Edicion exitosa");
            cerrarVentana();
            observador.notificacionOperacion("Nueva edicion", colaborador.getNombre());
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, mjs.getContenido(), "Error al guardar");
        }
    }

    private void cerrarVentana() {
        Stage base = (Stage) tfApellidoMaterno.getScene().getWindow();
        base.close();
    }

    private Colaborador obtenerDatos() {
        Colaborador colaborador = new Colaborador();
        colaborador.setNumeroPersonal(tfNumeroPersonal.getText());
        colaborador.setNombre(tfNombre.getText());
        colaborador.setApellidoPaterno(tfApellidoPaterno.getText());
        colaborador.setApellidoMaterno(tfApellidoMaterno.getText());
        colaborador.setCorreoElectronico(tfCorreoElectronico.getText());
        colaborador.setCURP(tfCURP.getText());
        colaborador.setPassword(tfPassword.getText());
        colaborador.setIdRol((cbRol.getSelectionModel().getSelectedItem() != null)
                ? cbRol.getSelectionModel().getSelectedItem().getIdRol() : null);
        return colaborador;
    }

    private File mostrarDialogoSeleccionado() {
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");

        FileChooser.ExtensionFilter filtroArchivos = new FileChooser.ExtensionFilter("Archivos PNG(*.png,*.jpg,*.jpeg)", "*.PNG", "*.JPG", "*.JPEG");
        dialogoSeleccionImg.getExtensionFilters().add(filtroArchivos);

        Stage escena = (Stage) tfApellidoMaterno.getScene().getWindow();
        return dialogoSeleccionImg.showOpenDialog(escena);
    }

    private void mostrarImagen(File foto) {
        try {
            BufferedImage buffer = ImageIO.read(foto);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            imFoto.setImage(image);
        } catch (IOException e) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "ERROR", "Hubo un error al mostrar la foto");
        }
    }

    private void cargarFotoEdicion() {
        if (colaboradorEdicion != null && isEditable) {
            Colaborador colaboradorFoto = ColaboradorDAO.obtenerFotoColaborador(colaboradorEdicion.getIdColaborador());
            String fotoBase64 = colaboradorFoto.getFotoBase64();

            if (fotoBase64 != null && !fotoBase64.isEmpty()) {
                try {
                    fotoBase64 = fotoBase64.trim().replaceAll("\\s+", "");
                    byte[] imagenBytes = java.util.Base64.getDecoder().decode(fotoBase64);
                    BufferedImage bufferedImage = ImageIO.read(new java.io.ByteArrayInputStream(imagenBytes));
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imFoto.setImage(image);

                } catch (IllegalArgumentException e) {
                    Utilidades.AletaSimple(Alert.AlertType.ERROR, "Error al cargar la imagen", "La cadena Base64 es inválida.");
                } catch (IOException e) {
                    Utilidades.AletaSimple(Alert.AlertType.ERROR, "Error al mostrar la imagen", "Hubo un error al procesar la imagen.");
                }
            }
        }
    }

}
