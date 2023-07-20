package ArtificialIntelligence;

import TicTacToe.Board;


import static TicTacToe.Window.menu2;


public class Algorithms {


    private Algorithms() {}

    public static void random (Board board) {

        //if (menu2.getLabel() == "Level 1") {
          //  System.out.println("Random Algo Level 1");
            Random.run(board);
       // }
    }


    public static void alphaBeta (Board board) {
        //if (menu2.getLabel() == "Level 2") {
         //   System.out.println("Alpha Beta Algo Level 2");

            AlphaBeta.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
       // }
    }

    public static void alphaBetapru (Board board, int ply) {
       // if (menu2.getLabel() == "Level 3") {
         //   System.out.println("Alpha Beta Advance Algo Level 3");
            AlphaBeta.alphaBetaPruning(board.getTurn(), board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);

       // }
    }

}
