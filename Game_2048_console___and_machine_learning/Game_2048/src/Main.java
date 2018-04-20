import Game.Game;
import Game.Move;
import Game.Tiles.Tile;
import Game.TotalScore.Score;
import Game.neurons_Network.Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        // гра коли компютер рандомно вибирає ходи
//        for (int i = 0; i < 100; i++) {
//            System.out.println("Game №  " + i);
//            newGame();
//        }

        Network gameComputerNetwork = new Network(16, 4,
                16);

        int maxTilePoint = 0;
        int maxScore = 0;
        int maxStep = 0;
        int howOften = 0;

        int maxTilePoint1 = 0;
        int maxScore1 = 0;
        int maxStep1 = 0;
        int howOften1 = 0;

        int nGame = 1500;
        int nGameLearn = 1000;

        Score learningScore = new Score(10000);
        Score gameScore = new Score(10000);

        for (int i = 0; i < nGame; i++) {
            System.out.println("Game of computer № " + i);
            Game game = new Game();
            if (i < nGameLearn) {
                newGameComputerAndLearning(gameComputerNetwork, game,
                        (double) 1 - (double) i / ((double) nGameLearn * 1.1));

                if (maxTilePoint < game.getMaxTile()) {
                    maxTilePoint = game.getMaxTile();
                    howOften = 0;
                }

                if (maxTilePoint == game.getMaxTile())
                    howOften++;

                if (maxScore < game.getScore())
                    maxScore = game.getScore();

                if (maxStep < game.getStep())
                    maxStep = game.getStep();

                learningScore.put(game);

                System.out.println("\n\nСТАТИСТИКА ДЛЯ   " + i + "   ГРИ З НАВЧАННЯМ" +
                        "\nmaxTilePoint = " + maxTilePoint +
                        "\nmaxScore = " + maxScore +
                        "\nmaxStep = " + maxStep +
                        "\nhowOften = " + howOften);

            } else {
                newGameComputer(gameComputerNetwork, game);

                if (maxTilePoint1 < game.getMaxTile()) {
                    maxTilePoint1 = game.getMaxTile();
                    howOften1 = 0;
                }

                if (maxTilePoint1 == game.getMaxTile())
                    howOften1++;

                if (maxScore1 < game.getScore())
                    maxScore1 = game.getScore();

                if (maxStep1 < game.getStep())
                    maxStep1 = game.getStep();

                gameScore.put(game);

                System.out.println("\n\n" +
                        "СТАТИСТИКА ДЛЯ   " + i + "   ІГОР БЕЗ НАВЧАННЯ" +
                        "\nmaxTilePoint = " + maxTilePoint1 +
                        "\nmaxScore = " + maxScore1 +
                        "\nmaxStep = " + maxStep1 +
                        "\nhowOften = " + howOften1);
            }

            System.out.println("===============================================================" +
                    "\n                        навчання завершено" +
                    "\n===============================================================");
        }

        System.out.println("\n\n\nСТАТИСТИКА ДЛЯ   " + nGameLearn + "   ІГОР З НАВЧАННЯМ" +
                "\nmaxTilePoint = " + maxTilePoint +
                "\nmaxScore = " + maxScore +
                "\nmaxStep = " + maxStep +
                "\nhowOften = " + howOften);
        learningScore.print();

        System.out.println("\n\n" +
                "СТАТИСТИКА ДЛЯ   " + (nGame - nGameLearn) + "   ІГОР БЕЗ НАВЧАННЯ" +
                "\nmaxTilePoint = " + maxTilePoint1 +
                "\nmaxScore = " + maxScore1 +
                "\nmaxStep = " + maxStep1 +
                "\nhowOften = " + howOften1);
        gameScore.print();
    }

    private static void newGameComputerAndLearning(Network network, Game game, double learningSpeed) {
        gamegamegame:

        while (!game.isGameOver()) {
            boolean isMove;
                    // тут спробую реалізувати навчання
                    // при обробці всіх можливих ходів для ціїє дошки з глибиною в 4 ходи
                    // вибір найкращого варіанту по найвищому результату очок
                    // але не завжди очка є великим плюсом((( тому це лише перший варіант
            int bestMove = 0;
            int score = game.getScore();
            double maxPointCoef = 0;
            int freeTiles = game.getFreeTiles();
            double coefisient = 3 * Math.pow( Math.log(game.getMaxTile()) , 3);

            Game[][][][] g = new Game[4][4][4][4];
            Game gameTMP;

            Move m;

            this1:
            for (int i = 0; i < 4; i++) {
                g[i][0][0][0] = new Game(game);

                m = Move.code(i + 1);
                isMove = g[i][0][0][0].moveTo(m);

                if (!isMove || g[i][0][0][0].isGameOver()) {
                    g[i][0][0][0] = null;
                    continue this1;
                }

                while (!g[i][0][0][0].newTile()) {
                }
                gameTMP = new Game(g[i][0][0][0]);

                this2:
                for (int ii = 0; ii < 4; ii++) {
                    if (g[i][0][0][0] == null)
                        continue this2;

                    g[i][ii][0][0] = new Game(gameTMP);

                    m = Move.code(ii + 1);
                    isMove = g[i][ii][0][0].moveTo(m);
                    if (!isMove || g[i][ii][0][0].isGameOver()) {
                        g[i][ii][0][0] = null;
                        continue this2;
                    }
                    while (!g[i][ii][0][0].newTile()) {
                    }
                    gameTMP = new Game(g[i][ii][0][0]);

                    if (g[i][ii][0][0] != null &&
                            maxPointCoef <= score - g[i][ii][0][0].getScore() +
                                    coefisient * (g[i][ii][0][0].getFreeTiles() - freeTiles)) {
                        maxPointCoef = score - g[i][ii][0][0].getScore() +
                                coefisient * (g[i][ii][0][0].getFreeTiles() - freeTiles);
                        bestMove = i;
                    }

                    this3:
                    for (int iii = 0; iii < 4; iii++) {
                        if (g[i][ii][0][0] == null)
                            continue this3;

                        g[i][ii][iii][0] = new Game(gameTMP);

                        m = Move.code(iii + 1);
                        isMove = g[i][ii][iii][0].moveTo(m);

                        if (!isMove || g[i][ii][iii][0].isGameOver()) {
                            g[i][ii][iii][0] = null;
                            continue this3;
                        }

                        while (!g[i][ii][iii][0].newTile()) {
                        }
                        gameTMP = new Game(g[i][ii][iii][0]);

                        if (g[i][ii][iii][0] != null &&
                                maxPointCoef <= score -  g[i][ii][iii][0].getScore() +
                                        coefisient * (g[i][ii][iii][0].getFreeTiles() - freeTiles)) {
                            maxPointCoef = score - g[i][ii][iii][0].getScore() +
                                    coefisient * (g[i][ii][iii][0].getFreeTiles() - freeTiles);
                            bestMove = i;
                        }

                        this4:
                        for (int iiii = 0; iiii < 4; iiii++) {
                            if (g[i][ii][iii][0] == null)
                                continue this4;

                            g[i][ii][iii][iiii] = new Game(gameTMP);
                            m = Move.code(iiii + 1);
                            isMove = g[i][ii][iii][iiii].moveTo(m);

                            if (!isMove || g[i][ii][iii][iiii].isGameOver())
                                g[i][ii][iii][iiii] = null;

                            if (g[i][ii][iii][iiii] != null &&
                                    maxPointCoef <= score - g[i][ii][iii][iiii].getScore() +
                                            coefisient * (g[i][ii][iii][iiii].getFreeTiles() - freeTiles)) {
                                maxPointCoef = score - g[i][ii][iii][iiii].getScore() +
                                        coefisient * (g[i][ii][iii][iiii].getFreeTiles() - freeTiles);
                                bestMove = i;
                            }
                        }
                    }
                }
            }


            //////////////////////////////////////////////////////////////////////////////////////////////
            // тут передаєм дошку нейронній мережі

            // кілька кроків навчання на даному прикладі
            int stepLearning = 5;
            do {
                network.setEnters(encodingInputs(game.getField()));
                network.setOuter();
                network.learn(learningSpeed, moveIsBest(bestMove));
            }while(stepLearning-- < 0);

            Move move;

            // виконуємо кращий хід
            move = Move.code(bestMove + 1);

            isMove = game.moveTo(move);
            if (!isMove) {

                // випадок коли при прогнозуванні в 4 ходи є якась помилка
                do {
                    move = Move.nextMove(move);
                } while (!game.moveTo(move));

            }

            int attempt = 0;

            while (!game.newTile()) {
            }
        }

                // рисуємо рахунок в кінці
        ViewSettings.printScore(game.getScore(),
                game.getStep(),
                game.getScoreLastStep());
    }


    public static void newGameComputer(Network network, Game game) {
//        game.print();
        while (!game.isGameOver()) {

            network.setEnters(encodingInputs(game.getField()));

            Move move;
            move = Move.code(codeMoveTo(network.setOuter()));

            if (!game.moveTo(move)) {
                do {
                    move = Move.nextMove(move);
                } while (!game.moveTo(move));
            }

            while (!game.newTile()) {
            }

//            game.print();

        }
        ViewSettings.printScore(game.getScore(),
                game.getStep(),
                game.getScoreLastStep());

    }


    private static double[] moveIsImpossible(double[] networkOut) {
        int impossibleMove = codeMoveTo(networkOut) - 1;
        networkOut[impossibleMove] = 0;

        for (int i = 0; i < networkOut.length; i++) {
            networkOut[i] = (i == impossibleMove) ? 0 : networkOut[i] + 0.1;
            networkOut[i] = (networkOut[i] > 1) ? 1 : networkOut[i];
        }
        return networkOut;
    }


    private static double[] moveIsGood(double[] networkOut, int deltaScore) {
        int goodMove = codeMoveTo(networkOut) - 1;
        networkOut[goodMove] += 0.1 * Math.log(deltaScore);

        if (networkOut[goodMove] > 1)
            networkOut[goodMove] = 1;

        return networkOut;
    }

    private static double[] moveIsBest(int best) {
        double[] networkOut = new double[4];
        for (int i = 0; i < 4; i++) {
            networkOut[i] = (i == best - 1) ? 1 : 0;
        }

        return networkOut;
    }

    private static double[] moveIsBad(double[] networkOut) {
        int badMove = codeMoveTo(networkOut) - 1;

        for (int i = 0; i < networkOut.length; i++) {
            networkOut[i] = (badMove == i) ? networkOut[i] - 0.05 : networkOut[i] + 0.03;
            networkOut[i] = (networkOut[i] < 0) ? 0 : networkOut[i];
            networkOut[i] = (networkOut[i] > 1) ? 1 : networkOut[i];
        }
        return networkOut;
    }


    private static void newGame() {
        Game game = new Game();
//        ViewSettings.printSettings(game.getScore(),
//                game.getStep(),
//                game.getScoreLastStep());
//        game.print();

        while (!game.isGameOver()) {
            Move move;
            do {
                do {
//                    ViewSettings.printNextMove();
                    move = Move.code(playStepRandom());
                } while (move == null);
            } while (!game.moveTo(move));


            while (!game.newTile()) {
            }
//            ViewSettings.printSettings(game.getScore(),
//                    game.getStep(),
//                    game.getScoreLastStep());
//            game.print();
        }

//        System.out.println("Y O U     L O S");
        ViewSettings.printScore(game.getScore(),
                game.getStep(),
                game.getScoreLastStep());
    }


    private static int playStep() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int step;

        try {
            step = Integer.valueOf(br.readLine());
        } catch (Exception e) {
//            e.printStackTrace();
            step = 0;
        }

        if (step > 4 && step < 0)
            step = 0;

        return step;
    }

    private static int playStepRandom() {
        Random r = new Random();
        return r.nextInt(5);


    }

    private static int codeMoveTo(double[] x) {
        double max = Double.MIN_VALUE;
        int retCode = 1;
        for (int i = 0; i < x.length; i++) {
            if (max < x[i]) {
                max = x[i];
                retCode = i;
            }
        }
        return retCode + 1;
    }


    private static double[] encodingInputs(int[][] x) {
        double[] ret = new double[x.length * x[0].length];

        int max = Integer.MIN_VALUE;
        for (int[] a : x) {
            for (int b : a) {
                if (b > max)
                    max = b;
            }
        }

        int i = 0;

        for (int[] a : x) {
            for (int b : a) {
                ret[i] = (double) b / (double) max;
                i++;
            }
        }
        return ret;
    }

}
