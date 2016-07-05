/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * <h1>King</h1>
 * is the class that is inherited from Asset class
 * @author amir
 */
public class King extends Asset {

    private int howManyMove;

    /**
     * It is the constructor for the King class
     * @param color
     * @param row
     * @param col
     */
    public King(AssetColor color, int row, int col) {
        super(color, row, col);
        whiteImg = new Image(getClass().getResourceAsStream("images/whiteking.png"));
        blackImg = new Image(getClass().getResourceAsStream("images/blackking.png"));
        asset.setImage(color == AssetColor.BLACK ? blackImg : whiteImg);
        asset.setLayoutX(col * 50 + 105);
        asset.setLayoutY(row * 50 + 105);
    }

    /**
     * It is another constructor for the King class that also take number of moves of the 
     * King as parameter
     * @param howManyMove
     * @param color
     * @param row
     * @param col
     */
    public King(int howManyMove, AssetColor color, int row, int col) {
        super(color, row, col);
        this.howManyMove = howManyMove;
    }

    /**
     *
     * @return Integer number of moves that was made by the Kng
     */
    public int getHowManyMove() {
        return howManyMove;
    }

    @Override
    public void makePossibleMove(Asset[][] as) {
        posiibleMove.clear();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    try {
                        if (as[row + i][col + j] == null) {
                            posiibleMove.add(new Cord(row + i, col + j));
                        } else if (as[row + i][col + j].getColor() != color) {
                            posiibleMove.add(new Cord(row + i, col + j));
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
        Asset kingBackup = this;

    }

    @Override
    public boolean move(int row, int col) {
        makePossibleMove(ChessGame.b.getAssets());
        if(ChessGame.b.check(color, this, new Cord(row, col))){
            return false;
        }
        
        boolean flag = false;
        Rook r = null;
        if (howManyMove == 0) {
            if (this.col + 2 == col) {
                if (posiibleMove.contains(new Cord(this.row, this.col + 1))) {
                    try {
                        if (ChessGame.b.getAssets()[this.row][7].getClass().getSimpleName().equals("Rook")) {
                            if (((Rook) ChessGame.b.getAssets()[this.row][7]).getHowManyMove() == 0) {
                                if (checkPathIsClear(true)) {
                                    if (!ChessGame.b.check(color)) {
                                        posiibleMove.add(new Cord(row, col));
                                        flag = true;
                                        r = (Rook) ChessGame.b.getAssets()[this.row][7];
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            } else if (this.col - 2 == col) {
                if (posiibleMove.contains(new Cord(this.row, this.col - 1))) {
                    try {
                        if (ChessGame.b.getAssets()[this.row][0].getClass().getSimpleName().equals("Rook")) {
                            if (((Rook) ChessGame.b.getAssets()[this.row][0]).getHowManyMove() == 0) {
                                if (checkPathIsClear(false)) {
                                    /*Asset[][] a = ChessGame.b.copy(ChessGame.b.getAssets());
                                    a[row][col] = a[this.row][this.col];
                                    a[this.row][this.col] = null;*/
                                    if (!ChessGame.b.check(color)) {
                                        posiibleMove.add(new Cord(row, col));
                                        flag = true;
                                        r = (Rook) ChessGame.b.getAssets()[this.row][0];
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        if (super.move(row, col)) {
            asset.setLayoutX(col * 50 + 105);
            asset.setLayoutY(row * 50 + 105);
            howManyMove++;
            if (flag) {
                int d = r.getCol() == 7 ? -1 : 1;
                r.setPosition(row, col + d);
            }
            return true;
        }
        return false;
    }

    private boolean checkPathIsClear(boolean isRight) {
        if (isRight) {
            for (int i = this.col + 1; i < 7; i++) {
                if (ChessGame.b.getAssets()[row][i] != null) {
                    return false;
                }
            }
        } else {
            for (int i = this.col - 1; i > 0; i--) {
                if (ChessGame.b.getAssets()[row][i] != null) {
                    return false;
                }
            }
        }
        return true;
    }
}
