/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import javafx.scene.shape.Rectangle;

/**
 * <h1>ChessSqure</h1>
 * It is a Square that has been inherited from Rectangle class and
 * had more properties like row,col,and the color of asset that protecting the
 * square.
 * @author amir
 */
public class ChessSqure extends Rectangle{

    private int row;
    private int col;
    private AssetColor protectedBy;
    
    /**
     * it is the constructor for the ChessSqure class
     * @param row
     * @param col
     */
    public ChessSqure(int row, int col) {
        this.row = row;
        this.col = col;
        this.setWidth(50);
        this.setHeight(50);
        this.setX(50 * col + 100);
        this.setY(50 * row + 100);
    }

    /**
     *
     * @return integer (the row of square)
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @return integer (the col of square)
     */
    public int getCol() {
        return col;
    }

    /**
     *
     * @return the color of the asset that protecting the square
     */
    public AssetColor getProtectedBy() {
        return protectedBy;
    }

    /**
     * it set the protected color 
     * @param protectedBy
     */
    public void setProtectedBy(AssetColor protectedBy) {
        this.protectedBy = protectedBy;
    }
    
}
