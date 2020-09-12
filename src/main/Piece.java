package main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Piece {

    public static final String EMP = "- ";
    public static final String SIRO = "○ ";
    public static final String KURO = "● ";

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

    private final int x;
    private final int y;

    private String state;

    private Board board;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = EMP;
    }

    public boolean isOppo(String myState) {
        if(this.state != EMP) {
            return this.state != myState;
        } else {
            return false;
        }
    }

    public boolean isMine(String mySate) {
        if(this.state != EMP) {
            return this.state == mySate;
        } else {
            return false;
        }
    }

    public boolean isEmp() {
        return this.state == EMP;
    }

    public boolean canTurnOver() {
        final boolean couldTurn = DIRECTION_LIST.stream().map(d -> {
            final Piece target = getAround(d);
            final boolean result = turn(target, d, 0);
            return result;
        }).collect(Collectors.toList()).stream()
            .anyMatch(result -> result);

        return couldTurn;
    }

    public boolean turn(Piece target, int direction, int count) {
        boolean result = false;

        if(target.isOppo(state)) {
            count++;
            final Piece nextTarget = target.getAround(direction);
            result = turn(nextTarget, direction, count);

        } else if(target.isMine(state)) {
            return count > 0;

        } else if(target.isEmp()){
            return false;
        }

        if(result) {
            target.state = this.state;
        } else {
            count = 0;
        }

        return count > 0;
    }

    public Piece getAround(int direction) {
        switch (direction) {
        case UP:
            return board.getPiece(x, y-1);
        case UP_RIGHT:
            return board.getPiece(x+1, y-1);
        case RIGHT:
            return board.getPiece(x+1, y);
        case DOWN_RIGHT:
            return board.getPiece(x+1, y+1);
        case DOWN:
            return board.getPiece(x, y+1);
        case DOWN_LEFT:
            return board.getPiece(x-1, y+1);
        case LEFT:
            return board.getPiece(x-1, y);
        case UP_LEFT:
            return board.getPiece(x-1, y-1);
        default:
            return new Piece(-1, -1);
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
