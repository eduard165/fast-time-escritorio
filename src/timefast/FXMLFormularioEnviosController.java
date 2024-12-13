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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author eduar
 */
public class FXMLFormularioEnviosController implements Initializable {

    @FXML
    private TextField tfNumeroGuia;
    @FXML
    private TextField tfCosto;
    @FXML
    private TextField tfDireccionOrigen;
    @FXML
    private TextField tfDireccionDestino;
    @FXML
    private Label lbDescripcion;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private Label lbErrorNumeroGuia;
    @FXML
    private Label lbErrorCosto;
    @FXML
    private Label lbErrorDireccionOrigen;
    @FXML
    private Label lbErrorDireccionDestino;
    @FXML
    private Label lbErrorDescripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Guardar(ActionEvent event) {
    }
    
}
