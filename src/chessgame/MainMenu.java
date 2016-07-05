/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * It is the class for creating the main menu
 *
 * @author amir
 */
public class MainMenu {

    private Board b;
    private Scene scene;
    private Stage window;

    /**
     * It is the constructor for the main menu class
     *
     * @param window
     */
    public MainMenu(Stage window) {
        this.window = window;
    }

    /**
     *
     * @return a Board object
     */
    public Board getB() {
        return b;
    }

    /**
     *
     * @return the Scene of main menu
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * It is the method that make the main menu scene
     */
    public void makeMenu() {
        Group root = new Group();
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("images/mainbackground.jpg")));
        root.getChildren().add(background);
        StackPane menu = new StackPane();
        menu.setLayoutX(30);
        menu.setLayoutY(150);
        Label chess = new Label(" Main Menu");
        chess.setFont(Font.font(20));
        chess.setTextFill(Color.web("#dbcda8"));
        chess.setLayoutX(40);
        chess.setLayoutY(105);
        Rectangle r1 = new Rectangle(130, 40, Color.web("#48434b"));
        r1.setArcHeight(10);
        r1.setArcWidth(10);
        r1.setLayoutX(30);
        r1.setLayoutY(105);
        root.getChildren().addAll(r1, chess);
        Rectangle r = new Rectangle(130, 150, Color.web("#48434b"));
        r.setArcHeight(10);
        r.setArcWidth(10);
        menu.getChildren().add(r);
        VBox buttonKeeper = new VBox(5);
        buttonKeeper.setAlignment(Pos.CENTER);
        Button singelPlayer = new Button("   Single Player", new ImageView(new Image(getClass().getResourceAsStream("images/singleplayer.png"))));
        String buttonStyle = "-fx-background-color: #3c3b3c; -fx-background-radius:5;-fx-text-fill: #dbcda8;";
        String inButtonstyle = "-fx-background-color: #565358; -fx-background-radius:5;-fx-text-fill: #dbcda8;";
        singelPlayer.setStyle(buttonStyle);
        singelPlayer.setOnAction(event -> singelPlayerInput());
        Button towPlayer = new Button("Two Player", new ImageView(new Image(getClass().getResourceAsStream("images/twoplayer.png"))));
        towPlayer.setStyle(buttonStyle);
        towPlayer.setOnAction(event -> {
            twoPlayerInput();
            System.out.println("two player");
                });
        Button load = new Button("     Load          ", new ImageView(new Image(getClass().getResourceAsStream("images/load.png"))));
        load.setStyle(buttonStyle);
        load.setOnAction(event -> {
            System.out.println("Loading");
            load();
        });
        Button exit = new Button("       Exit          ", new ImageView(new Image(getClass().getResourceAsStream("images/exit.png"))));
        exit.setStyle(buttonStyle);
        exit.setOnAction(event -> window.close());
        towPlayer.setOnMouseMoved(event -> ((Button) event.getSource()).setStyle(inButtonstyle));
        towPlayer.setOnMouseExited(event -> ((Button) event.getSource()).setStyle(buttonStyle));
        singelPlayer.setOnMouseMoved(event -> ((Button) event.getSource()).setStyle(inButtonstyle));
        singelPlayer.setOnMouseExited(event -> ((Button) event.getSource()).setStyle(buttonStyle));
        load.setOnMouseMoved(event -> ((Button) event.getSource()).setStyle(inButtonstyle));
        load.setOnMouseExited(event -> ((Button) event.getSource()).setStyle(buttonStyle));
        exit.setOnMouseMoved(event -> ((Button) event.getSource()).setStyle(inButtonstyle));
        exit.setOnMouseExited(event -> ((Button) event.getSource()).setStyle(buttonStyle));
        buttonKeeper.getChildren().addAll(singelPlayer, towPlayer, load, exit);
        menu.getChildren().add(buttonKeeper);
        root.getChildren().add(menu);
        scene = new Scene(root, 600, 400);
    }

    /**
     * It is a method that is called when the single player button is pressed
     */
    public void singelPlayerInput() {
        GridPane g = new GridPane();
        g.setPadding(new Insets(50));
        g.setHgap(10);
        g.setVgap(10);
        Label nameLabel = new Label("Player Name : ");
        nameLabel.setTextFill(Color.web("#dbcda8"));
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField name = new TextField("Player 1");
        name.setStyle("-fx-background-color: #3c3b3c; -fx-background-radius:5;-fx-text-fill: #dbcda8;");
        GridPane.setConstraints(name, 1, 0);
        Button ok = new Button("ok");
        ok.setStyle("-fx-background-color: #3c3b3c; -fx-background-radius:5;-fx-text-fill: #dbcda8;");
        String buttonStyle = "-fx-background-color: #3c3b3c; -fx-background-radius:5;-fx-text-fill: #dbcda8;";
        String inButtonstyle = "-fx-background-color: #565358; -fx-background-radius:5;-fx-text-fill: #dbcda8;";
        ok.setOnMouseMoved(event -> ((Button) event.getSource()).setStyle(inButtonstyle));
        ok.setOnMouseExited(event -> ((Button) event.getSource()).setStyle(buttonStyle));
        ok.setOnAction(event -> {
            b = new Board(name.getText(), "Computer");
            ChessGame.b = b;
            window.setScene(b.getScene());
        });
        GridPane.setConstraints(ok, 1, 1);
        g.getChildren().addAll(nameLabel, name, ok);
        Group root = new Group();
        root.getChildren().add(g);
        scene = new Scene(root, 600, 400, Color.web("#615f5f"));
        window.setScene(scene);
    }

    /**
     * It is a method that is called when the two player button is pressed
     */
    public void twoPlayerInput() {
        Group root = new Group();
        GridPane g = new GridPane();
        g.setPadding(new Insets(50));
        g.setHgap(10);
        g.setVgap(10);
        String buttonStyle = "-fx-background-color: #3c3b3c; -fx-background-radius:5;-fx-text-fill: #dbcda8;";
        String inButtonstyle = "-fx-background-color: #565358; -fx-background-radius:5;-fx-text-fill: #dbcda8;";
        Label whiteNameLabel = new Label("White Player Name : ");
        whiteNameLabel.setTextFill(Color.web("#dbcda8"));
        GridPane.setConstraints(whiteNameLabel, 0, 0);
        TextField whiteName = new TextField("Player 1");
        whiteName.setStyle(buttonStyle);
        GridPane.setConstraints(whiteName, 1, 0);
        Label blackNameLabel = new Label("Black Player Name : ");
        blackNameLabel.setTextFill(Color.web("#dbcda8"));
        GridPane.setConstraints(blackNameLabel, 0, 1);
        TextField blackName = new TextField("Player 2");
        blackName.setStyle(buttonStyle);
        GridPane.setConstraints(blackName, 1, 1);
        Button ok = new Button("ok");
        System.out.println("sdff");
        ok.setStyle(buttonStyle);
        ok.setOnMouseMoved(event -> ((Button) event.getSource()).setStyle(inButtonstyle));
        ok.setOnMouseExited(event -> ((Button) event.getSource()).setStyle(buttonStyle));
        GridPane.setConstraints(ok, 1, 2);
        ok.setOnAction(event -> {
            b = new Board(whiteName.getText(), blackName.getText());
            ChessGame.b = b;
            window.setScene(b.getScene());
        });
        g.getChildren().addAll(whiteNameLabel, whiteName, blackNameLabel, blackName, ok);
        root.getChildren().add(g);
        scene = new Scene(root, 600, 400, Color.web("#615f5f"));
        window.setScene(scene);
    }

    /**
     * It loads the game from a .ser file
     */
    public void load() {
        SaveInfo s = null;
        try {
            FileInputStream filein = new FileInputStream("save.ser");
            ObjectInputStream in = new ObjectInputStream(filein);
            s = (SaveInfo) in.readObject();
            in.close();
            filein.close();

        } catch (Exception e) {
        }
        b = new Board(s);
        ChessGame.b = b;
        window.setScene(b.getScene());
    }

}
