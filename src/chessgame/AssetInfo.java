/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import java.io.Serializable;

/**
 *
 * @author amir
 */
public class AssetInfo implements Serializable{
    private int row;
    private int col;
    private String color;
    private String className;
    private boolean dead;

    public AssetInfo(int row, int col, String color, String className, boolean dead) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.className = className;
    }

 

    public String getClassName() {
        return className;
    }

    public int getCol() {
        return col;
    }

    public String getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }
    
}
