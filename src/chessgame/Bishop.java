/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import javafx.scene.image.Image;

/**
 * <h1>Bishop</h1>
 * It is the Bishop class that is inherited from Asset class
 * @author amir
 */
public class Bishop extends Asset {

    /**
     * It is the constructor for the Bishop class
     * @param color
     * @param row
     * @param col
     */
    public Bishop(AssetColor color, int row, int col) {
        super(color, row, col);
        whiteImg = new Image(getClass().getResourceAsStream("images/whitebishop.png"));
        blackImg = new Image(getClass().getResourceAsStream("images/blackbishop.png"));
        asset.setImage(color == AssetColor.BLACK ? blackImg : whiteImg);
        asset.setLayoutX(col * 50 + 105);
        asset.setLayoutY(row * 50 + 105);
    }
    

    @Override
    public void makePossibleMove(Asset[][] as) {
        posiibleMove.clear();
        for (int m = -1; m <= 1; m += 2) {
            for (int n = -1; n <= 1; n += 2) {
                for (int i = row + m, j = col + n; (m > 0 ? i < 8 : i >= 0) && (n > 0 ? j < 8 : j >= 0); i += m, j += n) {
                    try {
                        if (as[i][j] == null) {
                            posiibleMove.add(new Cord(i, j));
                        } else if (as[i][j].getColor() != color) {
                            posiibleMove.add(new Cord(i, j));
                            break;
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
    }


    @Override
    public boolean move(int row, int col) {
        makePossibleMove(ChessGame.b.getAssets());
        if(ChessGame.b.check(color, this, new Cord(row, col))){
            return false;
        }
        if (super.move(row, col)) {
            asset.setLayoutX(col * 50 + 105);
            asset.setLayoutY(row * 50 + 105);
            return true;
        }
        return false;
    }

}
