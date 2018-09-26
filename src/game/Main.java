/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author kiran
 */
public class Main extends Application {

    Stage primaryStage;
    Scene scene;
    private GridPane root;
    private Button[][] gridButtons = new Button[3][3];

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                Button b = new Button("");
                gridButtons[row][column] = b;
                gridButtons[row][column].setPrefSize(300, 300);
                grid.add(gridButtons[row][column], row, column);
                grid.setGridLinesVisible(true);

            }
        }
        VBox vbox = new VBox();
        vbox.getChildren().add(grid);
        scene = new Scene(vbox, 600, 600);

        primaryStage.setTitle("Clue Classic with a Twist");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
