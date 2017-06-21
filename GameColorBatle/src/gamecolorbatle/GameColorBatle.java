/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamecolorbatle;

// бібліотека графічного інтерфейсу
import java.awt.*;
// бібліотека подій зпущених з компонента awt
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
//??????????????????????????????
import java.util.*;
// бібліотека для створення графічного інтерфейсу
import javax.swing.*;




/**
 *
 * @author Mykhailo
 */
public class GameColorBatle extends JFrame{
    
    //КОНСТАНТИ
            //НАЗВА ВІКНА
    final String TITLE_OF_PROGRAM = "ШЕСТИКУТНИК";
            // координата вікна при запуску програми
    int START_LOCATION_X = 115;
    int START_LOCATION_Y = 5;
            // ширина і висота вікна ???????????????????????????????????????????????????????
    int FIELD_WIDTH = 1040;
    int FIELD_HEIGHT = 600;
            // висота і ширина шестикутних комірок
    int PANTRY_HEIGHT = 45; // висота
    int PANTRY_WIDTH = (int)(Math.sqrt(3)/2 * PANTRY_HEIGHT); // ширина
            // кількість комірок для гри
    int W = 7;//від WIDTH 22 ширина ігрового поля в кількості комірок
    int H = 12;//від HEIGHT 10 висота ігрового поля в кількості комірок
            // масив кольорів
    int [] COLORS = {
        0x228B22, // ForestGreen
//        0x00FF00, // Lime
        0x7FFF00, // Chartreuse1
//        0x90EE90, // LightGreen
        0x00008B, // DarkBlue
        0x00FFFF, // Aqua
//        0xEE7AE9, // Orchid2
        0xEE799F, // PaleVioletRed2
//        0x8B0000, // DarkRed
        0xFF4500, // OrangeRed1
//        0xB8860B, // DarkGoldenRod
//        0xEE7942, // Sienna2
//        0xEE9A00, // Orange2*/
//        0xEEC591  // Burlywood2
    };
    // кількість гравців
    int PLAYERS = 4;
    int [] colorPlayers;
            // інформація про гру (змінюється динамічно
    int [] areaPlayers;
    final Font font = new Font("гра", Font.BOLD, 21);
    // Глобальні змінні
    // гравець, хід якого в цей момент
    public int playerNow;
    
    // new - ми створюєм обєкт на основі класа
    //ОБЄКТИ
        // для генерації випадкових чисел
    Random random = new Random();
        // для відображення поля
    Canvas canvas = new Canvas();
        // табло (параметри гри)
    JLabel board = new JLabel();
        // масив комірок (ігрове поле)
    Hexagons hexagons;
        // панель розміщена внизу вікна
    JPanel PanelSouth = new JPanel();
        // панель з кнопками
    JPanel PanelButtons;
    //JButton btn = new HexagonButtom("це я кнопка");
    //JButton btn1 = new HexagonButtom("i я кнопка");
    JButton [] btnAr;   // оголошення масиву кнопок класу JButton
        // панель меню
    JMenuBar menuBar = new JMenuBar();
    
    Options opt = new Options();
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new GameColorBatle();
        
    }
    
    // конструктор
        /*
        спеціальний метод що є в класі і він спрацьовує в той момент 
        коли клас створюється
        */
    GameColorBatle() {
        // встановлення імені заголовка
        setTitle(TITLE_OF_PROGRAM);
        // умова закінчення. якщо натиснути 
        // на кнопочку з хрестикм то закриється вікно
        // і програма перестане працювати
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // перші два параметри це координати лівого верхнього 
        // кута вікна, наступні два - ширина і висота вікна
        setBounds(START_LOCATION_X, START_LOCATION_Y,
                FIELD_WIDTH+100, FIELD_HEIGHT+100);
        // розмір вікна ми не мінятимемо
        setResizable(false);
        
        // https://javaswing.wordpress.com/2010/02/20/jmenubar/
            // меню
        Font fontMenu = new Font("Verdana", Font.PLAIN, 11);
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(fontMenu);
        
        JMenuItem newGameItem = new JMenuItem("Нова гра");
        newGameItem.setFont(fontMenu);
        fileMenu.add(newGameItem);
        newGameItem.addActionListener(new ActionListener() {           
            public void actionPerformed(ActionEvent e) {;
                setEnabled(true);
                setVisible(true);
                repaint();
                newGame();
            }           
        });
        
        /*JMenu newMenu = new JMenu("New");
        newMenu.setFont(fontMenu);
        fileMenu.add(newMenu);
        fileMenu.addSeparator();*/
        
        JMenuItem settingItem = new JMenuItem("Налаштування");
        settingItem.setFont(fontMenu);
        fileMenu.add(settingItem);
        
        settingItem.addActionListener(new ActionListener() {           
            public void actionPerformed(ActionEvent e) { 
                setEnabled(false);
                new settingPlay();
            }           
        });
        
        fileMenu.addSeparator();
        
        JMenuItem exitItem = new JMenuItem("Вийти");
        exitItem.setFont(fontMenu);
        fileMenu.add(exitItem);
        
        exitItem.addActionListener(new ActionListener() {           
            public void actionPerformed(ActionEvent e) {
                System.exit(0);             
            }           
        });

            // додаємо меню в вікно
        menuBar.add(fileMenu);
        add(BorderLayout.NORTH, menuBar);
        
        setVisible(true);
    }
        
        
    void newGame() {
        if (btnAr != null) {
            PanelButtons.removeAll();
            PanelSouth.removeAll();
        }
        
        playerNow = 1;
        System.out.println(colorPlayers);
        colorPlayers = null;
        System.out.println(colorPlayers);
        colorPlayers = new int [PLAYERS];
        // інформація про гру (змінюється динамічно
        areaPlayers = null;
        areaPlayers = new int [PLAYERS];
        hexagons = null;
        hexagons = new Hexagons(W, H);
        // board -  робота з панеллю!!!
        // обєкт наслідуваний від JLable
        // на панелі виведено інформацію про ігровий процес
        // очки, життя ...
        // для отримання полігону точок що описують шестикутник з заданим центром
        class Hex{
            int height = (int)(PANTRY_HEIGHT*1);
            int width = (int)(PANTRY_WIDTH*1);
            Polygon p = new Polygon();
            Hex(int x, int y){
                p.addPoint(x, y - height/2);
                p.addPoint(x + width/2, y - height/4);
                p.addPoint(x + width/2, y + height/4);
                p.addPoint(x, y + height/2);
                p.addPoint(x - width/2, y + height/4);
                p.addPoint(x - width/2, y - height/4);
                p.addPoint(x, y - height/2);
            }
            Polygon GetPolygon(){return p;}
        }

        // масив кнопок кількість рівна довжині мисиву кольорів
        btnAr = new HexagonButtom[COLORS.length];
        for (int i = 0; i < COLORS.length; i++) {
            
            //btnAr[i] = new HexagonButtom("i", new Hex((PANTRY_WIDTH/2)*(i*2+1),PANTRY_HEIGHT/2).GetPolygon());
            btnAr[i] = new HexagonButtom("", new Hex(18,23).GetPolygon());
            (btnAr[i]).setBackground(new Color(COLORS[i]));

            (btnAr[i]).addMouseListener(new CustomListener());
            //btnAr.getBackground(Color.white);
            //(btnAr[i]).setActionCommand(String.format("this is %d button", i));
            btnAr[i].setEnabled(true);
            // команда(повідомлення), що передається при натисканні кнопки
            for (int j = 0; j < colorPlayers.length; j++) {
                if (COLORS[i] == colorPlayers[j]) {
                    System.out.println("неактивна кнопка    " + i);
                    (btnAr[i]).setActionCommand(String.format("%d", -1));
                    btnAr[i].setEnabled(false);
                    
                    break;
                } else {
                    (btnAr[i]).setActionCommand(String.format("%d", COLORS[i]));
                }
            }
            System.out.println(btnAr[i].isEnabled());
        }
        board.setFont(font);

        // виключає прозорість панелі
            // по замовчуванню панель прозора
        board.setOpaque(true);
        // колір фона
        board.setBackground(Color.black);
        // колір шрифта
        board.setForeground(Color.white);
        // вирівнювання по горизонталі
        board.setHorizontalAlignment(JLabel.CENTER);
        // додаємо обєкти в робоче поле (вікно)
        //PanelSouth = new JPanel();
        PanelSouth.setLayout(new BorderLayout());
        //PanelSouth.add(BorderLayout.NORTH, btnAr[0]);
        PanelButtons = new JPanel();
        for (int i = 0; i < COLORS.length; i++){
            PanelButtons.add(BorderLayout.NORTH, btnAr[i]);
        }
        PanelSouth.add(BorderLayout.NORTH, PanelButtons);
        PanelSouth.add(BorderLayout.SOUTH, board);
        add(BorderLayout.CENTER, canvas);
        add(BorderLayout.SOUTH, PanelSouth);
        // зробити видимим наше вікно
//        setVisible(true);
        for (int i = 0; i < PLAYERS; i++) {
            areaPlayers[i] = 1;
        }
 // відображення ігрового процесу
        //board.setText(String.format("Гравець 1 : %3d Гравець 2 : %3d", areaPlayers[0], areaPlayers[1]));
        board.setText("Початок гри!!!!");
        //board.setFont(font);
        //check(1500);
        for (int t = 3; t > 0; t--) {
            String gameScore = "<html>";
            gameScore += "<h2>ГРА :</h2>";
            gameScore += "<font face=’verdana’ size = 6 color = 'red'>";
            String s = String.format("%d", t);
            gameScore = gameScore + "<p align='center'>" + s + "</p>";
            gameScore += "</html>";
            board.setText(gameScore);
            canvas.repaint();
            //check(500);
        }
        
        //PanelButtons.repaint();
        //PanelSouth.repaint();
        repaint();
        // перемальовує картинку на екрані
        canvas.repaint();
        repaint();
        for (int step = 0; step < PLAYERS; step++)
            play(String.format("%d", colorPlayers[step]), true);
    }
    
    // перевантажений метод для реалізації необов'язкового аргумента
    void play(String message) {
        play(message, false);
    }
    
    
    void play(String message, boolean isStart) {
        int color = Integer.parseInt(message);
        if (color == -1) return;
        //System.out.println((btnAr[4].getBackground()));
        //System.out.println(new Color(COLORS[4]));
        //System.out.println((btnAr[4].getBackground()).getRGB()==(new Color(COLORS[4])).getRGB());
        int tempColor = colorPlayers[playerNow - 1];
        int x1 = (playerNow == 1 || playerNow == 4) ? 0 : W - 1;
        int y1 = (playerNow == 1 || playerNow == 3) ? 0 : H - 1;
        hexagons.setColor(x1, y1, color);
        colorPlayers[playerNow - 1] = color;
        
        int i = x1; // КОМІРКА ПО Х
        int j = y1; // КОМІРКА ПО У
        boolean endI = false;
        boolean endJ = false;
        boolean isCapture = false;

        // проходження по рядках
        while (!endJ) {
            // проходження по комірках в рядку
            while (!endI) {
                // якщо комірка не завойована і відповідає кольору гравця
                checkConquest:
                {if (hexagons.getPlayer(i, j) == 0 && 
                        hexagons.getColor(i, j) == color) {
                    // перевіримо чи вона сусідня комірці гравця
                    // комірки вище
                    if (j != 0) {
                        if (hexagons.getPlayer(i, j - 1) == playerNow) {
                            hexagons.setPlayer(i, j, playerNow);
                            System.out.println(areaPlayers[playerNow-1]);
                            areaPlayers[playerNow-1]++;
                            isCapture = true;
                            break checkConquest;
                        }
                        // якщо рядок парний
                        if (((j + 1) % 2 == 0) && (i + 1 != W)) {
                            if (hexagons.getPlayer(i + 1, j - 1) == playerNow) {
                                hexagons.setPlayer(i, j, playerNow);
                                System.out.println(areaPlayers[playerNow-1]);
                                areaPlayers[playerNow-1]++;
                                isCapture = true;
                                break checkConquest;
                            }
                        }
                        // якщо рядок непарний
                        if (((j + 1) % 2 == 1) && (i != 0)) {
                            if (hexagons.getPlayer(i - 1, j - 1) == playerNow) {
                                hexagons.setPlayer(i, j, playerNow);
                                System.out.println(areaPlayers[playerNow-1]);
                                areaPlayers[playerNow-1]++;
                                isCapture = true;
                                break checkConquest;
                            }
                        }
                    }
                    
                    // комірки в цьому ряді
                    if (i != 0) {
                        if (hexagons.getPlayer(i - 1, j) == playerNow) {
                            hexagons.setPlayer(i, j, playerNow);
                            System.out.println(areaPlayers[playerNow-1]);
                            areaPlayers[playerNow-1]++;
                            isCapture = true;
                            break checkConquest;
                        }
                    }
                    if (i + 1 != W) {
                        if (hexagons.getPlayer(i + 1, j) == playerNow) {
                            hexagons.setPlayer(i, j, playerNow);
                            System.out.println(areaPlayers[playerNow-1]);
                            System.out.println(areaPlayers[playerNow-1]);
                            areaPlayers[playerNow-1]++;
                            isCapture = true;
                            break checkConquest;
                        }
                    }
                    
                    // комірки внизу
                    if (j + 1 != H) {
                        if (hexagons.getPlayer(i, j + 1) == playerNow) {
                            hexagons.setPlayer(i, j, playerNow);
                            System.out.println(areaPlayers[playerNow-1]);
                            areaPlayers[playerNow-1]++;
                            isCapture = true;
                            break checkConquest;
                        }
                        // якщо рядок парний 
                        if (((j + 1) % 2 == 0) && (i + 1 != W)) {
                            if (hexagons.getPlayer(i + 1, j + 1) == playerNow) {
                                hexagons.setPlayer(i, j, playerNow);
                                System.out.println(areaPlayers[playerNow-1]);
                                areaPlayers[playerNow-1]++;
                                isCapture = true;
                                break checkConquest;
                            }
                        }
                        // якщо рядок непарний 
                        if (((j + 1) % 2 == 1) && (i != 0)) {
                            if (hexagons.getPlayer(i - 1, j + 1) == playerNow) {
                                hexagons.setPlayer(i, j, playerNow);
                                System.out.println(areaPlayers[playerNow-1]);
                                areaPlayers[playerNow-1]++;
                                isCapture = true;
                                break checkConquest;
                            }
                        }
                    }  
                }}// кінець блока checkConquest
                
                // перемальовуємо завойовані комірки
                if (hexagons.getPlayer(i, j) == playerNow) {
                    hexagons.setColor(i, j, color);
                }
                // перехід на наступну комірку в рядку
                i = (x1 == 0) ? i + 1: i - 1;
                // якщо рядок закінчився
                if (((x1 == 0) && (i == W)) || ((x1 == W - 1) && (i == -1))) {
                    i = x1;
                    endI = true;
                }
            }
            endI = false;
            // перехід на наступний рядок
            j = (y1 == 0) ? j + 1: j - 1;
            //якщо завойовано комірку
            if (isCapture) {
                j = (y1 == 0) ? j - 2: j + 2;
                if (((j < 0) && (y1 == 0)) ||
                        ((j > H - 1) && (y1 == H - 1))) 
                    j = y1;
                isCapture = false;
            }
            // якщо рядки закінчились вихід з циклу
            if (((y1 == 0) && (j == H)) || ((y1 == H - 1) && (j == -1))) {
                j = y1;
                endJ = true;
            }
        }
            // команда(повідомлення), що передається при натисканні кнопки
            // зміна активності кнопки
        activateButton: {
        for (int J = 0; J < btnAr.length; J++) {
            if (isStart) { 
//                System.out.println("перервати.................................................");
                break activateButton;
            }
            if (btnAr[J].getBackground().getRGB() == (new Color(color)).getRGB()) {
                System.out.println("повідомлення .................... false");
                btnAr[J].setActionCommand(String.format("%d", -1));
                btnAr[J].setEnabled(false);
            }
            if (btnAr[J].getBackground().getRGB() == (new Color(tempColor)).getRGB()) {
                System.out.println("повідомлення .................... true");
                btnAr[J].setEnabled(true);
                btnAr[J].setActionCommand(String.format("%d", COLORS[J]));
            }
        }}
        playerNow = playerNow < PLAYERS ? playerNow + 1 : 1;
        
        int summScore = 0;
        String gameScore = "<html>";
        gameScore += "<h2>Рахунок гри</h2>";
        gameScore += "<font face=’verdana’ size = 3>";
        for (int step = 0; step < PLAYERS; step++) {
            String s = String.format("Гравець %d : %3d", step + 1, areaPlayers[step]);
            gameScore = gameScore + "<p>" + s + "</p>";
            summScore += areaPlayers[step];
        }
        gameScore += "</html>";
        
        if (summScore == W*H) {
            gameScore = "<html>";
            gameScore += "<h2>КІНЕЦЬ   ГРИ</h2>";
            gameScore += "<p>переможець:</p>";
            gameScore += "<font face=’verdana’ size = 3>";
            String s = String.format("Гравець %d : %3d", 1, areaPlayers[0]);
            int playerVin = 0;
            for (int step = 1; step < PLAYERS; step++) {
                if (areaPlayers[step] > areaPlayers[playerVin]) {
                    playerVin = step;
                    s = String.format("Гравець %d : %3d", step+1, areaPlayers[step]);
                }
            }
            gameScore += "<p>";
            gameScore += s;
            gameScore += "</p>";
            gameScore += "</html>";
        }
        board.setText(gameScore);
        //board.setText(String.format("Гравець 1 : %d Гравець 2 : %d", areaPlayers[0], areaPlayers[1]));
        canvas.repaint();
        // для затримки гри але щось не працює так як треба
//        check(3000);
        
    }
    
        // метод при виклику віккна параметрів
    void setSettingPlay(boolean startNewGame) {
        setEnabled(true);
        //setBounds(100, START_LOCATION_Y,
        //        FIELD_WIDTH+100, opt.newH);
        setVisible(true);
        if (startNewGame) {
            opt.setParametresInGlobal();
            newGame();
        }
        
    }
    
        // метод дя прийняття параметрів
    void getSettingPlay() {
        //public int newH;
    }
    
    // метод для реалізації затримки в грі
    void check(int milisecond) {
        try {
            Thread.sleep(milisecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    class Options{
        int newSTART_LOCATION_X;
        int newSTART_LOCATION_Y;
        int newFIELD_WIDTH;
        int newFIELD_HEIGHT;
        int newPANTRY_HEIGHT;
        int newPANTRY_WIDTH;
        int newW;
        int newH;
        int newPLAYERS;
        
        Options() {
            newSTART_LOCATION_X = START_LOCATION_X;
            newSTART_LOCATION_Y = START_LOCATION_Y;
            newFIELD_WIDTH = FIELD_WIDTH; // ширина поля
            newFIELD_HEIGHT = FIELD_HEIGHT; // висота поля
            newPANTRY_HEIGHT = PANTRY_HEIGHT; // висота комірок
            newPANTRY_WIDTH = PANTRY_WIDTH; // ширина комірок
            newW = W;//від WIDTH ширина ігрового поля в кількості комірок
            newH = H;//від HEIGHT висота ігрового поля в кількості комірок
            newPLAYERS = PLAYERS;// кількість гравців
        }
        void setFieldHeight(int fieldH) {newPANTRY_HEIGHT = fieldH;}
        void setFieldWidth(int fieldW) {newPANTRY_WIDTH = fieldW;}
        void setW(int W) {newW = W;};
        void setH(int H) {newH = H;}
        void setPlayers(int players) {newPLAYERS = players;}
        void setPantryHeight(int newPANTRY_HEIGHT) {this.newPANTRY_HEIGHT = newPANTRY_HEIGHT;}
        void setPantryWidth(int newPANTRY_WIDTH) {this.newPANTRY_WIDTH = newPANTRY_WIDTH;}
        
        void setParametresInGlobal() {
            START_LOCATION_X = newSTART_LOCATION_X;
            START_LOCATION_Y = newSTART_LOCATION_Y;
            FIELD_WIDTH = newFIELD_WIDTH;
            FIELD_HEIGHT = newFIELD_HEIGHT;
            PANTRY_HEIGHT = newPANTRY_HEIGHT;
            PANTRY_WIDTH = newPANTRY_WIDTH;
            W = newW;
            H = newH;
            PLAYERS = newPLAYERS;
            
            if (btnAr!=null) {
                for (int i = 0; i < COLORS.length; i++){
                    //PanelButtons.add(BorderLayout.NORTH, btnAr[i]);
                    btnAr[i].setEnabled(true);
                    
                }
            }
        }
    }
    
    
    class Hexagon {
        // властивості обєкта
        // координати центру комірки цього обєкту
        private int x, y;
            // колір комірки
        private int color;
            // комірка якого гравця?
                // 0 - нічия
                // 1,2,3,4 - номер гравця
        private int player = 0;
            // (створюємо) оголошуємо полігон точок
        Polygon p = new Polygon();

        // конструктор при створенні ігрового поля
        Hexagon(int X, int Y) {
                // X, Y - координати центру шестикутника на canvas
            build(X, Y);
            color = COLORS[random.nextInt(COLORS.length)];
        }
        
        // перевантажений конструктор при створенні масиву кнопок
        Hexagon(int X, int Y, int color) {
                // X, Y - координати центру шестикутника на canvas
            build(X, Y);
            this.color = color;
        }
        
        void build(int X, int Y) {
                // X, Y - координати центру шестикутника на canvas
            x = X;
            y = Y;
            // переприсвоюємо константи для простоти запису
            int height = PANTRY_HEIGHT;
            int width = PANTRY_WIDTH;
            // записуємо координати точок в полігон
            p.addPoint(x, y - height/2);
            p.addPoint(x + width/2, y - height/4);
            p.addPoint(x + width/2, y + height/4);
            p.addPoint(x, y + height/2);
            p.addPoint(x - width/2, y + height/4);
            p.addPoint(x - width/2, y - height/4);
            p.addPoint(x, y - height/2);
        }
        
        void paint(Graphics g) {
            // замальовуємо ігуру в колір
            g.setColor(new Color(color));
            g.fillPolygon(p);
           
            // малюємо фігуру з полігону точок
            // якщо фігура не захоплена
            if (player == 0) {
                g.setColor(Color.black);
                g.drawPolygon(p);
            } else {
                g.setColor(new Color(color));
                g.drawPolygon(p);
            }
        }
        
        void setColor(int color) {
            this.color = color;
        }
        
        void setPlayer(int player) {
            this.player = player;
        }
        
        int getPlayer() {
            return player;
        }
        
        int getColor() {
            return color;
        }
    }
    
    class Hexagons {
        // оголошуємо двовимірний масив комірок
        private Hexagon [][] hexagons;
        // координати центру першої комірки
                // можне треба записати як формулу??
        private int x = 50, y = 50;
        // розмірність матриці (масиву) комірок
        private int W, H;
        
        // при реалізації масиву комірок
        Hexagons(int w ,int h){
            // перевірка чи можна реалізувати гру(чи достатньо кольорів для гравців)
            if (COLORS.length <= PLAYERS) {
                System.out.println("кількість гравців менша за кольори");
                System.exit(1);
            }
            W = w;      // кількість комірок по ширині
            H = h;      // кількість комірок по висоті
            hexagons = null;
            hexagons = new Hexagon[h][w];
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    hexagons[j][i] =
                            new Hexagon((x + (i * PANTRY_WIDTH) +
                                    ((j%2) * ((int)(PANTRY_WIDTH / 2d)))),
                                    y + (j * ((int)((3/4d) *PANTRY_HEIGHT))));
                }
            }
            // присвоєння комірці гравця номеру гравця
            // та перевірка чи не однакові кольори в гравців
            // I - номер гравця
            for (int I = 1; I < PLAYERS + 1 ; I++) {
                int x1, y1;
                x1 = (I == 1 || I == 4) ? 0 : W - 1;
                y1 = (I == 1 || I == 3) ? 0 : H - 1;
                hexagons[y1][x1].setPlayer(I);
                colorPlayers[I - 1] = hexagons[y1][x1].getColor();
                if (I == 1) continue;
                for (int i = 0; i < I-1; i++) {
                    if (colorPlayers[I - 1] == colorPlayers[i]) {
                        colorPlayers[I - 1] = COLORS[random.nextInt(COLORS.length)];
                        hexagons[y1][x1].setColor(colorPlayers[I-1]);
                        i = -1;
                    }
                }
            }
        }
        
        void paint(Graphics g) {
            for (int j = 0; j < H; j++) {
                for (int i = 0; i < W; i++) {
                    hexagons[j][i].paint(g);
                }
            }
        }
        
        void setColor(int x, int y, int color) {
            hexagons[y][x].setColor(color);
        }
        
        void setPlayer(int x, int y, int player) {
            hexagons[y][x].setPlayer(player);
        }
        
        int getPlayer(int x, int y) {
            if (y < 0 || x < 0 || y > H || x > W)
                return 0;
            return hexagons[y][x].getPlayer();
        }
        
        int getColor(int x, int y) {
            if (y < 0 || x < 0 || y > H || x > W)
                return 0;
            return hexagons[y][x].getColor();
        }
    }
    
    
    // http://math.sgu.ru/sites/chairs/prinf/materials/java/lesson8.htm
    class settingPlay extends JFrame {
        settingPlay() {
            setTitle("НАЛАШТУВАННЯ ПАРАМЕТРІВ ГРИ");
                // для позиціонування вікна по центру
                // визначаємо розміри монітора
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;
                // задаємо розміри вікна
            setSize(500, 500);
            setLocation((screenWidth - getWidth())/2, (screenHeight - getHeight())/2);

            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            System.out.println("вікно нове створено))))");
                // панель з параметрами
            JPanel panelOptoins = new JPanel();
            panelOptoins.setLayout(null);
                // створюємо поля вводу
            JTextField tfWidthHex = new JTextField(10);
            tfWidthHex.setSize(50, 30);
            tfWidthHex.setLocation(10, 10);
            tfWidthHex.setText(String.valueOf(PANTRY_WIDTH));
            panelOptoins.add(tfWidthHex);

            JLabel lblWidthHex = new JLabel("ширина шестикутника");
            lblWidthHex.setSize(150, 30);
            lblWidthHex.setLocation(110, 10);
            panelOptoins.add(lblWidthHex);
            
            //Width = (int)(Math.sqrt(3)/2 * PANTRY_HEIGHT)
            
            JTextField tfHeightHex = new JTextField(10);
            tfHeightHex.setSize(50, 30);
            tfHeightHex.setLocation(10, 50);
            tfHeightHex.setText(String.valueOf(PANTRY_HEIGHT));
            panelOptoins.add(tfHeightHex); //добавляем 2-е поле
            
            JLabel lblHeightHex = new JLabel("висота шестикутника");
            lblHeightHex.setSize(150, 30);
            lblHeightHex.setLocation(110, 50);
            panelOptoins.add(lblHeightHex);
            
            JTextField tfFieldWidth = new JTextField(10);
            tfFieldWidth.setSize(50, 30);
            tfFieldWidth.setLocation(10, 90);
            tfFieldWidth.setText(String.valueOf(FIELD_WIDTH));
            panelOptoins.add(tfFieldWidth); //добавляем 2-е поле
            
            JLabel lblFieldWidth = new JLabel("ширина ігрового поля");
            lblFieldWidth.setSize(150, 30);
            lblFieldWidth.setLocation(110, 90);
            panelOptoins.add(lblFieldWidth);
            
            JTextField tfFieldHeight = new JTextField(10);
            tfFieldHeight.setSize(50, 30);
            tfFieldHeight.setLocation(10, 130);
            tfFieldHeight.setText(String.valueOf(FIELD_HEIGHT));
            panelOptoins.add(tfFieldHeight); //добавляем 2-е поле
            
            JLabel lblFieldHeight = new JLabel("висота ігрового поля");
            lblFieldHeight.setSize(150, 30);
            lblFieldHeight.setLocation(110, 130);
            panelOptoins.add(lblFieldHeight);
            
            JTextField tfW = new JTextField(10);
            tfW.setSize(50, 30);
            tfW.setLocation(10, 170);
            tfW.setText(String.valueOf(W));
            panelOptoins.add(tfW); //добавляем 2-е поле
            
            JLabel lblW = new JLabel("к-сть комірок по ширині");
            lblW.setSize(150, 30);
            lblW.setLocation(110, 170);
            panelOptoins.add(lblW);
            
            JTextField tfH = new JTextField(10);
            tfH.setSize(50, 30);
            tfH.setLocation(10, 210);
            tfH.setText(String.valueOf(H));
            panelOptoins.add(tfH); //добавляем 2-е поле
            
            JLabel lblH = new JLabel("к-сть комірок по висоті");
            lblH.setSize(150, 30);
            lblH.setLocation(110, 210);
            panelOptoins.add(lblH);
            
            JTextField tfPlayers = new JTextField(10);
            tfPlayers.setSize(50, 30);
            tfPlayers.setLocation(10, 350);
            tfPlayers.setText(String.valueOf(PLAYERS));
            panelOptoins.add(tfPlayers); //добавляем 2-е поле
            
            JLabel lblPlayers = new JLabel("кількість гравців");
            lblPlayers.setSize(150, 30);
            lblPlayers.setLocation(110, 350);
            panelOptoins.add(lblPlayers);
            
            JButton btnOK = new JButton("ПРИЙНЯТИ");
            btnOK.setLocation(150, 400);
            btnOK.setSize(100, 40);
            panelOptoins.add(btnOK);
            
            JButton btnCensel = new JButton("Відхилити");
            btnCensel.setLocation(280, 400);
            btnCensel.setSize(100, 40);
            panelOptoins.add(btnCensel);
            
                // додаємо на вікно панель з полями вводу
            add(panelOptoins);

            
                // дія при натисканні кнопки
            btnOK.addActionListener(new ActionListener() {           
                public void actionPerformed(ActionEvent e) {
                    opt.setW(Integer.parseInt(tfW.getText()));
                    opt.setH(Integer.parseInt(tfH.getText()));
                    opt.setPlayers(Integer.parseInt(tfPlayers.getText()));
                    opt.setPantryHeight(Integer.parseInt(tfHeightHex.getText()));
                    opt.setPantryWidth(Integer.parseInt(tfWidthHex.getText()));
                    
                    setSettingPlay(true);
                    setVisible(false);
                    
                }           
            });
            
            btnCensel.addActionListener(new ActionListener() {           
                public void actionPerformed(ActionEvent e) {
                    setSettingPlay(false);
                    setVisible(false);
                    
                }           
            });

            setVisible(true);
        }
    }
    
        
        // extends - это ключевое слово, предназначенное для расширения реализации
        // какого-то существующего класса. Создается новый класс на основе 
        // существующего, и этот новый класс расширяет (extends) возможности старого.
    // опис класа кнопки (шестикутної)
    public class HexagonButtom extends JButton {
        Polygon p = new Polygon();
        
        private int MAX(int[] arr) {
            if (arr.length < 2)
                return arr[0];
            int max = arr[0];
            for (int i = 1; i < arr.length; i++) {
                if (max < arr[i])
                    max = arr[i];
            }
            return max;
        }
        
        private int MIN(int[] arr) {
            if (arr.length < 2)
                return arr[0];
            int min = arr[0];
            for (int i = 1; i < arr.length; i++) {
                if (min > arr[i])
                    min = arr[i];
            }
            return min;
        }
        
        
        
        // конструктор класу
        public HexagonButtom(String s, Polygon p) {
                    // s - напис на кнопці
            super(s);
            // присвоєння полігону цьому обєкту
            this.p = p;
            
            // розміри
            Dimension size = getPreferredSize();
            size.height = MAX(p.ypoints) - MIN(p.ypoints) + 2;
            size.width = MAX(p.xpoints) - MIN(p.xpoints) + 2;
            setPreferredSize(size);
            setContentAreaFilled(false);
        }
        
        // protected - доступно всередині класу і в наслідуваннях
        protected void paintComponent (Graphics g) {
            if (getModel().isEnabled()) {
            if (getModel().isArmed()) {
                g.setColor(Color.red);
            } else {
                if (getModel().isRollover()) {
                    g.setColor(Color.PINK);
                } else {
                    g.setColor(getBackground());
                }
            }}
            g.fillPolygon(p);
            super.paintComponent(g);
        }
        
        protected void paintBorder(Graphics g) {
            g.setColor(Color.blue);
            g.drawPolygon(p);
        }
        
        Shape shape;
        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new Ellipse2D.Float(0, 0,
                    getWidth(), getHeight());
            }
            return shape.contains(x, y);
        }
    }
    
    // подивитись на сайт...
        // https://javaswing.wordpress.com/2009/07/26/jbutton_pressing/
    public class CustomListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            play(button.getActionCommand());
        }

        public void mouseEntered(MouseEvent e) {  }

        public void mouseExited(MouseEvent e) {  }

        public void mousePressed(MouseEvent e) {  }

        public void mouseReleased(MouseEvent e) {  }
    }
    
    
    // опис класа псевдоканвас
    class Canvas extends JPanel {
        @Override // Аннотация, говорящая о том, что этот метод
                // переопредеяет одноимённый метод родительского класса
        public void paint(Graphics g) {
            super.paint(g);
            hexagons.paint(g);
        }
    }
}
