package ArtificialIntelligence;

import TicTacToe.Board;
public class Random {

    private Random () {}

    public static void run (Board board) {
        int[] moves = new int[board.getAvailableMoves().size()];
        int index = 0;

        for (Integer item : board.getAvailableMoves()) {
            moves[index++] = item;
        }

        int randomMove = moves[new java.util.Random().nextInt(moves.length)];
        board.move(randomMove);
    }

}
