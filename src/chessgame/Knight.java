/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import javafx.scene.image.Image;

/**
 * <h1>Knight</h1>
 * It is the Knight class that inherited from Asset class
 *
 * @author amir
 */
public class Knight extends Asset {

    /**
     * It is the constructor for the Knight class
     *
     * @param color
     * @param row
     * @param col
     */
    public Knight(AssetColor color, int row, int col) {
        super(color, row, col);
        whiteImg = new Image(getClass().getResourceAsStream("images/whiteknight.png"));
        blackImg = new Image(getClass().getResourceAsStream("images/blackknight.png"));
        asset.setImage(color == AssetColor.BLACK ? blackImg : whiteImg);
        asset.setLayoutX(col * 50 + 105);
        asset.setLayoutY(row * 50 + 105);
    }

    @Override
    public void makePossibleMove(Asset[][] as) {
        posiibleMove.clear();
        for (int i = -2; i <= 2; i++) {
            if (i != 0) {
                int col1 = (Math.abs(i) == 2 ? 1 : 2);
                int col2 = -(Math.abs(i) == 2 ? 1 : 2);
                try {

                    if (as[row + i][col + col1] == null) {
                        posiibleMove.add(new Cord(row + i, col + col1));
                    } else if (as[row + i][col + col1].getColor() != color) {
                        posiibleMove.add(new Cord(row + i, col + col1));
                    }
                } catch (Exception e) {
                }
                try {
                    if (as[row + i][col + col2] == null) {
                        posiibleMove.add(new Cord(row + i, col + col2));
                    } else if (as[row + i][col + col2].getColor() != color) {
                        posiibleMove.add(new Cord(row + i, col + col2));
                    }
                } catch (Exception e) {

                }
            }

        }
    }

    @Override
    public boolean move(int row, int col) {
        makePossibleMove(ChessGame.b.getAssets());
        if (ChessGame.b.check(color, this, new Cord(row, col))) {
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
