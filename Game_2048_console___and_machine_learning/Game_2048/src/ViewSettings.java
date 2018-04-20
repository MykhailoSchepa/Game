import Game.Move;

public class ViewSettings {
    public static void printSettings(int score, int step, int scoreLastStep){

        printScore(score, step, scoreLastStep);

        System.out.format("\n           %5s : %d \n" +
                "%5s : %d                %5s : %d\n" +
                "            %5s : %d\n",
                Move.UP, Move.UP.getMove(),
                Move.LEFT, Move.LEFT.getMove(),
                Move.RIGHT, Move.RIGHT.getMove(),
                Move.DOWN, Move.DOWN.getMove());
    }

    public static void printNextMove(){
        System.out.format("\n\nPlease write code move:  ");
    }

    public static void printScore(int score, int step, int scoreLastStep){
        System.out.format("score: %6d                     step: %4d\n" +
                "score last step: %4d\n\n", score, step, scoreLastStep);
    }
}
