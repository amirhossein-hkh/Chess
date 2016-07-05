/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import javafx.scene.image.Image;

/**
 * <h1>Queen</h1>
 * It is the Queen class that is inherited from Asset class.
 * @author amir
 */
public class Queen extends Asset {

    /**
//     * It is the constructor for the Queen class
     * @param color
     * @param row
     * @param col
     */
    public Queen(AssetColor color, int row, int col) {
        super(color, row, col);
        whiteImg = new Image(getClass().getResourceAsStream("images/whitequeen.png"));
        blackImg = new Image(getClass().getResourceAsStream("images/blackqueen.png"));
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
