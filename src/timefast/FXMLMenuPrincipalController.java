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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.pojo.Colaborador;
import utils.Utilidades;

public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Label lbInformacionColaboradorEnSesion;
    @FXML
    private Pane clPanelColaborador;
    @FXML
    private Pane clPanelEnvios;
    @FXML
    private Pane clPanelCliente;
    @FXML
    private Pane clPanelPaquetes;
    @FXML
    private Pane clPanelUnidades;
    private Colaborador colaborador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarValores(Colaborador colaborador) {
        this.colaborador = colaborador;
        colaboradorActual();
    }

    private void colaboradorActual() {
        lbInformacionColaboradorEnSesion.setText("Nombre : " + colaborador.getNombre() + " " + colaborador.getApellidoPaterno() + " " + colaborador.getApellidoMaterno() + "\n" + "Correo Electronico : " + colaborador.getCorreoElectronico());
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAdministrarColaborador.fxml"));
            Parent root = loader.load();
            FXMLAdministrarColaboradorController controlador = loader.getController();
            controlador.inicializarValores(colaborador);
            Stage escenarioAdministrador = new Stage();
            Scene scene = new Scene(root);
            escenarioAdministrador.setScene(scene);
            escenarioAdministrador.setTitle("Administrador colaboradores");
            escenarioAdministrador.initModality(Modality.APPLICATION_MODAL);
            escenarioAdministrador.showAndWait();
        } catch (IOException ex) {
            Utilidades.AletaSimple(Alert.AlertType.ERROR, "Error al cambiar de pantalla", "Error");
        }
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

    @FXML
    private void salidaHoverColaborador(MouseEvent event) {
        clPanelColaborador.setStyle("-fx-border-color: grey;-fx-border-width: 2;-fx-border-radius: 15;-fx-background-radius: 15;-fx-background-color: white;");

    }

    @FXML
    private void entradaHoverColaborador(MouseEvent event) {
        clPanelColaborador.setStyle("-fx-border-color: blue;-fx-border-width: 2;-fx-border-radius: 15;-fx-background-radius: 15;-fx-background-color: lightblue;");

    }

    @FXML
    private void salidaHoverEnvios(MouseEvent event) {
        clPanelEnvios.setStyle("-fx-border-color: grey;-fx-border-width: 2;-fx-border-radius: 15;-fx-background-radius: 15;-fx-background-color: white;");
    }

    @FXML
    private void entradaHoverEnvios(MouseEvent event) {
        clPanelEnvios.setStyle("-fx-border-color: green;-fx-border-width: 2;-fx-border-radius: 15;-fx-background-radius: 15;-fx-background-color: lightgreen;");
    }

    @FXML
    private void salidaHoverCliente(MouseEvent event) {
        clPanelCliente.setStyle("-fx-border-color: grey;-fx-border-width: 2;-fx-border-radius: 15;-fx-background-radius: 15;-fx-background-color: white;");
    }

    @FXML
    private void entradaHoverCliente(MouseEvent event) {
        clPanelCliente.setStyle("-fx-border-color: yellow;-fx-border-width: 2;-fx-border-radius: 15;-fx-background-radius: 15;-fx-background-color: lightyellow;");
    }

    @FXML
    private void salidaHoverPaquetes(MouseEvent event) {
        clPanelPaquetes.setStyle("-fx-border-color: grey;-fx-border-width: 2;-fx-border-radius: 15;-fx-background-radius: 15;-fx-background-color: white;");
    }

    @FXML
    private void entradaHoverPaquetes(MouseEvent event) {
        clPanelPaquetes.setStyle("-fx-border-color: #ec4899;-fx-border-width: 2;-fx-border-radius: 15;-fx-background-radius: 15;-fx-background-color: #ef9dc7;");
    }

    @FXML
    private void salidaHoverUnidades(MouseEvent event) {
        clPanelUnidades.setStyle("-fx-border-color: grey;-fx-border-width: 2;-fx-border-radius: 15;-fx-background-radius: 15;-fx-background-color: white;");
    }

    @FXML
    private void entradaHoverUnidades(MouseEvent event) {
        clPanelUnidades.setStyle("-fx-border-color: purple;-fx-border-width: 2;-fx-border-radius: 15;-fx-background-radius: 15;-fx-background-color: #d8b4fe;");
    }

}
