package Game.View;

import Game.Tiles.Tile;

public class ViewField {
    public static void view(Tile[][] field) {
        System.out.println("================================"
                + (char)185);
        for (int i = 0; i < 4; i++) {
            System.out.println("|       |       |       |       |");
            System.out.print("|");
            for (int j = 0; j < 4; j++) {
                if (field[i][j].getPoint() == 0)
                    System.out.print("       |");
                else
                    System.out.format(" %4d  |", field[i][j].getPoint());
            }
            System.out.println();
            System.out.println("|       |       |       |       |");
            System.out.println("=================================");
        }
    }
}
