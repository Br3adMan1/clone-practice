package test;
import java.util.Random;

public class Tile {
    final public char letter;
    final public int score;

    private Tile(char letter , int score){
        this.letter = letter;
        this.score = score;
    }
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Tile tile = (Tile) object;
        return letter == tile.letter && score == tile.score;
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), letter, score);
    }

    public static class Bag {
        private static  Bag instance;
        int[] amounts;
        Tile[] tiles;
        Random random;
        private static final int[] INITIAL_AMOUNTS = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        private Bag(){
            amounts = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            tiles = new Tile[]{
                    new Tile('A', 1), new Tile('B', 3), new Tile('C', 3), new Tile('D', 2), new Tile('E', 1),
                    new Tile('F', 4), new Tile('G', 2), new Tile('H', 4), new Tile('I', 1), new Tile('J', 8),
                    new Tile('K', 5), new Tile('L', 1), new Tile('M', 3), new Tile('N', 1), new Tile('O', 1),
                    new Tile('P', 3), new Tile('Q', 10), new Tile('R', 1), new Tile('S', 1), new Tile('T', 1),
                    new Tile('U', 1), new Tile('V', 4), new Tile('W', 4), new Tile('X', 8), new Tile('Y', 4),
                    new Tile('Z', 10)
            };
            random = new Random();
        }
        public static Bag getBag() {
            if (instance == null)
                instance = new Bag();
            return instance;
        }
        public Tile getRand() {
            if(instance == null)
                return null;
            int index;
            do {
                index = random.nextInt(tiles.length);
            } while (amounts[index] == 0);
            amounts[index]--;
            return tiles[index];
        }

        public Tile getTile(char letter) {
            if (letter == '_') {
                return new Tile('_', 0);
            }
            if (letter < 'A' || letter > 'Z') {
                return null;
            }
            int index = letter - 'A';
            if (amounts[index] == 0) {
                return null;
            }
            amounts[index]--;
            return tiles[index];
        }

        public void put(Tile t){
            int index = t.letter - 'A';
            if(amounts[index] != INITIAL_AMOUNTS[index] && t.letter>='A' && t.letter<='Z')
                amounts[index]++;
        }
        public int size(){
            int count = 0 ;
            for (int i = 0; i<this.amounts.length; i++){
                count += this.amounts[i];
            }
            return count;
        }

        public int[] getQuantities() {
            return amounts.clone();
        }
    }

}
