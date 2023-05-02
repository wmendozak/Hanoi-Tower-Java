/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Display;

import DB.DataAccess;
import Hanoi.Session;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javaapplication1.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author wmend
 */
public class RegisterController implements Initializable {

    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtName;
    @FXML
    private Label lblMessage;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void RegisterUser(ActionEvent event) throws IOException {
        String output;
        if ("".equals(txtUser.getText()) 
                || "".equals(txtPassword.getText())
                || "".equals(txtName.getText())) {
            lblMessage.setText("Input the user, password and name.");
        } else {
            output = DataAccess.RegisterUser(txtUser.getText(), txtPassword.getText(), txtName.getText());
            if (output != null 
                    || output != "") {
                goToLogin(event.getSource());
            }else{
                lblMessage.setText(output);
            }
        }
    }
    
    @FXML
    private void CancelRegistration(ActionEvent event) throws IOException {
        goToLogin(event.getSource());
    }
    
    private void goToLogin(Object source) throws IOException{
        SceneController sc = new SceneController();
        sc.switchScene(source, "/Display/Login.fxml");
    }
}
