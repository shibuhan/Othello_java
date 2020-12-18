package main;

import java.util.Scanner;

public class Main {

    /**
     * mainメソッド
     * @param args
     */
    public static void main(String[] args) {
        Board board = new Board();
        Scanner sc;

        String turn = Piece.SIRO;
        while (true) { // TODO: 終了条件の実装
            board.showBoard();
            System.out.println(turn + "'s turn.");
            sc = new Scanner(System.in);
            final int x = sc.nextInt();
            final int y = sc.nextInt();

            // 盤外の座標を指定された場合
            if(x < 0 || x > 7 || y < 0 || y > 7) {
                System.out.println("!!!!Couldn't put piece.!!!!");
                continue;
            }

            final Piece piece = board.getPiece(x, y);

            // すでに取られているコマだった場合
            if (!piece.isEmp()) {
                System.out.println("!!!!Couldn't put piece.!!!!");
                continue;
            }

            final String defaultState = piece.getState();
            piece.setState(turn);

            final boolean couldTurn = piece.canTurnOver();
            if (!couldTurn) {
                System.out.println("!!!!Couldn't put piece.!!!!");
                piece.setState(defaultState);
                continue;
            }

            if (turn == Piece.SIRO) {
                turn = Piece.KURO;
            } else {
                turn = Piece.SIRO;
            }
        }

//        sc.close();
    }
}
