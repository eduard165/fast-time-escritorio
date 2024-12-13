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

/**
 * FXML Controller class
 *
 * @author eduar
 */
public class FXMLFormularioUnidadesController implements Initializable {

    @FXML
    private Label lbErrorMarca;
    @FXML
    private Label lbErrorModelo;
    @FXML
    private Label lbErrorAnio;
    @FXML
    private Label lbErrorVIN;
    @FXML
    private Label lbErrorNumeroInterno;
    @FXML
    private Label lbErrorTipo;
    @FXML
    private Label lbErrorConductor;

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
