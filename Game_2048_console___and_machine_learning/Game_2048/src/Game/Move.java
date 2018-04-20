package Game;

public enum Move {
    UP(4),
    LEFT(1),
    DOWN(2),
    RIGHT(3);

    private int move;

    Move(int move) {
        this.move = move;
    }

    public int getMove() {
        return move;
    }

    public static Move code(int move) {
        for (Move m : Move.values()) {
            if (move == m.move)
                return m;
        }

        return null;
    }

    public static Move nextMove(Move m){

        int m1 =  m.getMove();

        m1++;
        if (m1 > 4)
            m1 = 1;

        return code(m1);
    }
}
