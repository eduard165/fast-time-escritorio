/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.scene.control.Alert;

/**
 *
 * @author eduar
 */
public class Utilidades {
    
    public static void AletaSimple(Alert.AlertType tipo, String contenido, String titulo){  
        Alert alertaBienvenida = new Alert(tipo);
        alertaBienvenida.setTitle(titulo);
        alertaBienvenida.setHeaderText(null);
        alertaBienvenida.setContentText(contenido);
        alertaBienvenida.showAndWait();   
    }
}
