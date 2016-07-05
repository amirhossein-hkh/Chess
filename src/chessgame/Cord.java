/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

/**<h1>Cord</h1>
 * It is the class for keeping the coordinate of Assets 
 * @author amir
 */
public class Cord implements Cloneable{

    private int row;
    private int col;

    /**
     *
     */
    public Cord() {
    }

    /**
     *
     * @param row
     * @param col
     */
    public Cord(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     *
     * @return
     */
    public int getCol() {
        return col;
    }

    /**
     *
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @param c
     * @return
     */
    public boolean equal(Cord c) {
        if (c.col == col && c.row == row) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.row;
        hash = 79 * hash + this.col;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cord other = (Cord) obj;
        if (this.row != other.row) {
            return false;
        }
        if (this.col != other.col) {
            return false;
        }
        return true;
    }

    @Override
    public Cord clone() throws CloneNotSupportedException {
        return (Cord)super.clone(); 
    }
    
    

    @Override
    public String toString() {
        return "Cord{" + "row=" + row + ", col=" + col + '}';
    }
    
}
