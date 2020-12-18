package main;

import java.util.Scanner;

public class Main {

    static Board board = new Board();
    static Scanner sc;

    /**
     * mainメソッド
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc;

        String turn = Piece.SIRO;
        while (true) { // TODO: 終了条件の実装
            board.showBoard();
            System.out.println(turn + "'s turn.");
            Piece piece = choicePiece();

            if (!piece.isValid()) {
                System.out.println("!!!!Couldn't put piece.!!!!");
                continue;
            }

            piece.setState(turn);

            final boolean couldTurn = piece.canTurnOver();
            if (!couldTurn) {
                System.out.println("!!!!Couldn't put piece.!!!!");
                piece.setState(Piece.EMP);
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

    /**
     * コマンドラインから座標を指定してコマを選択します
     * @return 選択された座標のコマ
     */
    private static Piece choicePiece() {
        sc = new Scanner(System.in);
        final int x = sc.nextInt();
        final int y = sc.nextInt();
        final Piece piece = board.getPiece(x, y);

        return piece;
    }
}
