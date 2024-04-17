package test;
import java.util.ArrayList;
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
        int length = w.tiles.length;
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
        return isOnLetter;
    }

    boolean checkHorizontal(Word w){
        int length = w.tiles.length;
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
        return isOnLetter;
    }

    public boolean containsMiddle(Word w){
        int length = w.tiles.length;
        if(w.vertical){
            return w.row <= 7 && w.row + length - 1 >= 7;
        }
        else{
            return w.col <= 7 && w.col + length - 1 >= 7;
        }
    }
    public boolean boardLegal(Word w){
        if(w.row < 0 || w.row > 15 || w.col < 0 || w.col > 15)
            return false;
        if((!containsMiddle(w) && instance.tiles[7][7] == null))
            return false;
        if(w.vertical)
            return checkVertical(w);
        return  checkHorizontal(w);
    }
    boolean dictionaryLegal(Word w){
        return true; //temporary
    }
    private void addFirstWordToList(ArrayList<Word> list, Tile[] tiles, int row, int col, boolean vertical) {
        Word temp = new Word(tiles, row, col, vertical);
        for (int i = 0; i < tiles.length; i++) {
            if (vertical) {
                if (temp.tiles[i].letter == '_')
                    temp.tiles[i] = instance.tiles[row + i][col];
            } else {
                if (temp.tiles[i].letter == '_')
                    temp.tiles[i] = instance.tiles[row][col + i];
            }
        }
        list.add(temp);
    }

    private int findBeginningOfWord(int row, int col, boolean vertical){
        int temp;
       if(vertical){
           temp = row;
           while(instance.tiles[temp-1][col] != null && temp > 0)
               temp--;
           return temp;
       }
       temp = col;
       while(instance.tiles[row][temp-1]!=null && temp > 0)
           temp--;
       return temp;
    }
    private int findEndOfWord(int row, int col, boolean vertical){
        int temp;
        if(vertical){
            temp = row;
            while(instance.tiles[temp+1][col] != null && temp < 15)
                temp++;
            return temp;
        }
        temp = col;
        while(instance.tiles[row][temp+1]!=null&& temp < 15)
            temp++;
        return temp;
    }

    private void findWord(Tile[] tempTiles,int beg, int size,Word w){
        tempTiles = new Tile[size];
        if(!w.vertical){
            for (int i = 0; i< size; i++){
                if(instance.tiles[beg+i][w.col] == null)
                    tempTiles[i] = w.tiles[w.col-beg]; // insert the common tile with the original word
                else{
                    tempTiles[i] = instance.tiles[beg+i][w.col];
                }
            }
        }
        else{
            for (int i = 0; i< size; i++){
                if(instance.tiles[w.row][beg+i] == null)
                    tempTiles[i] = w.tiles[w.row-beg]; // insert the common tile with the original word
                else{
                    tempTiles[i] = instance.tiles[w.row][beg+i];
                }
            }
        }
    }
    public ArrayList<Word> getWords(Word w) {
        ArrayList<Word> a = new ArrayList<>();
        addFirstWordToList(a, w.tiles, w.row, w.col, w.vertical);
        for (int i = 0; i< tiles.length; i++){
            int beg = findBeginningOfWord(w.row,w.col,!w.vertical);
            int end = findEndOfWord(w.row,w.col,!w.vertical);
            int size = end - beg + 1;
            Tile[] tempTiles = null;
            findWord(tempTiles,beg,size,w);
            if(w.vertical)
                a.add(new Word(tempTiles,w.row,beg,!w.vertical));
            else
                a.add(new Word(tempTiles,beg,w.col,!w.vertical));
        }

        return a;
    }

    private int calculateWordPositionScore(Word w) {
        int wordPositionScore = 0;
        for (Tile tile : w.tiles) {
            if (tile != null) {
                int letterValue = tile.score;
                wordPositionScore += letterValue;
                int bonus = bonuses[w.row][w.col];
                if (bonus == 2) { // Double letter
                    wordPositionScore += letterValue;
                } else if (bonus == 3) { // Triple letter
                    wordPositionScore += 2 * letterValue;
                }
            }
        }
        return wordPositionScore;
    }

    private int calculateWordMultiplierForVertical(Word w) {
        int wordMultiplier = 1;
        int col = w.col;
        for (Tile tile : w.tiles) {
            if (tile != null) {
                int bonus = bonuses[w.row][col];
                if (bonus == 4) { // Double word
                    wordMultiplier *= 2;
                } else if (bonus == 5) { // Triple word
                    wordMultiplier *= 3;
                }
            }
            col++;
        }
        return wordMultiplier;
    }

    private int calculateWordMultiplierForHorizontal(Word w) {
        int wordMultiplier = 1;
        int row = w.row;
        for (Tile tile : w.tiles) {
            if (tile != null) {
                int bonus = bonuses[row][w.col];
                if (bonus == 4) { // Double word
                    wordMultiplier *= 2;
                } else if (bonus == 5) { // Triple word
                    wordMultiplier *= 3;
                }
            }
            row++;
        }
        return wordMultiplier;
    }

    public int getScore(Word w) {
        int score = 0;
        int wordMultiplier = 1;
        int wordPositionScore = calculateWordPositionScore(w);

        if (w.vertical) {
            wordMultiplier = calculateWordMultiplierForVertical(w);
        } else {
            wordMultiplier = calculateWordMultiplierForHorizontal(w);
        }

        score += wordPositionScore * wordMultiplier;
        return score;
    }

}
