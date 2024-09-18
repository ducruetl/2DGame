package pathfinding;

import tile.Tile;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static java.lang.Character.getNumericValue;

public class TileNode implements Comparable<TileNode>{
    int g, h, row, col;
    TileNode parentNode;

    public TileNode(int row, int col, int g, int h, TileNode parentNode) {
        this.row = row;
        this.col = col;
        this.g = g;
        this.h = h;
        this.parentNode = parentNode;
    }

    public int getF() {
        return g + h;
    }

    @Override
    public int compareTo(TileNode o) {
        return Integer.compare(this.getF(), o.getF());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TileNode tileNode = (TileNode) obj;
        return row == tileNode.row && col == tileNode.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
