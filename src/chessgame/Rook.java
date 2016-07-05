/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import javafx.scene.image.Image;

/**
 *
 * @author amir
 */
public class Rook extends Asset {

    private int howManyMove;

    /**
     *
     * @param color
     * @param row
     * @param col
     */
    public Rook(AssetColor color,int row,int col) {
        super(color,row,col);
        whiteImg = new Image(getClass().getResourceAsStream("images/whiterook.png"));
        blackImg = new Image(getClass().getResourceAsStream("images/blackrook.png"));
        asset.setImage(color == AssetColor.BLACK ? blackImg : whiteImg);
        asset.setLayoutX(col * 50 + 105);
        asset.setLayoutY(row * 50 + 102);
    }

    /**
     * It is another constructor for the Rook class that also take how many moves
     * was made by Rook as ap parameter
     * @param howManyMove
     * @param color
     * @param row
     * @param col
     */
    public Rook(int howManyMove, AssetColor color, int row, int col) {
        super(color, row, col);
        this.howManyMove = howManyMove;
    }
        
    /**
     *
     * @return Integer the number of moves that was made by Rook
     */
    public int getHowManyMove() {
        return howManyMove;
    }
    
    @Override
    public void makePossibleMove(Asset[][] as) {
        posiibleMove.clear();
        for (int i = col + 1; i < 8; i++) {
            try {
                if (as[row][i] == null) {
                    posiibleMove.add(new Cord(row, i));
                } else if (as[row][i].getColor() != color) {
                    posiibleMove.add(new Cord(row, i));
                    break;
                } else {
                    break;
                }
            } catch (Exception e) {

            }
        }
        for (int i = col - 1; i >= 0; i--) {
            try {
                if (as[row][i] == null) {
                    posiibleMove.add(new Cord(row, i));
                } else if (as[row][i].getColor() != color) {
                    posiibleMove.add(new Cord(row, i));
                    break;
                } else {
                    break;
                }
            } catch (Exception e) {

            }
        }
        for (int i = row + 1; i < 8; i++) {
            try {
                if (as[i][col] == null) {
                    posiibleMove.add(new Cord(i, col));
                } else if (as[i][col].getColor() != color) {
                    posiibleMove.add(new Cord(i, col));
                    break;
                } else {
                    break;
                }
            } catch (Exception e) {

            }
        }
        for (int i = row - 1; i >= 0; i--) {
            try {
                if (as[i][col] == null) {
                    posiibleMove.add(new Cord(i, col));
                } else if (as[i][col].getColor() != color) {
                    posiibleMove.add(new Cord(i, col));
                    break;
                } else {
                    break;
                }
            } catch (Exception e) {

            }
        }

    }

    @Override
    public void setPosition(int row, int col) {
        ChessGame.b.getAssets()[row][col] = this;
        ChessGame.b.getAssets()[this.row][this.col] = null;
        this.row = row;
        this.col = col;
        asset.setLayoutX(col * 50 + 105);
        asset.setLayoutY(row * 50 + 102);
    }
    
    @Override
    public boolean move(int row, int col) {
        if(ChessGame.b.check(color, this, new Cord(row, col))){
            return false;
        }
        makePossibleMove(ChessGame.b.getAssets());
        if (super.move(row, col)) {
            asset.setLayoutX(col * 50 + 105);
            asset.setLayoutY(row * 50 + 102);
            howManyMove++;
            return true;
        }
        return false;
    }

}
