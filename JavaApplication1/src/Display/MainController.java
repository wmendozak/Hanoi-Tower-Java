/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Display;

import Hanoi.Session;
import Hanoi.TowerHanoiApp;
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
public class MainController implements Initializable {

    @FXML
    private Button btnScore;
    @FXML
    private Button btnPlay;
    @FXML
    private TextField txtDisks;
    @FXML
    private Label lblUserName;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblUserName.setText(Session.userName);
        txtDisks.setText("3");
    }
    
    @FXML
    private void playGame(ActionEvent event) throws IOException {
        Session.numDisks = Integer.valueOf(txtDisks.getText());
        SceneController sc = new SceneController();
        sc.switchScene(event.getSource(), "/Display/TowerHanoi.fxml");
    }
    
    @FXML
    private void showScore(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchScene(event.getSource(), "/Display/ScoreList.fxml");
    }
    
}
