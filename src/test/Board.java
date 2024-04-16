package test;
import java.util.Arrays;

public class Board {
    private final int[][] bonuses;
    private static Board instance;
    Tile[][] tiles;
    private Board() {
        bonuses = new int[][] {
                {5, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 3, 0, 0, 5},
                {0, 4, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 4, 0},
                {0, 0, 4, 0, 0, 0, 3, 0, 3, 0, 0, 0, 4, 0, 0},
                {3, 0, 0, 4, 0, 0, 0, 3, 0, 0, 0, 4, 0, 0, 3},
                {0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0},
                {0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0},
                {0, 0, 3, 0, 0, 0, 3, 0, 3, 0, 0, 0, 3, 0, 0},
                {5, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 5},
                {0, 0, 3, 0, 0, 0, 3, 0, 3, 0, 0, 0, 3, 0, 0},
                {0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0},
                {3, 0, 0, 4, 0, 0, 0, 3, 0, 0, 0, 4, 0, 0, 3},
                {0, 0, 4, 0, 0, 0, 3, 0, 3, 0, 0, 0, 4, 0, 0},
                {0, 4, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 4, 0},
                {5, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 3, 0, 0, 5}
        };
        tiles = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                tiles[i][j] = null;
            }
        }
    }
    public static Board getBoard() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }
    public Tile[][] getTiles() {
        Tile[][] clonedTiles = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            clonedTiles[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }
        return clonedTiles;
    }

    boolean checkVertical(Word w){
        if(w.row + length - 1 > 15)
            return false;
        boolean isOnLetter = false;
        for (int i = w.row; i< w.row+length- 1; i++){
            if(instance.tiles[i][w.col] != null) {
                isOnLetter = true;
                if(w.tiles[i].letter != '_')
                    return false;
            }
        }
        if(!isOnLetter)
            return false;
        return  true;
    }

    boolean checkHorizontal(Word w){
        if(w.col + length - 1 > 15)
            return false;
        boolean isOnLetter = false;
        for (int i = w.col; i< w.col+length- 1; i++){
            if(instance.tiles[w.row][i] != null) {
                isOnLetter = true;
                if(w.tiles[i].letter != '_')
                    return false;
            }
        }
        if(!isOnLetter)
            return false;
        return true;
    }

    public boolean containesMiddle(Word w){
        int length = w.tiles.length;
        if(w.vertical){
            if(w.row<=7 && w.row + length - 1 >= 7)
                return true;
        }
        else{
            if(w.col<=7 && w.col + length - 1 >= 7)
                return true;
        }
        return false;
    }
    public boolean boardLegal(Word w){
        int length = w.tiles.length;
        if(w.row < 0 || w.row > 15 || w.col < 0 || w.col > 15)
            return false;
        if((!containesMiddle(w) && instance.tiles[7][7] == null))
            return false;
        if(w.vertical)
            return checkVertical(w);
        return  checkHorizontal(w);
    }
    boolean dictionaryLegal(Word w){
        return true; //temporary
    }

}
