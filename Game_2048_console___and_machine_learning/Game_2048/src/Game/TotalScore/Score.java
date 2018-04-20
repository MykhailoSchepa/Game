package Game.TotalScore;

import Game.Game;

import java.util.HashMap;
import java.util.Map;

public class Score {
    private Map<String, Integer> listScore;

    int maxPoint;

    public Score(int maxPointTile){
        maxPoint = maxPointTile;
        listScore = new HashMap<>();

        int point = 2;
        while (point <= maxPointTile){
            listScore.put(new String(String.valueOf(point)), 0);
            point *= 2;
        }
    }

    public void put(Game game){
        listScore.replace(String.valueOf(game.getMaxTile()),
                (Integer) listScore.get(String.valueOf(game.getMaxTile()))+1);
    }

    public void print(){

        for (int i = 2; i <= maxPoint; i *= 2){
            System.out.format("  %5d =====>  ", i);
            System.out.println(listScore.get(String.valueOf(i)));
        }

    }
}
