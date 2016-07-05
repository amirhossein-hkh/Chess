/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * <h1>Pawn</h1>
 * It is the pawn class that inherited from Asset class.
 * @author amir
 */
public class Pawn extends Asset {

    private int howManyMove;
    private boolean en_pasant;

    /**
     * It is the constructor of the pawn class
     * @param color
     * @param row
     * @param col
     */
    public Pawn(AssetColor color, int row, int col) {
        super(color, row, col);
        whiteImg = new Image(getClass().getResourceAsStream("images/whitepawn.png"));
        blackImg = new Image(getClass().getResourceAsStream("images/blackpawn.png"));
        asset.setImage(color == AssetColor.BLACK ? blackImg : whiteImg);
        asset.setLayoutX(col * 50 + 107.5);
        asset.setLayoutY(row * 50 + 103.5);

    }

    /**
     * It is another constructor of the pawn class that also take how many moves
     * the pawn was made
     * @param howManyMove
     * @param color
     * @param row
     * @param col
     */
    public Pawn(int howManyMove, AssetColor color, int row, int col) {
        super(color, row, col);
        this.howManyMove = howManyMove;
    }

    /**
     * 
     * @return Integer that how many moves was made by this pawn
     */
    public int getHowManyMove() {
        return howManyMove;
    }

    /**
     *
     * @return a boolean weather the pawn is en_passant or not 
     */
    public boolean isEn_pasant() {
        return en_pasant;
    }

    /**
     * It set the value of the en_passant
     * @param en_passant
     */
    public void setEn_pasant(boolean en_passant) {
        this.en_pasant = en_passant;
    }

    @Override
    public void makePossibleMove(Asset[][] as) {
        posiibleMove.clear();
        int d = color == AssetColor.BLACK ? 1 : -1;
        try {
            if (howManyMove == 0) {
                if (as[row + (2 * d)][col] == null) {
                    posiibleMove.add(new Cord((row + (2 * d)), col));
                }
            }
            if (as[row + d][col] == null) {
                posiibleMove.add(new Cord(row + d, col));
            }
        } catch (Exception e) {

        }
        for (int i = -1; i <= 1; i += 2) {
            try {
                if ((as[row + d][col + i] == null)) {
                    posiibleMove.add(new Cord(row + d, col + i));
                } else if (as[row + d][col + i].getColor() != color) {
                    posiibleMove.add(new Cord(row + d, col + i));
                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public boolean move(int row, int col) {
        makePossibleMove(ChessGame.b.getAssets());
        if (ChessGame.b.check(color, this, new Cord(row, col))) {
            return false;
        }
        boolean flag = false;
        int d = color == AssetColor.BLACK ? 1 : -1;

        if ((color == AssetColor.WHITE && this.row == 3) || (color == AssetColor.BLACK && this.row == 4)) {
            for (int i = -1; i < 2; i += 2) {
                try {
                    if (ChessGame.b.getAssets()[this.row][this.col + i].getClass().getSimpleName().equals("Pawn")
                            && ChessGame.b.getAssets()[this.row][this.col + i].getColor() != color) {
                        if (((Pawn) ChessGame.b.getAssets()[this.row][this.col + i]).en_pasant) {
                            if (this.row + d == row && this.col + i == col) {
                                posiibleMove.add(new Cord(row, col));

                                flag = true;
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        if (!flag) {
            if (ChessGame.b.getAssets()[row][col] == null) {
                if (this.col != col) {
                    return false;
                }
            }
        }
        if (super.move(row, col)) {
            if (howManyMove == 0) {
                en_pasant = true;
            }
            if (flag) {
                ChessGame.b.destroy(ChessGame.b.getAssets()[row - d][col]);
            }
            howManyMove++;
            asset.setLayoutX(col * 50 + 107.5);
            asset.setLayoutY(row * 50 + 103.5);
            if ((color == AssetColor.WHITE && row == 0) || (color == AssetColor.BLACK && row == 7)) {
                pawnPopUp();
            }
            return true;
        }
        return false;
    }

    /**
     * This method make a pop-up window for the pawn promotion
     */
    private void pawnPopUp() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Pawn Promotion");
        Label l = new Label("Which on do you want to become ?");
        l.setTextFill(Color.web("#dbcda8"));
        l.setFont(Font.font(20));
        l.setLayoutX(5);
        Group root = new Group();
        HBox v = new HBox(10);
        root.getChildren().addAll(l,v);
        v.setLayoutY(30);
        v.setAlignment(Pos.CENTER);
        Button queen = new Button("Queen", new ImageView(new Image(getClass().getResourceAsStream("images/queenicon.png"))));
        queen.setOnAction(e -> {
            ChessGame.b.getWhichPlayerTurn().getRemainedAsset().remove(this);
            ChessGame.b.getRoot().getChildren().remove(asset);
            ChessGame.b.getAssets()[row][col] = new Queen(color, row, col);
            ChessGame.b.getWhichPlayerTurn().getRemainedAsset().add(ChessGame.b.getAssets()[row][col]);
            ChessGame.b.getRoot().getChildren().add(ChessGame.b.getAssets()[row][col].getAsset());
            window.close();
        });
        Button bishop = new Button("Bishop", new ImageView(new Image(getClass().getResourceAsStream("images/bishopicon.png"))));
        bishop.setOnAction(e -> {
            ChessGame.b.getWhichPlayerTurn().getRemainedAsset().remove(this);
            ChessGame.b.getRoot().getChildren().remove(asset);
            ChessGame.b.getAssets()[row][col] = new Bishop(color, row, col);
            ChessGame.b.getWhichPlayerTurn().getRemainedAsset().add(ChessGame.b.getAssets()[row][col]);
            ChessGame.b.getRoot().getChildren().add(ChessGame.b.getAssets()[row][col].getAsset());
            window.close();
        });
        Button rook = new Button("Rook", new ImageView(new Image(getClass().getResourceAsStream("images/rookicon.png"))));
        rook.setOnAction(e -> {
            ChessGame.b.getWhichPlayerTurn().getRemainedAsset().remove(this);
            ChessGame.b.getRoot().getChildren().remove(asset);
            ChessGame.b.getAssets()[row][col] = new Rook(color, row, col);
            ChessGame.b.getWhichPlayerTurn().getRemainedAsset().add(ChessGame.b.getAssets()[row][col]);
            ChessGame.b.getRoot().getChildren().add(ChessGame.b.getAssets()[row][col].getAsset());
            window.close();
        });
        Button knight = new Button("Knight", new ImageView(new Image(getClass().getResourceAsStream("images/knighticon.png"))));
        knight.setOnAction(e -> {
            ChessGame.b.getWhichPlayerTurn().getRemainedAsset().remove(this);
            ChessGame.b.getRoot().getChildren().remove(asset);
            ChessGame.b.getAssets()[row][col] = new Knight(color, row, col);
            ChessGame.b.getWhichPlayerTurn().getRemainedAsset().add(ChessGame.b.getAssets()[row][col]);
            ChessGame.b.getRoot().getChildren().add(ChessGame.b.getAssets()[row][col].getAsset());
            window.close();
        });
        String buttonStyle = "-fx-background-color: #3c3b3c; -fx-background-radius:5;-fx-text-fill: #dbcda8;";
        String inButtonstyle = "-fx-background-color: #565358; -fx-background-radius:5;-fx-text-fill: #dbcda8;";
        knight.setStyle(buttonStyle);
        bishop.setStyle(buttonStyle);
        queen.setStyle(buttonStyle);
        rook.setStyle(buttonStyle);
        knight.setOnMouseMoved(event -> ((Button)event.getSource()).setStyle(inButtonstyle));
        knight.setOnMouseExited(event -> ((Button)event.getSource()).setStyle(buttonStyle));
        bishop.setOnMouseMoved(event -> ((Button)event.getSource()).setStyle(inButtonstyle));
        bishop.setOnMouseExited(event -> ((Button)event.getSource()).setStyle(buttonStyle));
        queen.setOnMouseMoved(event -> ((Button)event.getSource()).setStyle(inButtonstyle));
        queen.setOnMouseExited(event -> ((Button)event.getSource()).setStyle(buttonStyle));
        rook.setOnMouseMoved(event -> ((Button)event.getSource()).setStyle(inButtonstyle));
        rook.setOnMouseExited(event -> ((Button)event.getSource()).setStyle(buttonStyle));
        window.setOnCloseRequest(event -> event.consume());
        window.setResizable(false);
        v.getChildren().addAll(queen, bishop, rook, knight);
        Scene scene = new Scene(root,Color.web("#615f5f"));
        window.setScene(scene);
        window.showAndWait();
    }

}
