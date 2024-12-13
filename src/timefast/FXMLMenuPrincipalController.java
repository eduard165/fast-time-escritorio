/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timefast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.pojo.Colaborador;
import utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author eduar
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Label lbInformacionColaboradorEnSesion;
    private Colaborador colaborador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializarValores(Colaborador colaborador) {
        this.colaborador = colaborador;
        colaboradorActual();
    }

    private void colaboradorActual() {
        lbInformacionColaboradorEnSesion.setText(colaborador.getNombre() + " " + colaborador.getApellidoPaterno() + " " + colaborador.getApellidoMaterno() + "\n" + colaborador.getCorreoElectronico());
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {
        Utilidades.AletaSimple(Alert.AlertType.CONFIRMATION, "Cierre de sesion", "Esperamos regreses pronto");
        try {
            Stage escenarioBase = (Stage) lbInformacionColaboradorEnSesion.getScene().getWindow();

            Parent principal = FXMLLoader.load(getClass().getResource("FXMLInisioSesion.fxml"));
            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Inisio de sesion");
        } catch (IOException ex) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, ex + "/n error al cambiar", "Error al cambiar de pantalla");
        }
    }

    @FXML
    private void irColaboradores(MouseEvent event) {
        cambiarVentana("Aministrador Colaboradores", "FXMLAdministrarColaborador.fxml");
    }

    @FXML
    private void irClientes(MouseEvent event) {
        cambiarVentana("Aministrador Clientes", "FXMLAdministrarClientes.fxml");
    }

    @FXML
    private void irUnidades(MouseEvent event) {
    }

    @FXML
    private void irEnvios(MouseEvent event) {
    }

    @FXML
    private void irPaquetes(MouseEvent event) {
    }

    private void cambiarVentana(String titulo, String archivo) {
        try {
            Stage escenarioAdministrador = new Stage();
            Parent administrador = FXMLLoader.load(getClass().getResource(archivo));
            Scene scene = new Scene(administrador);
            escenarioAdministrador.setScene(scene);
            escenarioAdministrador.setTitle(titulo);
            escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
            escenarioAdministrador.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Lo sentimos ocurrio un error inesperado, intentalo nuevamente", "Error" );
        }
    }

}
