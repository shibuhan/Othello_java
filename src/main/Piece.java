package main;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Piece extends PieceBase {

    /** コマの状態を表す定数 */
    public static final String EMP = "- ";
    public static final String SIRO = "○ ";
    public static final String KURO = "● ";

    /** コマの方向を表す列挙型 */
    public enum Direction {
        UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT;
    }

    /**
     * コンストラクタ
     * @param x x座標
     * @param y y座標
     */
    public Piece(int x, int y) {
        setX(x);
        setY(y);
        setState(EMP);
    }

    /**
     * 手番のプレイヤーから見てこのコマが相手のコマかどうか判定します
     * @param myState 手番のプレイヤーの状態
     * @return このコマが相手のコマかどうか
     */
    private boolean isOpponents(String myState) {
        if (getState() != EMP) {
            return getState() != myState;
        } else {
            return false;
        }
    }

    /**
     * 手番のプレイヤーから見てこのコマが自分のコマかどうか判定します
     * @param myState 手番のプレイヤーの状態
     * @return このコマが自分のコマかどうか
     */
    private boolean isMine(String myState) {
        if (getState() != EMP) {
            return getState() == myState;
        } else {
            return false;
        }
    }

    /**
     * このコマがまだ置かれていないかどうか判定します
     * @return このコマがまだ置かれていないかどうか
     */
    public boolean isEmp() {
        return getState() == EMP;
    }

    /**
     * このコマの周りを見て、可能であれば相手のコマをひっくり返します
     * @return 一つ以上ひっくり返せたかどうか
     */
    public boolean canTurnOver() {
        List<Boolean> results = Stream.of(Direction.values()).map(d -> {
            final Piece target = getAround(d);
            final boolean result = turnOver(target, d, 0);
            return result;
        }).collect(Collectors.toList()); // resultが一つでもtrueになった段階でanyMatchに行ってしまうので一度Listにしている。

        return results.stream().anyMatch(result -> result); // 全ての方向で1つ以上ひっくり返せたかどうか。
    }

    /**
     * このコマから見て、指定の方向に進み相手のコマをひっくり返します
     * @param target 指定の方向の隣り合うコマ
     * @param direction 方向
     * @param count 見たコマの個数
     * @return その方向で一つ以上相手のコマをひっくり返せたかどうか
     */
    private boolean turnOver(Piece target, Direction direction, int count) {
        boolean canTurnOverThisDirection = false;

        // 隣り合うコマが相手のコマの場合、探索を進める。
        if (target.isOpponents(getState())) {
            count++;
            final Piece nextTarget = target.getAround(direction);
            canTurnOverThisDirection = turnOver(nextTarget, direction, count); // 再帰呼び出しでさらに隣のコマを見る。今みている方向のコマをひっくり返してもいいかどうか呼び出し先に判断させる。

            // 再帰から帰る時の処理。判断結果がtrueの場合、ひっくりかえす。
            if (canTurnOverThisDirection) {
                target.setState(getState());
            }

            // 呼び出し元(前のコマ)に、判断結果を伝える。
            return canTurnOverThisDirection;

        // 探索終了。最後尾のコマ。
        } else {
            return canTurnOverThisDirection(target, count); // この方向のコマをひっくり返していいか判断する。判断を呼び出し元に返す。
        }
    }

    /**
     * その方向の最後尾のコマからみてその方向のコマをひっくり返していいかどうか判断します。
     * @param target その方向の最後尾のコマ
     * @param count 道中で見た相手のコマの数
     * @return その方向の相手のコマをひっくり返していいかどうか
     */
    private boolean canTurnOverThisDirection(Piece target, int count) {
        // 最後尾のコマが自分のものだった場合
        if (target.isMine(getState())) {
            return count > 0; // 呼び出し元に、この方向のコマをひっくり返していいかどうかを返す。道中に相手のコマがあったらtrue。道中でひとつも相手のコマがなかったらfalse。

        // 最後尾のコマがなかった場合
        } else {
            return false; // 途中で相手のコマがあったかどうかにかかわらずfalseを返す。
        }
    }

    /**
     * このコマから見て指定の方向の隣り合うコマを取得します
     * @param direction 方向
     * @return 指定の方向の隣り合うコマ
     */
    private Piece getAround(Direction direction) {
        switch (direction) {
        case UP:
            return getBoard().getPiece(getX(), getY() - 1);
        case UP_RIGHT:
            return getBoard().getPiece(getX() + 1, getY() - 1);
        case RIGHT:
            return getBoard().getPiece(getX() + 1, getY());
        case DOWN_RIGHT:
            return getBoard().getPiece(getX() + 1, getY() + 1);
        case DOWN:
            return getBoard().getPiece(getX(), getY() + 1);
        case DOWN_LEFT:
            return getBoard().getPiece(getX() - 1, getY() + 1);
        case LEFT:
            return getBoard().getPiece(getX() - 1, getY());
        case UP_LEFT:
            return getBoard().getPiece(getX() - 1, getY() - 1);
        default:
            return new Piece(-1, -1);
        }
    }
}
