package main;

public class Board {

    /** コマ */
    static Piece[][] board = new Piece[8][8];

    /**
     * コンストラクタ
     */
    public Board() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final Piece piece = new Piece(j, i);
                piece.setBoard(this);
                board[i][j] = piece;
            }
        }

        getPiece(3, 3).setState(Piece.KURO);
        getPiece(3, 4).setState(Piece.SIRO);
        getPiece(4, 3).setState(Piece.SIRO);
        getPiece(4, 4).setState(Piece.KURO);
    }

    /**
     * 座標を指定してコマを取得する
     * @param x x座標
     * @param y y座標
     * @return コマ
     */
    public Piece getPiece(int x, int y) {
        if (x < 0 || y < 0 || x > 7 || y > 7) {
            return new Piece(-1, -1);
        } else {
            return board[y][x];
        }
    }

    /**
     * 盤を表示させます
     */
    public void showBoard() {
        System.out.println();

        for (int i = 0; i < 9; i++) {
            if (i == 0) {
                System.out.print("  ");
            } else {
                System.out.print(i - 1 + " ");
            }

            for (int j = 0; j < 8; j++) {
                if (i == 0) {
                    System.out.print(j + " ");
                } else {
                    System.out.print(board[i - 1][j].getState());
                }
            }
            System.out.println();
        }
    }
}
