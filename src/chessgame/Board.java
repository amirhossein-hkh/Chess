/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * <h1>Board</h1>
 * It is the main class of the program that all the important work has done
 * here. like initialing objects and positioning them
 *
 * @author amir
 */
public class Board {

    private ChessSqure[][] squres;
    private Asset[][] assets;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player whichPlayerTurn;
    private Asset whichAssetIsSelected;
    private Scene scene;
    private Group root;
    private Button save;
    private ArrayList<CheckMoveToAPostionIsPossible> checks = new ArrayList<>();
    private final HBox deadBlack = new HBox(0);
    private final HBox deadWhite = new HBox(0);
    public static Glow glow = new Glow(.7);

    /**
     * It is the main constructor of Board class that create objects and players
     * and positions them
     *
     * @param playername1
     * @param playername2
     */
    public Board(String playername1, String playername2) {

        deadBlack.setLayoutX(80);
        deadBlack.setLayoutY(30);
        deadWhite.setLayoutX(80);
        deadWhite.setLayoutY(520);
        root = new Group();
        setBackground();
        save = new Button();
        save.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("images/save.png"))));
        String buttonStyle = "-fx-background-color: #3c3b3c; -fx-background-radius:5;-fx-text-fill: #dbcda8;";
        String inButtonstyle = "-fx-background-color: #565358; -fx-background-radius:5;-fx-text-fill: #dbcda8;";
        save.setStyle(buttonStyle);
        save.setOnMouseMoved(event -> ((Button) event.getSource()).setStyle(inButtonstyle));
        save.setOnMouseExited(event -> ((Button) event.getSource()).setStyle(buttonStyle));
        save.setOnAction(event -> saveBoard());
        save.setLayoutX(550);
        save.setLayoutY(300);
        root.getChildren().addAll(deadBlack, deadWhite, save);
        scene = new Scene(root, 600, 600, Color.web("#545256").brighter());
        squres = new ChessSqure[8][8];
        assets = new Asset[8][8];
        whitePlayer = new Player(playername1, AssetColor.WHITE);
        whichPlayerTurn = whitePlayer;
        blackPlayer = new Player(playername2, AssetColor.BLACK);
        showInfo();
        //squres
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squres[i][j] = new ChessSqure(i, j);
                root.getChildren().add(squres[i][j]);
                if ((i + j) % 2 == 0) {
                    squres[i][j].setFill(Color.web("#ebedea"));
                } else {
                    squres[i][j].setFill(Color.web("#7d747a"));
                }
                squres[i][j].setOnMouseClicked(event -> {
                    ChessSqure r = (ChessSqure) event.getSource();
                    if (whichAssetIsSelected != null) {
                        if (assets[whichAssetIsSelected.getRow()][whichAssetIsSelected.getCol()].move(r.getRow(), r.getCol())) {
                            whichPlayerTurn.incrimentMoveNumber();
                            if (check(AssetColor.WHITE)) {
                                whitePlayer.setChecked(true);
                            } else {
                                whitePlayer.setChecked(false);
                            }
                            if (check(AssetColor.BLACK)) {
                                blackPlayer.setChecked(true);
                            } else {
                                blackPlayer.setChecked(false);
                            }
                            ChangePlayer();
                     
                            whichAssetIsSelected = null;

                        } else if (assets[whichAssetIsSelected.getRow()][whichAssetIsSelected.getCol()] != null) {

                            if (whichAssetIsSelected.getColor() == assets[whichAssetIsSelected.getRow()][whichAssetIsSelected.getCol()].getColor()) {
                                whichAssetIsSelected = assets[r.getRow()][r.getCol()];
                                try{
            
                                }catch(Exception e){}
                            }
                        }
                    } else if (assets[r.getRow()][r.getCol()] == null) {
               
                        whichAssetIsSelected = null;
                    } else if (whichPlayerTurn.getColor() == assets[r.getRow()][r.getCol()].getColor()) {
                        whichAssetIsSelected = assets[r.getRow()][r.getCol()];
                       
                    }
                });
            }
        }

        for (int i = 0; i < 8; i++) {
            assets[1][i] = new Pawn(AssetColor.BLACK, 1, i);
            blackPlayer.getRemainedAsset().add(assets[1][i]);
            root.getChildren().add(assets[1][i].getAsset());
            assets[6][i] = new Pawn(AssetColor.WHITE, 6, i);
            whitePlayer.getRemainedAsset().add(assets[6][i]);
            root.getChildren().add(assets[6][i].getAsset());

        }
        for (int i = 1; i < 8; i += 5) {
            assets[0][i] = new Knight(AssetColor.BLACK, 0, i);
            blackPlayer.getRemainedAsset().add(assets[0][i]);
            root.getChildren().add(assets[0][i].getAsset());
            assets[7][i] = new Knight(AssetColor.WHITE, 7, i);
            whitePlayer.getRemainedAsset().add(assets[7][i]);
            root.getChildren().add(assets[7][i].getAsset());
        }
        for (int i = 2; i < 8; i += 3) {
            assets[0][i] = new Bishop(AssetColor.BLACK, 0, i);
            blackPlayer.getRemainedAsset().add(assets[0][i]);
            root.getChildren().add(assets[0][i].getAsset());
            assets[7][i] = new Bishop(AssetColor.WHITE, 7, i);
            whitePlayer.getRemainedAsset().add(assets[7][i]);
            root.getChildren().add(assets[7][i].getAsset());
        }
        for (int i = 0; i < 8; i += 7) {
            assets[0][i] = new Rook(AssetColor.BLACK, 0, i);
            blackPlayer.getRemainedAsset().add(assets[0][i]);
            root.getChildren().add(assets[0][i].getAsset());
            assets[7][i] = new Rook(AssetColor.WHITE, 7, i);
            whitePlayer.getRemainedAsset().add(assets[7][i]);
            root.getChildren().add(assets[7][i].getAsset());
        }
        assets[0][3] = new Queen(AssetColor.BLACK, 0, 3);
        blackPlayer.getRemainedAsset().add(assets[0][3]);
        root.getChildren().add(assets[0][3].getAsset());
        assets[7][3] = new Queen(AssetColor.WHITE, 7, 3);
        whitePlayer.getRemainedAsset().add(assets[7][3]);
        root.getChildren().add(assets[7][3].getAsset());
        assets[0][4] = new King(AssetColor.BLACK, 0, 4);
        blackPlayer.getRemainedAsset().add(assets[0][4]);
        root.getChildren().add(assets[0][4].getAsset());
        assets[7][4] = new King(AssetColor.WHITE, 7, 4);
        whitePlayer.getRemainedAsset().add(assets[7][4]);
        root.getChildren().add(assets[7][4].getAsset());

    }

    /**
     * It is the constructor that used for loading the map,the scene and objects
     * and it take a SaveInfo object for start
     *
     * @param s
     */
    public Board(SaveInfo s) {
        deadBlack.setLayoutX(80);
        deadBlack.setLayoutY(30);
        deadWhite.setLayoutX(80);
        deadWhite.setLayoutY(520);
        root = new Group();
        setBackground();
        save = new Button("save");
        save.setOnAction(event -> saveBoard());
        save.setLayoutX(550);
        save.setLayoutY(300);
        root.getChildren().addAll(deadBlack, deadWhite, save);
        scene = new Scene(root, 600, 600, Color.web("#545256").brighter());
        squres = new ChessSqure[8][8];
        assets = new Asset[8][8];
        whitePlayer = new Player(s.getWhitePlayerName(), AssetColor.WHITE);
        blackPlayer = new Player(s.getBlackPlayerName(), AssetColor.BLACK);
        whitePlayer.setChecked(s.isWhiteChecked());
        whitePlayer.setCheckmated(s.isWhiteCheckmated());
        whitePlayer.setNumberOfMoves(s.getWhiteMovement());
        blackPlayer.setChecked(s.isBlackChecked());
        blackPlayer.setCheckmated(s.isBlackCheckmated());
        blackPlayer.setNumberOfMoves(s.getBlackMovement());
        whichPlayerTurn = s.getWhosTurn().equals(s.getWhitePlayerName()) ? whitePlayer : blackPlayer;
        showInfo();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squres[i][j] = new ChessSqure(i, j);
                root.getChildren().add(squres[i][j]);
                if ((i + j) % 2 == 0) {
                    squres[i][j].setFill(Color.web("#ebedea"));
                } else {
                    squres[i][j].setFill(Color.web("#7d747a"));
                }
                squres[i][j].setOnMouseClicked(event -> {
                    ChessSqure r = (ChessSqure) event.getSource();
                    if (whichAssetIsSelected != null) {
                        if (assets[whichAssetIsSelected.getRow()][whichAssetIsSelected.getCol()].move(r.getRow(), r.getCol())) {
                            whichPlayerTurn.incrimentMoveNumber();
                            if (check(AssetColor.WHITE)) {
                                whitePlayer.setChecked(true);
                            } else {
                                whitePlayer.setChecked(false);
                            }
                            if (check(AssetColor.BLACK)) {
                                blackPlayer.setChecked(true);
                            } else {
                                blackPlayer.setChecked(false);
                            }
                            ChangePlayer();
                         
                            whichAssetIsSelected = null;

                        } else if (assets[whichAssetIsSelected.getRow()][whichAssetIsSelected.getCol()] != null) {

                            if (whichAssetIsSelected.getColor() == assets[whichAssetIsSelected.getRow()][whichAssetIsSelected.getCol()].getColor()) {
                                whichAssetIsSelected = assets[r.getRow()][r.getCol()];
                             
                            }
                        }
                    } else if (assets[r.getRow()][r.getCol()] == null) {
          
                        whichAssetIsSelected = null;
                    } else if (whichPlayerTurn.getColor() == assets[r.getRow()][r.getCol()].getColor()) {
                        whichAssetIsSelected = assets[r.getRow()][r.getCol()];

                    }
                });
            }
        }
        for (AssetInfo info : s.getInfo()) {
            AssetColor color = info.getColor().equals("white") ? AssetColor.WHITE : AssetColor.BLACK;
            Player player = info.getColor().equals("white") ? whitePlayer : blackPlayer;
            if (info.getClassName().equals("Pawn")) {
                assets[info.getRow()][info.getCol()] = new Pawn(color, info.getRow(), info.getCol());
                player.getRemainedAsset().add(assets[info.getRow()][info.getCol()]);
                root.getChildren().add(assets[info.getRow()][info.getCol()].getAsset());
            } else if (info.getClassName().equals("Knight")) {
                assets[info.getRow()][info.getCol()] = new Knight(color, info.getRow(), info.getCol());
                player.getRemainedAsset().add(assets[info.getRow()][info.getCol()]);
                root.getChildren().add(assets[info.getRow()][info.getCol()].getAsset());
            } else if (info.getClassName().equals("Queen")) {
                assets[info.getRow()][info.getCol()] = new Queen(color, info.getRow(), info.getCol());
                player.getRemainedAsset().add(assets[info.getRow()][info.getCol()]);
                root.getChildren().add(assets[info.getRow()][info.getCol()].getAsset());
            } else if (info.getClassName().equals("Bishop")) {
                assets[info.getRow()][info.getCol()] = new Bishop(color, info.getRow(), info.getCol());
                player.getRemainedAsset().add(assets[info.getRow()][info.getCol()]);
                root.getChildren().add(assets[info.getRow()][info.getCol()].getAsset());
            } else if (info.getClassName().equals("King")) {
                assets[info.getRow()][info.getCol()] = new King(color, info.getRow(), info.getCol());
                player.getRemainedAsset().add(assets[info.getRow()][info.getCol()]);
                root.getChildren().add(assets[info.getRow()][info.getCol()].getAsset());
            } else if (info.getClassName().equals("Rook")) {
                assets[info.getRow()][info.getCol()] = new Rook(color, info.getRow(), info.getCol());
                player.getRemainedAsset().add(assets[info.getRow()][info.getCol()]);
                root.getChildren().add(assets[info.getRow()][info.getCol()].getAsset());
            }
        }
    }

    /**
     *
     * @return an 2d array of assets
     */
    public Asset[][] getAssets() {
        return assets;
    }

    /**
     *
     * @return a player that is his turn
     */
    public Player getWhichPlayerTurn() {
        return whichPlayerTurn;
    }

    /**
     *
     * @return the asset that has been selected
     */
    public Asset getWhichAssetIsSelected() {
        return whichAssetIsSelected;
    }

    /**
     *
     * @return 2d arrays of ChessSqure class
     */
    public ChessSqure[][] getSqures() {
        return squres;
    }

    /**
     *
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     *
     * @return the white player
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     *
     * @return the black player
     */
    public Player getBlackPlayer() {
        return blackPlayer;
    }

    /**
     *
     * @return the root of the scene that is a Group
     */
    public Group getRoot() {
        return root;
    }

    /**
     * It set which Asset has been selected
     *
     * @param whichAssetIsSelected
     * @return nothing
     */
    public void setWhichAssetIsSelected(Asset whichAssetIsSelected) {
        this.whichAssetIsSelected = whichAssetIsSelected;
    }

    /**
     * The method change the player to one that is his turn and if the player
     * choose the single player mode then the computer will randomly generate a
     * move that does not make the computer check
     *
     * @return nothing
     */
    public void ChangePlayer() {

        if (whichPlayerTurn.getColor() == whitePlayer.getColor()) {
            whichPlayerTurn = blackPlayer;
        } else {
            whichPlayerTurn = whitePlayer;
        }
        if (blackPlayer.getName().equals("Computer")) {
            Random r = new Random(System.nanoTime());
            if (whichPlayerTurn == blackPlayer) {
                boolean f = false;
                for (Asset a : blackPlayer.getRemainedAsset()) {
                    a.makePossibleMove(assets);
                    for (Cord c : a.posiibleMove) {
                        if (!check(AssetColor.BLACK, a, c)) {
                            f = true;
                        }
                    }
                }
                if (f) {
                    while (true) {

                        Asset a = blackPlayer.getRemainedAsset().get(r.nextInt(blackPlayer.getRemainedAsset().size()));
                        if (a.posiibleMove.size() != 0) {
                            Cord c = a.getPosiibleMove().get(r.nextInt(a.getPosiibleMove().size()));
                            if (a.move(c.getRow(), c.getCol())) {
                                blackPlayer.incrimentMoveNumber();
                                if (whichPlayerTurn.getColor() == whitePlayer.getColor()) {
                                    whichPlayerTurn = blackPlayer;
                                } else {
                                    whichPlayerTurn = whitePlayer;
                                }
                                break;
                            }
                        }

                    }
                } else {
                    blackPlayer.setCheckmated(true);
                }
            }
        }
    }

    /**
     * This method is called when a Asset capture another one then the captured
     * asset go the parts that belong to the captured Asset
     *
     * @param a
     */
    public void destroy(Asset a) {
        assets[a.getRow()][a.getCol()] = null;
        a.getAsset().setScaleX(.4);
        a.getAsset().setScaleY(.4);
        a.getAsset().setOnMouseClicked(null);
        a.setDead(true);
        if (whitePlayer.getColor().equals(a.color)) {
            whitePlayer.getRemainedAsset().remove(a);
            whitePlayer.getKiledAsset().add(a);
        } else {
            blackPlayer.getRemainedAsset().remove(a);
            blackPlayer.getKiledAsset().add(a);
        }
        if (a.getColor().equals(AssetColor.BLACK)) {
            deadBlack.getChildren().add(a.getAsset());
        } else {
            deadWhite.getChildren().add(a.getAsset());
        }
    }

    /**
     * It copies a 2d array of Assets
     *
     * @param a 2d array of asset that is going to be copied
     * @return a 2d array of asset that is a copy of the a[][] array
     */
    public Asset[][] copy(Asset[][] a) {

        Asset[][] copy = new Asset[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (a[i][j] != null) {
                    if (a[i][j].getClass().getSimpleName().equals("Pawn")) {
                        copy[i][j] = new Pawn(((Pawn) a[i][j]).getHowManyMove(), a[i][j].getColor(), i, j);
                    } else if (a[i][j].getClass().getSimpleName().equals("Knight")) {
                        copy[i][j] = new Knight(a[i][j].getColor(), i, j);
                    } else if (a[i][j].getClass().getSimpleName().equals("Bishop")) {
                        copy[i][j] = new Bishop(a[i][j].getColor(), i, j);
                    } else if (a[i][j].getClass().getSimpleName().equals("Queen")) {
                        copy[i][j] = new Queen(a[i][j].getColor(), i, j);
                    } else if (a[i][j].getClass().getSimpleName().equals("Rook")) {
                        copy[i][j] = new Rook(((Rook) a[i][j]).getHowManyMove(), a[i][j].getColor(), i, j);
                    } else if (a[i][j].getClass().getSimpleName().equals("King")) {
                        copy[i][j] = new King(((King) a[i][j]).getHowManyMove(), a[i][j].getColor(), i, j);
                    }
                }
            }
        }
        return copy;
    }

    /**
     * it set the 3 main dark rectangle for the background
     */
    public void setBackground() {
        Rectangle black = new Rectangle(80, 10, 440, 60);
        black.setFill(Color.web("#48434b"));
        black.setArcHeight(10);
        black.setArcWidth(10);

        Rectangle white = new Rectangle(80, 530, 440, 60);
        white.setFill(Color.web("#48434b"));
        white.setArcHeight(10);
        white.setArcWidth(10);

        Rectangle board = new Rectangle(80, 80, 440, 440);
        board.setFill(Color.web("#48434b"));

        root.getChildren().addAll(black, white, board);

    }

    /**
     * this method is for showing information about the game and the players
     */
    public void showInfo() {
        Label checkwhite = new Label("not check");
        checkwhite.setTextFill(Color.ORANGE);
        Label checkblack = new Label("check");
        checkblack.setTextFill(Color.ORANGE);
        Label checkmatewhite = new Label("");
        checkmatewhite.setTextFill(Color.RED);
        Label checkmateblack = new Label("");
        checkmateblack.setTextFill(Color.RED);
        root.getChildren().addAll(checkwhite, checkblack, checkmatewhite, checkmateblack);
        checkwhite.setLayoutX(200);
        checkwhite.setLayoutY(560);
        checkmatewhite.setLayoutX(300);
        checkmatewhite.setLayoutY(560);
        checkblack.setLayoutX(200);
        checkblack.setLayoutY(20);
        checkmateblack.setLayoutX(300);
        checkmateblack.setLayoutY(20);
        Color white = Color.web("#cfc19a");
        Color black = Color.web("#48434b");
        Color transparenceWhite = Color.web("#cfc19a", 0.2);
        Color transparenceBlack = Color.web("#48434b", 0.1);
        Circle whiteCircle = new Circle(20, 300, 10, white);
        Circle blackCircle = new Circle(45, 300, 10, transparenceBlack);
        Label whiteMoves = new Label(whitePlayer.getName() + " : " + whitePlayer.getNumberOfMoves());
        whiteMoves.setTextFill(Color.web("#cfc19a"));
        whiteMoves.setLayoutX(90);
        whiteMoves.setLayoutY(560);
        Label blackMoves = new Label(blackPlayer.getName() + " : " + blackPlayer.getNumberOfMoves());
        blackMoves.setTextFill(Color.web("#cfc19a"));
        blackMoves.setLayoutX(90);
        blackMoves.setLayoutY(20);
        root.getChildren().addAll(whiteCircle, blackCircle, whiteMoves, blackMoves);

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                whiteMoves.setText(whitePlayer.getName() + " : " + whitePlayer.getNumberOfMoves());
                blackMoves.setText(blackPlayer.getName() + " : " + blackPlayer.getNumberOfMoves());
                if (whitePlayer.isChecked()) {
                    checkwhite.setText("Check");
                } else {
                    checkwhite.setText("");
                }
                if (blackPlayer.isChecked()) {
                    checkblack.setText("Check");
                } else {
                    checkblack.setText("");
                }
                if (whitePlayer.isCheckmated()) {
                    checkmatewhite.setText("Checkmate");
                } else {
                    checkmatewhite.setText("");
                }
                if (blackPlayer.isCheckmated()) {
                    checkmateblack.setText("Checkmate");
                } else {
                    checkmateblack.setText("");
                }
                if (whichPlayerTurn.getColor() == AssetColor.WHITE) {
                    whiteCircle.setFill(white);
                    blackCircle.setFill(transparenceBlack);
                } else {
                    whiteCircle.setFill(transparenceWhite);
                    blackCircle.setFill(black);
                }
            }
        }.start();
    }

    /**
     * It uses for checking that is the king check or not
     *
     * @param kingColor
     * @return a boolean weather is a check or not
     */
    public boolean check(AssetColor kingColor) {
        AssetColor enemyColor = kingColor == AssetColor.BLACK ? AssetColor.WHITE : AssetColor.BLACK;
        makeSqureProtected(enemyColor, true, assets);
        Player penemy = kingColor == AssetColor.BLACK ? whitePlayer : blackPlayer;
        Player pking = kingColor == AssetColor.BLACK ? blackPlayer : whitePlayer;
        King k = null;
        for (Asset a : pking.getRemainedAsset()) {
            if (a.getClass().getSimpleName().equals("King")) {
                k = (King) a;
            }
        }
        if (squres[k.getRow()][k.getCol()].getProtectedBy() == enemyColor) {
            if (checkmate(k)) {
                pking.setCheckmated(true);
            }
            return true;
        }
        return false;
    }

    /**
     * It simulate the situation when an asset move to certain position then
     * check weather the king will be checked or not
     *
     * @param kingColor
     * @param a
     * @param to
     * @return boolean weather is check or not
     */
    public boolean check(AssetColor kingColor, Asset a, Cord to) {
        Asset[][] map = copy(assets);
        map[a.getRow()][a.getCol()] = null;
        if (a.getClass().getSimpleName().equals("Pawn")) {
            map[to.getRow()][to.getCol()] = new Pawn(((Pawn) a).getHowManyMove(), a.getColor(), to.getRow(), to.getCol());
        } else if (a.getClass().getSimpleName().equals("Knight")) {
            map[to.getRow()][to.getCol()] = new Knight(a.getColor(), to.getRow(), to.getCol());
        } else if (a.getClass().getSimpleName().equals("Bishop")) {
            map[to.getRow()][to.getCol()] = new Bishop(a.getColor(), to.getRow(), to.getCol());
        } else if (a.getClass().getSimpleName().equals("Queen")) {
            map[to.getRow()][to.getCol()] = new Queen(a.getColor(), to.getRow(), to.getCol());
        } else if (a.getClass().getSimpleName().equals("Rook")) {
            map[to.getRow()][to.getCol()] = new Rook(((Rook) a).getHowManyMove(), a.getColor(), to.getRow(), to.getCol());
        } else if (a.getClass().getSimpleName().equals("King")) {
            map[to.getRow()][to.getCol()] = new King(((King) a).getHowManyMove(), a.getColor(), to.getRow(), to.getCol());
        }

        AssetColor enemyColor = kingColor == AssetColor.BLACK ? AssetColor.WHITE : AssetColor.BLACK;
        makeSqureProtected(enemyColor, true, map);
        King k = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (map[i][j] != null) {
                    if (map[i][j].getClass().getSimpleName().equals("King") && map[i][j].getColor() == kingColor) {
                        k = (King) map[i][j];
                    }
                }
            }
        }
        
        System.out.println(k);
        System.out.println(squres[k.getRow()][k.getCol()]);
        if (squres[k.getRow()][k.getCol()].getProtectedBy() == enemyColor) {
            return true;
        }
        return false;
    }

    /**
     * This method checks that the player is checkmated or not
     *
     * @param k a king asset
     * @return boolean that is player is checkmated or not
     */
    public boolean checkmate(King k) {
        makeSqureProtected(k.getColor(), false, assets);
        AssetColor enemyColor = k.getColor() == AssetColor.BLACK ? AssetColor.WHITE : AssetColor.BLACK;
        if (squres[k.getRow()][k.getCol()].getProtectedBy() == enemyColor) {
            if (k.posiibleMove.size() != 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * It tag every square with an color that an asset will protect
     *
     * @param color
     * @param restart
     * @param map
     */
    public void makeSqureProtected(AssetColor color, boolean restart, Asset[][] map) {
        if (restart) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    squres[i][j].setProtectedBy(AssetColor.NONE);
                }
            }
        }
        int c = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (map[i][j] != null) {
                    if (map[i][j].getColor() == color) {
                        checks.add(new CheckMoveToAPostionIsPossible(map[i][j], map));
                        checks.get(checks.size() - 1).start();

                    }
                }

            }
        }
        for (CheckMoveToAPostionIsPossible ck : checks) {
            try {
                ck.join();
            } catch (Exception e) {
            }
        }

    }

    /**
     * it save the board using SaveInfo class into .ser file
     */
    public void saveBoard() {
        SaveInfo s = new SaveInfo(whitePlayer.getName(), blackPlayer.getName(), whitePlayer.getName(), whitePlayer.getNumberOfMoves(), blackPlayer.getNumberOfMoves(), whitePlayer.isChecked(), whitePlayer.isCheckmated(), blackPlayer.isChecked(), blackPlayer.isCheckmated());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (assets[i][j] != null) {
                    String color = assets[i][j].getColor() == AssetColor.BLACK ? "black" : "white";
                    s.getInfo().add(new AssetInfo(i, j, color, assets[i][j].getClass().getSimpleName(), false));
                }
            }
        }
        try {
            FileOutputStream outfile = new FileOutputStream("save.ser");
            ObjectOutputStream out = new ObjectOutputStream(outfile);
            out.writeObject(s);
            out.close();
            outfile.close();
            System.out.println("Saved");
        } catch (Exception e) {
        }

    }

}
