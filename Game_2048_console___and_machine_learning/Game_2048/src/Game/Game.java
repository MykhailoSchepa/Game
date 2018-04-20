package Game;

import Game.Tiles.Tile;
import Game.View.ViewField;

import java.util.Random;

public class Game {

    private int score;
    private int step;
    private int scoreLastStep;
    private Tile[][] field;

    public Game() {
        score = 0;
        step = 0;
        scoreLastStep = 0;

        field = new Tile[4][4];

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = new Tile(0);
            }
        }

        Random r = new Random();
        field[r.nextInt(4)][r.nextInt(4)] = new Tile();
    }

    public Game(Game game) {
        score = game.getScore();
        step = game.getStep();
        scoreLastStep = game.getScoreLastStep();

        field = new Tile[4][4];

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = new Tile(game.getTileField()[i][j]);
            }
        }
    }

    public void print() {
        ViewField.view(field);
    }

    public boolean newTile() {
        Random r = new Random();
        int i = r.nextInt(4);
        int j = r.nextInt(4);

        if (field[i][j].getPoint() == 0)
            field[i][j] = new Tile();
        else
            return false;

        return true;
    }

    public boolean isGameOver() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (field[i][j].getPoint() == 0)
                    return false;
            }
        }

        return !isPossibleMove();
    }

    private boolean isPossibleMove() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j].getPoint() == field[i][j + 1].getPoint())
                    return true;
            }
        }

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                if (field[i][j].getPoint() == field[i + 1][j].getPoint())
                    return true;
            }
        }

        return false;
    }

    public boolean moveTo(Move move) {
        boolean isMove = false;
        scoreLastStep = 0;

        if (move == Move.LEFT) {
            for (int i = 0; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    if (field[i][j - 1].getPoint() == 0 && field[i][j].getPoint() != 0) {
                        field[i][j - 1] = field[i][j];
                        field[i][j] = new Tile(0);
                        j = (j == 1) ? 0 : j - 2;
                        isMove = true;
                        continue;
                    } else if (field[i][j - 1].getPoint() == field[i][j].getPoint()
                            && field[i][j].getPoint() != 0) {
                        field[i][j - 1] = new Tile(field[i][j - 1].getPoint());
                        field[i][j] = new Tile(0);
                        scoreLastStep += field[i][j - 1].getPoint();
                        isMove = true;
                    }
                }
            }
        }

        if (move == Move.RIGHT) {
            for (int i = 0; i < 4; i++) {
                for (int j = 2; j > -1; j--) {
                    if (field[i][j + 1].getPoint() == 0 && field[i][j].getPoint() != 0) {
                        field[i][j + 1] = field[i][j];
                        field[i][j] = new Tile(0);
                        j = (j == 2) ? 3 : j + 2;
                        isMove = true;
                        continue;
                    } else if (field[i][j + 1].getPoint() == field[i][j].getPoint() &&
                            field[i][j].getPoint() != 0) {
                        field[i][j + 1] = new Tile(field[i][j].getPoint());
                        field[i][j] = new Tile(0);
                        scoreLastStep += field[i][j + 1].getPoint();
                        isMove = true;
                    }
                }
            }
        }

        if (move == Move.DOWN) {
            for (int j = 0; j < 4; j++) {
                for (int i = 2; i > -1; i--) {
                    if (field[i + 1][j].getPoint() == 0 && field[i][j].getPoint() != 0) {
                        field[i + 1][j] = field[i][j];
                        field[i][j] = new Tile(0);
                        i = (i == 2) ? 3 : i + 2;
                        isMove = true;
                        continue;
                    } else if (field[i + 1][j].getPoint() == field[i][j].getPoint()
                            && field[i][j].getPoint() != 0) {
                        field[i + 1][j] = new Tile(field[i][j].getPoint());
                        field[i][j] = new Tile(0);
                        scoreLastStep += field[i + 1][j].getPoint();
                        isMove = true;
                    }
                }
            }
        }

        if (move == Move.UP) {
            for (int j = 0; j < 4; j++) {
                for (int i = 1; i < 4; i++) {
                    if (field[i - 1][j].getPoint() == 0 && field[i][j].getPoint() != 0) {
                        field[i - 1][j] = field[i][j];
                        field[i][j] = new Tile(0);
                        i = (i == 1) ? 0 : i - 2;
                        isMove = true;
                        continue;
                    } else if (field[i - 1][j].getPoint() == field[i][j].getPoint()
                            && field[i][j].getPoint() != 0) {
                        field[i - 1][j] = new Tile(field[i - 1][j].getPoint());
                        field[i][j] = new Tile(0);
                        scoreLastStep += field[i - 1][j].getPoint();
                        isMove = true;
                    }
                }
            }
        }

        score += scoreLastStep;

        if (isMove)
            step++;

        return isMove;
    }

    public int getScore() {
        return score;
    }

    public int getStep() {
        return step;
    }

    public int getScoreLastStep() {
        return scoreLastStep;
    }

    public int[][] getField() {
        int[][] f = new int[field.length][field[0].length];

        for (int j = 0; j < f.length; j++) {
            for (int i = 0; i < f[0].length; i++) {
                f[j][i] = field[j][i].getPoint();
            }
        }
        return f;
    }

    public Tile[][] getTileField(){
        return field;
    }

    public int getMaxTile() {
        int max = 0;
        for (int j = 0; j < field.length; j++) {
            for (int i = 0; i < field[0].length; i++) {
                if (max < field[j][i].getPoint())
                    max = field[j][i].getPoint();
            }
        }

        return max;
    }


    private static int maxTile(Game game) {
        int max = 0;
        int[][] field = game.getField();
        for (int j = 0; j < field.length; j++) {
            for (int i = 0; i < field[0].length; i++) {
                if (max < field[j][i])
                    max = field[j][i];
            }
        }

        return max;
    }

    public int getFreeTiles(){
        int ret = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].getPoint() == 0)
                    ret++;
            }
        }

        return ret;
    }

}
