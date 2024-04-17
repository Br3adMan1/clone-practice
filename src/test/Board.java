package test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Board {
    private int[][] bonuses;
    private static Board instance;
    Tile[][] tiles;
    HashSet<String> foundWords;
    private Board() {
        foundWords = new HashSet<>();
        bonuses = new int[][] { // 0 - no bonus, 1 - star, 2 - triple letter, 3- double letter, 4 - double word, 5 - triple word
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
    private boolean hasNeighbor(Tile[][] tiles, int row, int col) {
        if (col > 0 && tiles[row][col - 1] != null) {
            return true;
        }
        if (col < 14 && tiles[row][col + 1] != null) {
            return true;
        }
        if (row > 0 && tiles[row - 1][col] != null) {
            return true;
        }
        if (row < 14 && tiles[row + 1][col] != null) {
            return true;
        }
        return false;
    }
    boolean checkVertical(Word w){
        int length = w.tiles.length;
        if(w.row + length - 1 > 15)
            return false;
        if(w.row+length <= 15 && containsMiddle(w) && instance.tiles[7][7] == null)
            return true;
        boolean isOnLetter = false;
        for (int i = w.row; i< w.row+length- 1; i++){
            if(instance.tiles[i][w.col] != null) {
                isOnLetter = true;
                if(w.tiles[i-w.row].letter != '_')
                    return false;
            }
            if(hasNeighbor(instance.tiles,i,w.col))
                return true;
        }
        return isOnLetter;
    }

    boolean checkHorizontal(Word w){
        int length = w.tiles.length;
        if(w.col + length - 1 > 15)
            return false;
        if(w.col+length <= 15 && containsMiddle(w) && instance.tiles[7][7] == null)
            return true;
        boolean isOnLetter = false;
        for (int i = w.col; i< w.col+length- 1; i++){
            if(instance.tiles[w.row][i] != null) {
                isOnLetter = true;
                if(w.tiles[i-w.col].letter != '_')
                    return false;
            }
            if(hasNeighbor(instance.tiles,w.row,i))
                return true;
        }
        return isOnLetter;
    }

    public boolean containsMiddle(Word w){
        int length = w.tiles.length;
        if(w.vertical){
            return w.col==7 && w.row <= 7 && w.row + length - 1 >= 7;
        }
        else{
            return w.row == 7 && w.col <= 7 && w.col + length - 1 >= 7;
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
        StringBuilder wordStringBuilder = new StringBuilder();
        for (int i = 0; i < tiles.length; i++) {
            if (vertical) {
                if (temp.tiles[i].letter == '_')
                    temp.tiles[i] = instance.tiles[row + i][col];
                wordStringBuilder.append(temp.tiles[i].letter);
            } else {
                if (temp.tiles[i].letter == '_')
                    temp.tiles[i] = instance.tiles[row][col + i];
                wordStringBuilder.append(temp.tiles[i].letter);
            }
        }
        list.add(temp);
        foundWords.add(wordStringBuilder.toString());
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

    private Tile[] findWord(int beg, int size, Word w, int count) {
        Tile[] tempTiles = new Tile[size];
        if(size==1)
            return null;

        if (!w.vertical) {
            for (int i = 0; i < size; i++) {
                if (instance.tiles[beg + i][w.col+count] == null)
                    tempTiles[i] = w.tiles[count]; // insert the common tile with the original word
                else {
                    tempTiles[i] = instance.tiles[beg + i][w.col + count];
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (instance.tiles[w.row + count][beg + i] == null)
                    tempTiles[i] = w.tiles[count]; // insert the common tile with the original word
                else {
                    tempTiles[i] = instance.tiles[w.row + count][beg + i];
                }
            }
        }

        return tempTiles;
    }
    private String buildWordString(Tile[] tiles) {
        StringBuilder wordBuilder = new StringBuilder();
        for (Tile tile : tiles) {
            if (tile != null) {
                wordBuilder.append(tile.letter);
            }
        }
        return wordBuilder.toString();
    }


    public ArrayList<Word> getWords(Word w) {
        ArrayList<Word> a = new ArrayList<>();
        addFirstWordToList(a, w.tiles, w.row, w.col, w.vertical);

        int count = 0;
        for (int i = 0; i < w.tiles.length; i++) {
            int beg, end;
            if (w.vertical) {
                beg = findBeginningOfWord(w.row + i, w.col, !w.vertical);
                end = findEndOfWord(w.row + i, w.col, !w.vertical);
            }
            else {
                beg = findBeginningOfWord(w.row, w.col + i, !w.vertical);
                end = findEndOfWord(w.row, w.col + i, !w.vertical);
            }
            int size = end - beg + 1;
            Tile[] tempTiles = findWord(beg, size, w, count);

            if (tempTiles != null) {
                String newWordString = buildWordString(tempTiles);
                Word newWord = new Word(tempTiles,
                        w.vertical ? (w.row + count) : beg,
                        w.vertical ? beg : (w.col + count),
                        !w.vertical);
                if (!foundWords.contains(newWordString)) {
                    a.add(newWord);
                    foundWords.add(newWordString);
                }
            }

            count++;
        }

        return a;
    }
    private int calculateWordPositionScore(Word w) {
        int wordPositionScore = 0;
        int row = w.row;
        int col = w.col;

        for (Tile tile : w.tiles) {
            if (tile != null) {
                int letterValue = tile.score;
                wordPositionScore += letterValue;
                int bonus;
                if (w.vertical) {
                    bonus = bonuses[row][col];
                    row++;
                } else {
                    bonus = bonuses[row][col];
                    col++;
                }
                if (bonus == 3) { // Double letter
                    wordPositionScore += letterValue;
                } else if (bonus == 2) { // Triple letter
                    wordPositionScore += 2 * letterValue;
                }
            }
            else {
                if (w.vertical) {
                    row++;
                }
                else {
                    col++;
                }
            }
        }
        return wordPositionScore;
    }

    private int calculateWordMultiplierForVertical(Word w) {
        int wordMultiplier = 1;
        int row = w.row;
        for (Tile tile : w.tiles) {
            if (tile != null) {
                int bonus = bonuses[row][w.col];
                if (bonus == 4) { // Double word
                    wordMultiplier *= 2;
                }
                if(bonus == 1){
                    wordMultiplier *= 2;
                    bonuses[7][7] = 0;
                }
                else if (bonus == 5) { // Triple word
                    wordMultiplier *= 3;
                }
            }
            row++;
        }
        return wordMultiplier;
    }

    private int calculateWordMultiplierForHorizontal(Word w) {
        int wordMultiplier = 1;
        int col = w.col;
        for (Tile tile : w.tiles) {
            if (tile != null) {
                int bonus = bonuses[w.row][col];
                if (bonus == 4) { // Double word
                    wordMultiplier *= 2;
                }
                if(bonus == 1){
                    wordMultiplier *= 2;
                    bonuses[7][7] = 0;
                }
                else if (bonus == 5) { // Triple word
                    wordMultiplier *= 3;
                }
            }
            col++;
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

    public int tryPlaceWord(Word w) {
        int totalScore = 0;

        if (boardLegal(w)) {
            ArrayList<Word> newWords = getWords(w);
            boolean allWordsLegal = true;
            for (Word newWord : newWords) {
                if (!dictionaryLegal(newWord)) {
                    allWordsLegal = false;
                    break;
                }
            }

            if (allWordsLegal) {
                int row = w.row;
                int col = w.col;
                for (Tile tile : w.tiles) {
                    if(tile.letter!='_')
                        instance.tiles[row][col] = tile;
                    if(w.vertical)
                        row++;
                    else
                        col++;

                }

                for (Word newWord : newWords) {
                    totalScore += getScore(newWord);
                }
            }
        }
        return totalScore;
    }

}
