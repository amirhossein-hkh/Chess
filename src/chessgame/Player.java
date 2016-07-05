/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class is used for storing data of a player like color , its assets and etc
 * @author amir
 */
public class Player {

    private String name;
    private boolean turn ;
    private int numberOfMoves;
    private ArrayList<Asset> remainedAsset;
    private ArrayList<Asset> kiledAsset;
    private AssetColor color;
    private boolean checked;
    private boolean checkmated;

    /**
     * it is the constructor of Player class
     * @param name
     * @param color
     */
    public Player(String name, AssetColor color) {
        remainedAsset = new ArrayList<>();
        kiledAsset = new ArrayList<>();

        this.name = name;

        this.color = color;

        if (this.color.equals(AssetColor.WHITE)) {
          turn = true;
        }
        
    }

    /**
     * 
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return number of the moves that the player has made
     */
    public int getNumberOfMoves() {
        return numberOfMoves;
    }
    
    /**
     * It increments the number of moves that player has made by one
     */
    public void incrimentMoveNumber(){
        numberOfMoves++;
    }

    /**
     *
     * @return the remain Assets of the player as an ArrayList
     */
    public ArrayList<Asset> getRemainedAsset() {
        return remainedAsset;
    }

    /**
     *
     * @return the killed assets of the player as ArrayList
     */
    public ArrayList<Asset> getKiledAsset() {
        return kiledAsset;
    }

    /**
     *
     * @return the color of player choose
     */
    public AssetColor getColor() {
        return color;
    }

    /**
     *
     * @return weather the player is checked or not
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * set the check property to true or false
     * @param a boolean checked
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     *
     * @return a boolean weather the player is checkmated or not
     */
    public boolean isCheckmated() {
        return checkmated;
    }

    /**
     * set the checkmated property to true or false
     * @param a boolean checkmated
     */
    public void setCheckmated(boolean checkmated) {
        this.checkmated = checkmated;
    }

    /**
     * set the name of player
     * @param a String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the number of moves that player has made
     * @param numberOfMoves
     */
    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    /**
     * set the color of the player
     * @param color
     */
    public void setColor(AssetColor color) {
        this.color = color;
    }
    
    

}
