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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPassword;
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
    private void btnLogin_EventHandled(ActionEvent event) {
        //lbMsg.setText("button OK clicked.");
        //event.getSource();
        if ("".equals(txtUser.getText()) || "".equals(txtPassword.getText())) {
            lblMessage.setText("Input the user and password");
        } else {
            String userName = DataAccess.Login(txtUser.getText(), txtPassword.getText());
            if (userName != null) {
                Session.userName = userName;
                Session.userId = txtUser.getText();
                System.out.println(userName);
                
                //Send to Main game
                SceneController sc = new SceneController();
                sc.switchScene(event.getSource(), "/Display/Main.fxml");
            }
            else{
                lblMessage.setText("Incorrect credentials");
            }
        }
    }

    @FXML
    private void btnRegister_EventHandled(ActionEvent event) {
        SceneController sc = new SceneController();
        sc.switchScene(event.getSource(), "/Display/Register.fxml");
    }

}
