import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class PlayingField extends JFrame {

    private int GRID = 7;
    private int WIDTH = 500;
    private int FACTOR = WIDTH / GRID;
    private int counter = 0;

    private int scoreB = 0;
    private int scoreR = 0;

    private byte EMPTY = 0;
    private byte RED = 1;
    private byte BLUE = 2;
    private byte BORDER = 3;

    private java.awt.Rectangle[][] wallH = new Rectangle[GRID][GRID + 1];
    private java.awt.Rectangle[][] wallV = new Rectangle[GRID + 1][GRID];

    private byte[][] wallColorH = new byte[GRID][GRID + 1];
    private byte[][] wallColorV = new byte[GRID + 1][GRID];
    private byte[][] rooms = new byte[GRID][GRID];

    PlayingField() {
        for (int y = 0; y < GRID + 1; y++) {
            for (int x = 0; x < GRID; x++) {
                //Horizontal Walls
                wallH[x][y] = new Rectangle();
                wallH[x][y].setBounds(x * (FACTOR) + 15, y * (FACTOR) + 35, WIDTH / GRID - 10, 10);
            }
        }
        for (int y = 0; y < GRID; y++) {
            for (int x = 0; x < GRID + 1; x++) {
                //Vertical Walls
                wallV[x][y] = new Rectangle();
                wallV[x][y].setBounds(x * (FACTOR) + 5, y * (FACTOR) + 45, 10, WIDTH / GRID - 10);
            }
        }

        //Setting the border
        for (int y = 0; y < GRID + 1; y++) {
            for (int x = 0; x < GRID; x++) {
                if (y == 0 || y == GRID)
                    wallColorH[x][y] = BORDER;
            }
        }
        for (int y = 0; y < GRID; y++) {
            for (int x = 0; x < GRID + 1; x++) {
                if (x == 0 || x == GRID)
                    wallColorV[x][y] = BORDER;
            }
        }

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {

                int roomsCompleted = 0;
                //Checking for the clicked horizontal Wall that is EMPTY and not a BORDER
                for (int y = 0; y < GRID + 1; y++) {
                    for (int x = 0; x < GRID; x++)
                        if (wallH[x][y].contains(e.getPoint()) && wallColorH[x][y] == EMPTY) {

                            //Coloring the clicked Horizontal Wall
                            wallColorH[x][y] = counter % 2 == 0 ? RED : BLUE;

                            //Check room above clicked HWall
                            if (wallColorH[x][y] != EMPTY && wallColorH[x][y - 1] != EMPTY && wallColorV[x][y - 1] != EMPTY && wallColorV[x + 1][y - 1] != EMPTY) {
                                rooms[x][y - 1] = counter % 2 == 0 ? RED : BLUE;
                                scoreR = counter % 2 == 0 ? scoreR + 1 : scoreR;
                                scoreB = counter % 2 == 0 ? scoreB : scoreB + 1;
                                roomsCompleted++;
                            }
                            //Check room under clicked HWall
                            if (wallColorH[x][y] != EMPTY && wallColorH[x][y + 1] != EMPTY && wallColorV[x + 1][y] != EMPTY && wallColorV[x][y] != EMPTY) {
                                rooms[x][y] = counter % 2 == 0 ? RED : BLUE;
                                scoreR = counter % 2 == 0 ? scoreR + 1 : scoreR;
                                scoreB = counter % 2 == 0 ? scoreB : scoreB + 1;
                                roomsCompleted++;
                            }
                            setTitle("Red: " + scoreR + " Blue: " + scoreB);
                            repaint();
                            counter++;
                            //Determine winner

                        }
                }
                //Checking for the clicked Vertical Wall that is EMPTY and not BORDER
                for (int y = 0; y < GRID; y++) {
                    for (int x = 0; x < GRID + 1; x++) {
                        if (wallV[x][y].contains(e.getPoint()) && wallColorV[x][y] == EMPTY) {
                            //Coloring the clicked vertical Wall
                            wallColorV[x][y] = counter % 2 == 0 ? RED : BLUE;
                            //Check room left to clicked VWall
                            if (wallColorV[x][y] != EMPTY && wallColorH[x - 1][y] != EMPTY && wallColorV[x - 1][y] != EMPTY && wallColorH[x - 1][y + 1] != EMPTY) {
                                rooms[x - 1][y] = counter % 2 == 0 ? RED : BLUE;
                                scoreR = counter % 2 == 0 ? scoreR + 1 : scoreR;
                                scoreB = counter % 2 == 0 ? scoreB : scoreB + 1;
                                roomsCompleted++;
                            }
//Check room right to clicked VWall
                            if (wallColorV[x][y] != EMPTY && wallColorH[x][y + 1] != EMPTY && wallColorV[x + 1][y] != EMPTY && wallColorH[x][y] != EMPTY) {
                                rooms[x][y] = counter % 2 == 0 ? RED : BLUE;
                                scoreR = counter % 2 == 0 ? scoreR + 1 : scoreR;
                                scoreB = counter % 2 == 0 ? scoreB : scoreB + 1;
                                roomsCompleted++;
                            }
                            setTitle("Red: " + scoreR + " Blue: " + scoreB);
                            repaint();
                            counter++;
                            if ((scoreR + scoreB) == GRID * GRID) {
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
                //If a room was completed the counter mustn't increase so it stays either even or odd
                //thus it's the same colors turn.
                if (roomsCompleted != 0)
                    counter--;
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
        this.setLayout(null);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Renting Rooms");
        this.setSize(WIDTH + 50, WIDTH + 50);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        //Drawing dots to click in between
        g2.setColor(Color.BLACK);
        for (int y = 0; y < GRID + 1; y++) {
            for (int x = 0; x < GRID + 1; x++) {
                g2.fillOval(x * FACTOR + 5, y * FACTOR + 35, 10, 10);
            }
        }

        //Drawing the Horizontal Walls
        for (int y = 0; y < GRID + 1; y++) {
            for (int x = 0; x < GRID; x++) {

                if (wallColorH[x][y] == RED)
                    g2.setColor(Color.RED);
                else if (wallColorH[x][y] == BLUE)
                    g2.setColor(Color.BLUE);
                else if (wallColorH[x][y] == BORDER)
                    g2.setColor(Color.BLACK);
                else if (wallColorH[x][y] == EMPTY)
                    g2.setColor(Color.WHITE);
                g2.fill(wallH[x][y]);
            }
        }
        //Drawing the Vertical Walls
        for (int y = 0; y < GRID; y++) {
            for (int x = 0; x < GRID + 1; x++) {
                if (wallColorV[x][y] == RED)
                    g2.setColor(Color.RED);
                else if (wallColorV[x][y] == BLUE)
                    g2.setColor(Color.BLUE);
                else if (wallColorV[x][y] == BORDER)
                    g2.setColor(Color.BLACK);
                else if (wallColorV[x][y] == EMPTY)
                    g2.setColor(Color.WHITE);
                g2.fill(wallV[x][y]);
            }
        }
        //Coloring the rooms
        for (int y = 0; y < GRID; y++) {
            for (int x = 0; x < GRID; x++) {
                if (rooms[x][y] == RED)
                    g2.setColor(Color.RED);
                else if (rooms[x][y] == BLUE)
                    g2.setColor(Color.BLUE);
                else if (rooms[x][y] == BLUE)
                    g2.setColor(Color.BLUE);
                else if (rooms[x][y] == EMPTY)
                    g2.setColor(Color.WHITE);
                g2.fillOval(x * FACTOR + wallH[0][0].width / 2, y * FACTOR + wallH[0][0].width / 2 + 35, 15, 15);
            }
        }
        Graphics2D g2dComponent = (Graphics2D) g;
        g2dComponent.drawImage(bufferedImage, null, 0, 0);
    }
}
