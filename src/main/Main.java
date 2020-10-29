package main;

import java.util.Scanner;

public class Main {

    static Board board = new Board();

    public static void main(String[] args) {
        board.getPiece(3, 3).setState(Piece.KURO);
        board.getPiece(3, 4).setState(Piece.SIRO);
        board.getPiece(4, 3).setState(Piece.SIRO);
        board.getPiece(4, 4).setState(Piece.KURO);

        board.showBoard();

        String turn = Piece.SIRO;
        while (true) {
            System.out.println(turn + "'s turn.");
            final Scanner sc = new Scanner(System.in);
            final int x = sc.nextInt();
            final int y = sc.nextInt();

            final Piece piece = board.getPiece(x, y);
            final String defaultState = piece.getState();
            piece.setState(turn);

            final boolean couldTurn = piece.canTurnOver();
            if (!couldTurn) {
                System.out.println("!!!!Couldn't put piece.!!!!");
                piece.setState(defaultState);
                if (turn == Piece.SIRO) {
                    turn = Piece.KURO;
                } else {
                    turn = Piece.SIRO;
                }
            }

            board.showBoard();

            if (turn == Piece.SIRO) {
                turn = Piece.KURO;
            } else {
                turn = Piece.SIRO;
            }
        }
    }
}
