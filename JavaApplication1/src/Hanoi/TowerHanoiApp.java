/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hanoi;

//import java.awt.Rectangle;
import java.util.Comparator;
import java.util.Optional;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author wmend
 */
public class TowerHanoiApp extends Application {

    private static final int NUM_CIRCLES = 3;
    private static final int NUM_PEG = 3;
    private static int steps;
    private Optional<Circle> selectedCircle = Optional.empty();
    Alert a = new Alert(AlertType.NONE);

    @Override
    public void start(Stage stage) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Parent createContent() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        Pane root = new Pane();
        AnchorPane ap = new  AnchorPane();
        root.setPrefSize(400 * NUM_PEG, 400);
        steps = 0;
        for (int i = 0; i < NUM_PEG; i++) {
            Tower tower = new Tower(i * 400, 0, i);
            if (i == 0) {
                for (int j = NUM_CIRCLES; j > 0; j--) {
                    Circle circle = new Circle(30 + j * 20, null);
                    circle.setStroke(setColor(j));
                    circle.setStrokeWidth(circle.getRadius() / 30.0);
                    //circle.setId(STYLESHEET_MODENA);
                    tower.addCircle(circle);
                }
            }
            root.getChildren().add(tower);
        }

        return root;
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
                    a = new Alert(AlertType.CONFIRMATION, "Congratulations", ButtonType.OK);
                    a.show();
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
            } else {
                if (circle.getRadius() < topMost.getRadius()) {
                    getChildren().add(circle);
                    steps++;
                    
                    if (_towerId == NUM_PEG - 1) {
                        //long el = getChildren().stream().map(n -> (Circle) n).count();
                        if (getChildren().size() == NUM_CIRCLES + 1) {
                            _end = true;
                        }
                    }
                }
            }
        }
    }
}
