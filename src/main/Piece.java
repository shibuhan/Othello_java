package main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Piece {

    /** コマの状態を表す定数 */
    public static final String EMP = "- ";
    public static final String SIRO = "○ ";
    public static final String KURO = "● ";

    /** コマの方向を表す定数 */
    public static final int UP = 1;
    public static final int UP_RIGHT = 2;
    public static final int RIGHT = 3;
    public static final int DOWN_RIGHT = 4;
    public static final int DOWN = 5;
    public static final int DOWN_LEFT = 6;
    public static final int LEFT = 7;
    public static final int UP_LEFT = 8;
    public static final List<Integer> DIRECTION_LIST = Arrays.asList(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT,
        LEFT, UP_LEFT);

    /** 座標 */
    private final int x;
    private final int y;

    /** 状態 */
    private String state;

    /** 盤 */
    private Board board;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = EMP;
    }

    /**
     * 手番のプレイヤーから見てこのコマが相手のコマかどうか判定します
     * @param myState 手番のプレイヤーの状態
     * @return このコマが相手のコマかどうか
     */
    public boolean isOppo(String myState) {
        if (this.state != EMP) {
            return this.state != myState;
        } else {
            return false;
        }
    }

    /**
     * 手番のプレイヤーから見てこのコマが自分のコマかどうか判定します
     * @param mySate 手番のプレイヤーの状態
     * @return このコマが自分のコマかどうか
     */
    public boolean isMine(String mySate) {
        if (this.state != EMP) {
            return this.state == mySate;
        } else {
            return false;
        }
    }

    /**
     * このコマがまだ置かれていないかどうか判定します
     * @return このコマがまだ置かれていないかどうか
     */
    public boolean isEmp() {
        return this.state == EMP;
    }

    /**
     * このコマの周りを見て、可能であれば相手のコマをひっくり返します<br>
     * @return 一つ以上ひっくり返せたかどうか
     */
    public boolean canTurnOver() {
        final boolean couldTurn = DIRECTION_LIST.stream().map(d -> {
            final Piece target = getAround(d);
            final boolean result = turn(target, d, 0);
            return result;
        }).collect(Collectors.toList()).stream()
            .anyMatch(result -> result);

        return couldTurn;
    }

    /**
     * このコマから見て、指定の方向に進み相手のコマをひっくり返します
     * @param target 指定の方向の隣り合うコマ
     * @param direction 方向
     * @param count 見たコマの個数
     * @return その方向で一つ以上相手のコマをひっくり返せたかどうか
     */
    public boolean turn(Piece target, int direction, int count) {
        boolean result = false;

        if (target.isOppo(state)) {
            count++;
            final Piece nextTarget = target.getAround(direction);
            result = turn(nextTarget, direction, count);

        } else if (target.isMine(state)) {
            return count > 0;

        } else if (target.isEmp()) {
            return false;
        }

        if (result) {
            target.state = this.state;
        } else {
            count = 0;
        }

        return count > 0;
    }

    /**
     * このコマから見て指定の方向の隣り合うコマを取得します
     * @param direction 方向
     * @return 指定の方向の隣り合うコマ
     */
    public Piece getAround(int direction) {
        switch (direction) {
        case UP:
            return board.getPiece(x, y - 1);
        case UP_RIGHT:
            return board.getPiece(x + 1, y - 1);
        case RIGHT:
            return board.getPiece(x + 1, y);
        case DOWN_RIGHT:
            return board.getPiece(x + 1, y + 1);
        case DOWN:
            return board.getPiece(x, y + 1);
        case DOWN_LEFT:
            return board.getPiece(x - 1, y + 1);
        case LEFT:
            return board.getPiece(x - 1, y);
        case UP_LEFT:
            return board.getPiece(x - 1, y - 1);
        default:
            return new Piece(-1, -1);
        }
    }

    /**
     * 状態を取得します
     * @return 状態
     */
    public String getState() {
        return state;
    }

    /**
     * 状態をセットします
     * @param state 状態
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 盤を取得します
     * @param board 盤
     */
    public void setBoard(Board board) {
        this.board = board;
    }
}
