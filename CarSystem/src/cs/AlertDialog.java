/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs;

import javafx.scene.control.Alert;

/**
 *
 * @author whitney-pc
 */
public class AlertDialog {
    static public void AlertDialog(String context){
        Alert alert;
        alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("INFORMATION DIALOG");
        alert.setContentText(context);
        alert.showAndWait();
        
    }
}
