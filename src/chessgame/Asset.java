/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import static chessgame.ChessGame.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * It is an abstract class that all the other assets inherited from. it has 
 * almost all the fields that an asset need like row,column,color and etc.
 * @author amir
 */
public abstract class Asset {

    protected int row;
    protected int col;
    protected AssetColor color;
    protected ImageView asset = new ImageView();
    protected ArrayList<Cord> posiibleMove;
    protected static Image blackImg;
    protected static Image whiteImg;
    protected boolean dead;

    /**
     * It is the the constructor of asset class
     * @param color
     * @param row
     * @param col
     */
    public Asset(AssetColor color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
        this.posiibleMove = new ArrayList<>();
        this.asset.setOnMouseClicked(event -> {
            if (ChessGame.b.getWhichAssetIsSelected() != null) {
                if (ChessGame.b.getWhichAssetIsSelected().getColor() != this.color) {
                    if (ChessGame.b.getWhichAssetIsSelected().move(this.row, this.col)) {
                        ChessGame.b.getWhichPlayerTurn().incrimentMoveNumber();
                        if (ChessGame.b.check(AssetColor.WHITE)) {
                                ChessGame.b.getWhitePlayer().setChecked(true);
                            } else{
                                ChessGame.b.getWhitePlayer().setChecked(false);
                            }
                            if (ChessGame.b.check(AssetColor.BLACK)) {
                                ChessGame.b.getBlackPlayer().setChecked(true);
                            } else {
                                ChessGame.b.getBlackPlayer().setChecked(false);
                            }
                        ChessGame.b.ChangePlayer();
                      
                        ChessGame.b.setWhichAssetIsSelected(null);
                    }
                } else if (ChessGame.b.getWhichPlayerTurn().getColor() == color) {

                    ChessGame.b.setWhichAssetIsSelected(this);
             

                }
            } else if (ChessGame.b.getWhichPlayerTurn().getColor() == color) {
                ChessGame.b.setWhichAssetIsSelected(this);
          
            }

        });
    }
    
    /**
     *
     * @return integer that is the row of the asset
     */
    public int getRow() {
        return row;
    }

    /**
     * 
     * @return integer that is the col of the asset
     */
    public int getCol() {
        return col;
    }

    /**
     *
     * @return the color of the asset
     */
    public AssetColor getColor() {
        return color;
    }

    /**
     *
     * @return a ImageView that is basicly the shape of the object in the scene
     */
    public ImageView getAsset() {
        return asset;
    }

    /**
     * this is used for getting all the possible moves that asset can have
     * @return an ArrayList of Cord 
     */
    public ArrayList<Cord> getPosiibleMove() {
        return posiibleMove;
    }

    /**
     *
     * @return
     */
    public boolean isDead() {
        return dead;
    }
    
    /**
     * It sets the row of the asset
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * It set the column of the asset
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }
    
    /**
     * set weather is asset dead or not
     * @param dead
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * It is an abstract method for making all the possible moves that an asset
     * can make
     * @param as
     */
    public abstract void makePossibleMove(Asset[][] as);
    
    /**
     * It set the position of the asset
     * @param row
     * @param col
     */
    public void setPosition(int row,int col){};
    
    /**
     * It check that a move to certain position is possible or not
     * @param row
     * @param col
     * @return true if move was made false otherwise
     */
    public boolean move(int row, int col) {
        for (Cord c : posiibleMove) {
            if (c.equal(new Cord(row, col))) {
                if (ChessGame.b.getAssets()[row][col] != null) {
                    if (!ChessGame.b.getAssets()[row][col].getClass().getSimpleName().equals("King")) {
                        ChessGame.b.destroy(ChessGame.b.getAssets()[row][col]);
                    }else{
                        return false;
                    }
                }
                ChessGame.b.getAssets()[row][col] = this;
                ChessGame.b.getAssets()[this.row][this.col] = null;
                this.row = row;
                this.col = col;
                Player player = color == AssetColor.BLACK ? ChessGame.b.getWhitePlayer() : ChessGame.b.getBlackPlayer();
                for (Asset a : player.getRemainedAsset()) {
                    if (a.getClass().getSimpleName().equals("Pawn")) {
                        ((Pawn) a).setEn_pasant(false);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
