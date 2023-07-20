package TicTacToe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import TicTacToe.*;
import ArtificialIntelligence.*;

public class Window extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private static Board board;
    private static Panel panel;
    private BufferedImage imageBackground, imageX, imageO;

    private enum Mode {Player, AI}
    private  Mode mode;


    private Point[] cells;

    private static final int DISTANCE = 100;

     Window () {
        this(Mode.AI);
    }

    private MenuBar mb=new MenuBar();

    private MenuItem i1=new MenuItem("Player");
    private MenuItem i2=new MenuItem("AI");
    private MenuItem i21=new MenuItem("Level 1");
    private MenuItem i22=new MenuItem("Level 2");
    private MenuItem i23=new MenuItem("Level 3");
    private static Menu menu=new Menu("Game Player");
    public static Menu menu2=new Menu("Levels");

    private Window (Mode mode) {
        this.mode = mode;
        board = new Board();
        loadCells();
        panel = createPanel();
        setWindowProperties();
        loadImages();
    }

    private void loadCells () {
        cells = new Point[9];

        cells[0] = new Point(109, 109);
        cells[1] = new Point(299, 109);
        cells[2] = new Point(489, 109);
        cells[3] = new Point(109, 299);
        cells[4] = new Point(299, 299);
        cells[5] = new Point(489, 299);
        cells[6] = new Point(109, 489);
        cells[7] = new Point(299, 489);
        cells[8] = new Point(489, 489);
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setTitle("Tic Tac Toe");

        menu.add(i1);
        menu.add(i2);
        mb.add(menu);

        menu2.add(i21);
        menu2.add(i22);
        menu2.add(i23);
        mb.add(menu2);

        setMenuBar(mb);
        Action_Menu();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);


    }

    private  void Action_Menu(){
        i1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             System.out.println("Game Mode: Player vs. Player");
             mode = Mode.Player;
             board.reset();
             panel.repaint();
         }
     });

        i2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             System.out.println("Game Mode: Player vs. AI");
             mode = Mode.AI;
             board.reset();
             panel.repaint();
         }
     });

        /////Levels////
        i21.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {

            //                  SwingUtilities.invokeLater(() -> new Window(Mode.Player));
                     System.out.println("Level 1");
                     menu2.setLabel("Level 1");
                     System.out.println("Random Algo Level 1");
                     Random.run(board);

                 }
             }
        );
        i22.addActionListener(new ActionListener() {
         @Override
                 public void actionPerformed(ActionEvent e) {

//                  SwingUtilities.invokeLater(() -> new Window(Mode.Player));
                     System.out.println("Level 2");
                     menu2.setLabel("Level 2");
                     System.out.println("Alpha Beta Algo Level 2");

                     AlphaBeta.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
                 }
             }
        );

        i23.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {

//                  SwingUtilities.invokeLater(() -> new Window(Mode.Player));
             System.out.println("Level 3");
             menu2.setLabel("Level 3");
             System.out.println("Alpha Beta Advance Algo Level 3");
             AlphaBeta.alphaBetaPruning(board.getTurn(), board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
         }
     }
        );
    }
    private Panel createPanel () {
        Panel panel = new Panel();
        Container cp = getContentPane();
        cp.add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.addMouseListener(new MyMouseAdapter());
        return panel;
    }

    private void loadImages () {
        imageBackground = getImage("background");
        imageX = getImage("x");
        imageO = getImage("o");
    }


    private static BufferedImage getImage (String path) {

        BufferedImage image;

        try {
            path = ".." + File.separator + "Assets" + File.separator + path + ".png";
            image = ImageIO.read(Window.class.getResource(path));
        } catch (IOException ex) {
            throw new RuntimeException("Image could not be loaded.");
        }

        return image;
    }


    private class Panel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            paintTicTacToe((Graphics2D) g);
        }

        private void paintTicTacToe (Graphics2D g) {
            setProperties(g);
            paintBoard(g);
            paintWinner(g);
        }


        private void setProperties (Graphics2D g) {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(imageBackground, 0, 0, null);

            // The first time a string is drawn it tends to lag.
            // Drawing something trivial at the beginning loads the font up.
            // Better to lag at the beginning than during the middle of the game.
            g.drawString("", 0, 0);
        }

        private void paintBoard (Graphics2D g) {
            Board.State[][] boardArray = board.toArray();

            int offset = 20;

            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (boardArray[y][x] == Board.State.X) {
                        g.drawImage(imageX, offset + 190 * x, offset + 190 * y, null);
                    } else if (boardArray[y][x] == Board.State.O) {
                        g.drawImage(imageO, offset + 190 * x, offset + 190 * y, null);
                    }
                }
            }
        }
        private void paintWinner (Graphics2D g) {
            if (board.isGameOver()) {
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("TimesRoman", Font.PLAIN, 50));

                String s;

                if (board.getWinner() == Board.State.Blank) {
                    s = "Draw";
                } else {
                    s = board.getWinner() + " Wins!";
                }

                g.drawString(s, 300 - getFontMetrics(g.getFont()).stringWidth(s)/2, 315);

            }
        }
    }
    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mouseClicked(e);

            if (board.isGameOver()) {
                board.reset();
                panel.repaint();
            } else {
                playMove(e);
            }



        }



        private void playMove (MouseEvent e) {
            int move = getMove(e.getPoint());

            if (!board.isGameOver() && move != -1) {
                boolean validMove = board.move(move);
                if (mode == Mode.AI && validMove && !board.isGameOver()) {
                    Algorithms.alphaBeta(board);
                }
                panel.repaint();
            }
        }
        private int getMove (Point point) {
            for (int i = 0; i < cells.length; i++) {
                if (distance(cells[i], point) <= DISTANCE) {
                    return i;
                }
            }
            return -1;
        }


        private double distance (Point p1, Point p2) {
            double xDiff = p1.getX() - p2.getX();
            double yDiff = p1.getY() - p2.getY();

            double xDiffSquared = xDiff*xDiff;
            double yDiffSquared = yDiff*yDiff;

            return Math.sqrt(xDiffSquared+yDiffSquared);
        }
    }

    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> new Window(Mode.Player));
//        if (Player) {
//            System.out.println("Game Mode: Player vs. Player");
//           SwingUtilities.invokeLater(() -> new Window(Mode.Player));
//       } else {
//           System.out.println("Game Mode: Player vs. AI");
//           SwingUtilities.invokeLater(() -> new Window(Mode.AI));
//        }

    }

}
