/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author amir
 */
public class SaveInfo implements Serializable{
    private ArrayList<AssetInfo> info;
    private String whitePlayerName;
    private String blackPlayerName;
    private String whosTurn;
    private int whiteMovement;
    private int blackMovement;
    private boolean whiteChecked;
    private boolean whiteCheckmated;
    private boolean blackChecked;
    private boolean blackCheckmated;

    public SaveInfo(String whitePlayerName, String blackPlayerName, String whosTurn, int whiteMovement, int blackMovement, boolean whiteChecked, boolean whiteCheckmated, boolean blackChecked, boolean blackCheckmated) {
        this.whitePlayerName = whitePlayerName;
        this.blackPlayerName = blackPlayerName;
        this.whosTurn = whosTurn;
        this.whiteMovement = whiteMovement;
        this.blackMovement = blackMovement;
        this.whiteChecked = whiteChecked;
        this.whiteCheckmated = whiteCheckmated;
        this.blackChecked = blackChecked;
        this.blackCheckmated = blackCheckmated;
        info = new ArrayList<>();
    }

    public int getBlackMovement() {
        return blackMovement;
    }

    public int getWhiteMovement() {
        return whiteMovement;
    }

    public boolean isBlackChecked() {
        return blackChecked;
    }

    public boolean isBlackCheckmated() {
        return blackCheckmated;
    }

    public boolean isWhiteChecked() {
        return whiteChecked;
    }

    public boolean isWhiteCheckmated() {
        return whiteCheckmated;
    }
           

    public String getBlackPlayerName() {
        return blackPlayerName;
    }

    public ArrayList<AssetInfo> getInfo() {
        return info;
    }

    public String getWhitePlayerName() {
        return whitePlayerName;
    }

    public String getWhosTurn() {
        return whosTurn;
    }
    
}
