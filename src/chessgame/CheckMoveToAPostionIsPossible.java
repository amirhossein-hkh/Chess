/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

/**
 * It called the makePossibleMove for the object and set protected color for the
 * squares that are one of the possible moves
 * @author amir
 */

public class CheckMoveToAPostionIsPossible extends Thread {

    private Asset asset;
    private Asset[][] map;
    
    public CheckMoveToAPostionIsPossible(Asset asset,Asset[][] map) {
        this.asset = asset;
        this.map = map;
    }

    @Override
    public void run() {
        asset.makePossibleMove(map);
        for (Cord c : asset.posiibleMove) {
            ChessGame.b.getSqures()[c.getRow()][c.getCol()].setProtectedBy(asset.getColor());
        }
    }

    public Asset getAsset() {
        return asset;
    }

}
