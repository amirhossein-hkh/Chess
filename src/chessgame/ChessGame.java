/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author amir
 */
public class ChessGame extends Application {

    /**
     *
     */
    public static Board b;

    /**
     *
     */
    public static MainMenu menu;
    
    @Override
    public void start(Stage primaryStage) {
        menu = new MainMenu(primaryStage);
        menu.makeMenu();       
        primaryStage.setTitle("Chess");
        primaryStage.setScene(menu.getScene());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
