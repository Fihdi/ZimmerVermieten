import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayingField extends JFrame {

    int WIDTH = 600;
    int GRID = 7;
    int FACTOR = WIDTH / GRID;
    int counter = 0;

    int scoreB = 0;
    int scoreR = 0;

    byte EMPTY = 0;
    byte RED = 1;
    byte BLUE = 2;
    byte BORDER = 3;

    java.awt.Rectangle[][] rectH = new Rectangle[GRID][GRID + 1];
    java.awt.Rectangle[][] rectV = new Rectangle[GRID + 1][GRID];

    byte[][] colorsH = new byte[GRID][GRID + 1];
    byte[][] colorsV = new byte[GRID + 1][GRID];
    byte[][] rooms = new byte[GRID][GRID];

    PlayingField() {


        for (int y = 0; y < GRID + 1; y++) {
            for (int x = 0; x < GRID; x++) {

                //Horizontal Walls
                rectH[x][y] = new Rectangle();
                rectH[x][y].setBounds(x * (FACTOR) + 15, y * (FACTOR) + 35, WIDTH / GRID - 10, 10);


            }
        }
        for (int y = 0; y < GRID; y++) {
            for (int x = 0; x < GRID + 1; x++) {

                rectV[x][y] = new Rectangle();
                rectV[x][y].setBounds(x * (FACTOR) + 5, y * (FACTOR) + 45, 10, WIDTH / GRID - 10);

            }
        }
        for (int y = 0; y < GRID + 1; y++) {
            for (int x = 0; x < GRID; x++) {
                if (y == 0 || y == GRID) {
                    colorsH[x][y] = BORDER;
                }
            }
        }
        for (int y = 0; y < GRID; y++) {
            for (int x = 0; x < GRID + 1; x++) {
                if (x == 0 || x == GRID) {
                    colorsV[x][y] = BORDER;
                }


            }
        }
//        if(y==0||y==GRID){
//            colorsH[x][y] = BORDER;
//        }
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {

                int roomsCompleted = 0;
                for (int y = 0; y < GRID + 1; y++) {
                    for (int x = 0; x < GRID; x++) {
                        if (rectH[x][y].contains(e.getPoint()) && colorsH[x][y] == EMPTY) {

                            if (counter % 2 == 0) {
                                colorsH[x][y] = RED;
                            } else {
                                colorsH[x][y] = BLUE;
                            }
                            //Check room above clicked HWall
                            if (colorsH[x][y] != EMPTY && colorsH[x][y - 1] != EMPTY && colorsV[x][y - 1] != EMPTY && colorsV[x + 1][y - 1] != EMPTY) {

                                if (counter % 2 == 0) {
                                    rooms[x][y] = RED;
                                    scoreR++;
                                } else {
                                    rooms[x][y] = BLUE;
                                    scoreB++;
                                }
                                roomsCompleted++;
                            }
                            //Check room under clicked HWall
                            if (colorsH[x][y] != EMPTY && colorsH[x][y + 1] != EMPTY && colorsV[x + 1][y] != EMPTY && colorsV[x][y] != EMPTY) {

                                if (counter % 2 == 0) {
                                    rooms[x][y + 1] = RED;
                                    scoreR++;
                                } else {
                                    rooms[x][y + 1] = BLUE;
                                    scoreB++;
                                }

                                roomsCompleted++;
                            }
                            setTitle("Red: " + scoreR + " Blue: " + scoreB);
                            repaint();
                            counter++;
                            if ((scoreR + scoreB) == (GRID - 1) * (GRID - 1)) {
                                if (scoreB > scoreR) {
                                    setTitle("Blue won!");
                                } else if (scoreB < scoreR) {
                                    setTitle("Red won!");
                                } else {
                                    setTitle("Tied!");
                                }
                            }
                        }
                    }
                }
                for (int y = 0; y < GRID; y++) {
                    for (int x = 0; x < GRID + 1; x++) {
                        if (rectV[x][y].contains(e.getPoint()) && colorsV[x][y] == EMPTY) {

                            if (counter % 2 == 0) {
                                colorsV[x][y] = RED;
                            } else {
                                colorsV[x][y] = BLUE;

                            }
                            //Check room left to clicked VWall
                            if (colorsV[x][y] != EMPTY && colorsH[x - 1][y] != EMPTY && colorsV[x - 1][y] != EMPTY && colorsH[x - 1][y + 1] != EMPTY) {
                                if (counter % 2 == 0) {
                                    rooms[x - 1][y + 1] = RED;
                                    scoreR++;
                                } else {
                                    rooms[x - 1][y + 1] = BLUE;
                                    scoreB++;
                                }
                                roomsCompleted++;
                            }
//Check room right to clicked VWall
                            if (colorsV[x][y] != EMPTY && colorsH[x][y + 1] != EMPTY && colorsV[x + 1][y] != EMPTY && colorsH[x][y] != EMPTY) {
                                if (counter % 2 == 0) {
                                    rooms[x][y + 1] = RED;
                                    scoreR++;
                                } else {
                                    rooms[x][y + 1] = BLUE;
                                    scoreB++;
                                }
                                roomsCompleted++;
                            }

                            setTitle("Red: " + scoreR + " Blue: " + scoreB);
                            repaint();
                            counter++;
                        }
                    }
                }
                if (roomsCompleted != 0) {
                    counter--;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Renting Rooms");
        this.setSize(WIDTH, WIDTH);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        int rad = 10;

        for (int y = 0; y < GRID + 1; y++) {
            for (int x = 0; x < GRID; x++) {
                if (colorsH[x][y] == RED) {
                    g2.setColor(Color.RED);
                    g2.fillRect(rectH[x][y].x, rectH[x][y].y, rectH[x][y].width, rectH[x][y].height);//rectH[x][y]

                } else if (colorsH[x][y] == BLUE) {
                    g2.setColor(Color.BLUE);
                    g2.fillRect(rectH[x][y].x, rectH[x][y].y, rectH[x][y].width, rectH[x][y].height);//rectH[x][y]

                } else if (colorsH[x][y] == BORDER) {
                    g2.setColor(Color.BLACK);
                    g2.fillRect(rectH[x][y].x, rectH[x][y].y, rectH[x][y].width, rectH[x][y].height);//rectH[x][y]

                }
            }
        }
        for (int y = 0; y < GRID; y++) {
            for (int x = 0; x < GRID + 1; x++) {

                if (colorsV[x][y] == RED) {
                    g2.setColor(Color.RED);
                    g2.fillRect(rectV[x][y].x, rectV[x][y].y, rectV[x][y].width, rectV[x][y].height);//rectH[x][y]

                } else if (colorsV[x][y] == BLUE) {
                    g2.setColor(Color.BLUE);
                    g2.fillRect(rectV[x][y].x, rectV[x][y].y, rectV[x][y].width, rectV[x][y].height);//rectH[x][y]

                } else if (colorsV[x][y] == BORDER) {
                    g2.setColor(Color.BLACK);
                    g2.fillRect(rectV[x][y].x, rectV[x][y].y, rectV[x][y].width, rectV[x][y].height);//rectH[x][y]

                }

            }
        }
        g2.setColor(Color.BLACK);
        for (int y = 0; y < GRID; y++) {
            for (int x = 0; x < GRID; x++) {
                g2.fillOval(x * FACTOR + 5, y * FACTOR + 35, 10, 10);
            }
        }
        for (int y = 0; y < GRID; y++) {
            for (int x = 0; x < GRID; x++) {
                if (rooms[x][y] == RED) {
                    g2.setColor(Color.RED);
                    g2.fillOval(x * FACTOR + 30, y * FACTOR , 10, 10);
                } else if (rooms[x][y] == BLUE) {
                    g2.setColor(Color.BLUE);
                    g2.fillOval(x * FACTOR + 30, y * FACTOR , 10, 10);
                }
            }
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

    }
}