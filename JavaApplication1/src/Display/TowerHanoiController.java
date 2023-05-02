/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Display;

import DB.DataAccess;
import Hanoi.Session;
import Hanoi.TowerHanoiApp;
import com.sun.javafx.scene.layout.region.Margins;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javaapplication1.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author wmend
 */
public class TowerHanoiController implements Initializable {

    private static int numCircles;
    private static final int NUM_PEG = 3;
    private static int steps;
    private Optional<Circle> selectedCircle = Optional.empty();
    Alert a = new Alert(Alert.AlertType.NONE);
    private static int timePlayed;
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            timePlayed++;
        }
    };

    @FXML
    private Pane PaneMain;
    @FXML
    private Label lblSteps;
    @FXML
    private Button btnBack;

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.switchScene(event.getSource(), "/Display/Main.fxml");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        PaneMain.setPrefSize(400 * NUM_PEG, 400);
        numCircles = Session.numDisks;
        for (int i = 0; i < NUM_PEG; i++) {
            TowerHanoiController.Tower tower = new TowerHanoiController.Tower(i * 400, 0, i);
            if (i == 0) {
                for (int j = numCircles; j > 0; j--) {
                    Circle circle = new Circle(30 + j * 20, null);
                    circle.setStroke(setColor(j));
                    circle.setStrokeWidth(circle.getRadius() / 30.0);
                    //circle.setId(STYLESHEET_MODENA);
                    tower.addCircle(circle);
                }
            }
            PaneMain.getChildren().add(tower);
        }
        steps = 0;
        lblSteps.setText(String.valueOf(steps));
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    private Paint setColor(int i) {
        Paint paint = null;
        switch (i) {
            case 0 ->
                paint = Color.BLUE;
            case 1 ->
                paint = Color.RED;
            case 2 ->
                paint = Color.YELLOW;
            case 3 ->
                paint = Color.GREEN;
            case 4 ->
                paint = Color.BLACK;
            case 5 ->
                paint = Color.ORANGE;
            case 6 ->
                paint = Color.PURPLE;
        }
        return paint;
    }

    private class Tower extends StackPane {

        private int _towerId;
        private boolean _end;

        Tower(int x, int y, int towerId) {
            setTranslateX(x);
            setTranslateY(y);
            setPrefSize(400, 400);
            _towerId = towerId;
            _end = false;
            Rectangle bg = new Rectangle(25, 25);
            bg.setOnMouseClicked(e -> {
                if (selectedCircle.isPresent()) {
                    addCircle(selectedCircle.get());
                    selectedCircle = Optional.empty();
                } else {
                    selectedCircle = Optional.ofNullable(getTopMost());
                }
                if (_end) {
                    a = new Alert(Alert.AlertType.CONFIRMATION, "Congratulations", ButtonType.OK);
                    a.setContentText("Congratulations!\n"
                            + "You made it in " + String.valueOf(steps) + " steps\n"
                            + "in " + String.valueOf(timePlayed) + " seconds");
                    a.show();
                    registerGame();
                    SceneController sc = new SceneController();
                    sc.switchScene(e.getSource(), "/Display/Main.fxml");
                }
            });

            getChildren().add(bg);
        }

        private Circle getTopMost() {
            return getChildren().stream()
                    .filter(n -> n instanceof Circle)
                    .map(n -> (Circle) n)
                    .min(Comparator.comparingDouble(Circle::getRadius))
                    .orElse(null);
        }

        void addCircle(Circle circle) {
            Circle topMost = getTopMost();

            if (topMost == null) {
                getChildren().add(circle);
                steps++;
                lblSteps.setText(String.valueOf(steps));
            } else {
                if (circle.getRadius() < topMost.getRadius()) {
                    getChildren().add(circle);
                    steps++;
                    lblSteps.setText(String.valueOf(steps));
                    if (_towerId == NUM_PEG - 1) {
                        //long el = getChildren().stream().map(n -> (Circle) n).count();
                        if (getChildren().size() == numCircles + 1) {
                            _end = true;
                            timerTask.cancel();
                            timer.cancel();
                        }
                    }
                }
            }
        }

        private void registerGame() {
            DataAccess.registerScore(Session.userId, numCircles, NUM_PEG, steps, timePlayed);
        }

    }
}
