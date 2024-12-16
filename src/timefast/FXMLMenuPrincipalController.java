
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

public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Label lbInformacionColaboradorEnSesion;
    private Colaborador colaborador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        Utilidades.AletaSimple(Alert.AlertType.INFORMATION, "Esperemos que vuelva pronto", "Cierre sesión");

        try {
            Stage stage = (Stage) lbInformacionColaboradorEnSesion.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLInicioSesion.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio de Sesión");
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Ocurrió un error al cerrar sesión, intente de nuevo", "Error");
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
        cambiarVentana("Administrador Unidades", "FXMLAdministrarUnidades.fxml");
    }

    @FXML
    private void irEnvios(MouseEvent event) {
        cambiarVentana("Administrador Envios", "FXMLAdministrarEnvios.fxml");
    }

    @FXML
    private void irPaquetes(MouseEvent event) {
        cambiarVentana("Administrador Paquetes", "FXMLAdministrarPaquetes.fxml");
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
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Lo sentimos ocurrio un error inesperado, intentalo nuevamente", "Error");
        }
    }

}
