/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timefast;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author eduar
 */
public class FXMLAdministrarEnviosController implements Initializable {

    @FXML
    private TableView<?> tblGestionEnvios;
    @FXML
    private TableColumn<?, ?> colNumeroGuia;
    @FXML
    private TableColumn<?, ?> colCosto;
    @FXML
    private TableColumn<?, ?> colDescripcion;
    @FXML
    private TableColumn<?, ?> colEstatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Registrar(ActionEvent event) {
    }

    @FXML
    private void Editar(ActionEvent event) {
    }

    @FXML
    private void Eliminar(ActionEvent event) {
    }

    @FXML
    private void Buscar(KeyEvent event) {
    }

    @FXML
    private void AgregarOrigen(ActionEvent event) {
    }

    @FXML
    private void AgregarDestino(ActionEvent event) {
    }

    @FXML
    private void AsignarConductor(ActionEvent event) {
    }
    
}
