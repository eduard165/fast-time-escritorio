/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timefast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.dao.LoginDAO;
import modelo.pojo.Colaborador;
import modelo.pojo.respuestasPojos.RespuestaColaborador;
import utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lbErrorNumeroPersonal;
    @FXML
    private Label lbErrorPassword;

    private Colaborador colaborador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String numeroPersonal = tfNumeroPersonal.getText();
        String password = tfPassword.getText();
        if (validarCampos(numeroPersonal, password)) {
            verificarCredencialesSistema(numeroPersonal, password);
        }
    }

    private boolean validarCampos(String numeroPersonal, String password) {
        boolean camposValidos = true;
        lbErrorNumeroPersonal.setText("");
        lbErrorPassword.setText("");
        if (numeroPersonal.isEmpty() || numeroPersonal.length() > 20) {
            lbErrorNumeroPersonal.setText("Numero de personal no valido");
            camposValidos = false;
        }
        if (password.isEmpty()) {
            lbErrorPassword.setText("Contraseña no valida");
            camposValidos = false;

        }
        return camposValidos;
    }

    private void irPantallaPrincipal() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMenuPrincipal.fxml"));
            Parent root = loader.load();
            FXMLMenuPrincipalController controlador = loader.getController();
            controlador.inicializarValores(colaborador);

            Stage escenarioBase = (Stage) lbErrorPassword.getScene().getWindow();
            Scene escenaPrincipal = new Scene(root);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Menu Principal");
        } catch (IOException ex) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, ex + "Error al cambiar de pantalla", "Error");
        }

    }

    private void verificarCredencialesSistema(String numeroPersonal, String password) {
        RespuestaColaborador respuestaLogin = LoginDAO.iniciarSesion(numeroPersonal, password);
        if (respuestaLogin.getColaborador().getIdRol() != 3) {
            if (respuestaLogin.isError() == false) {
                colaborador = respuestaLogin.getColaborador();
                Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "Bienvenido(a) al sistema de GymForte", "Time Fast");
                irPantallaPrincipal();
            } else {
                Utilidades.AletaSimple(Alert.AlertType.ERROR, respuestaLogin.getContenido(), "Error");
            }
        } else {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Usuario no valido.", "Error");
        }
    }

}
