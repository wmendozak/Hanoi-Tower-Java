/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Display;

import DB.DataAccess;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javaapplication1.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author wmend
 */
public class ScoreListController implements Initializable {

    @FXML
    private ListView<String> lvScore;
    @FXML
    private Button btnBack;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadListView();
    }   
    
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchScene(event.getSource(), "/Display/Main.fxml");
    }

    private void loadListView() {
        List<String> scores = DataAccess.listScore("");
        lvScore.getItems().addAll(scores);
    }
    
}
