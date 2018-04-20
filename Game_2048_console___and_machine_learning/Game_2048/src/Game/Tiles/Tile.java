package Game.Tiles;

import java.util.Random;

public class Tile {

    private int point;

    public Tile() {
        Random random = new Random();
        point = 2;
    }

    public Tile(Tile t){
        point = t.getPoint();
    }

    public Tile(int prewPoint){
        point = 2 * prewPoint;
    }

    public int getPoint() {
        return point;
    }
}
