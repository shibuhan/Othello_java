package main;

public class PieceBase {
	
	/** x座標 */
    private int x;
    
    /** y座標 */
    private int y;

    /** 状態 */
    private String state;

    /** 盤 */
    private Board getBoard;

    /**
     * x座標を取得します
     * @return x座標
     */
	public int getX() {
		return x;
	}

	/**
	 * x座標をセットします
	 * @param x x座標
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * y座標を取得します
	 * @return y座標
	 */
	public int getY() {
		return y;
	}

	/**
	 * y座標をセットします
	 * @param y y座標
	 */
	public void setY(int y) {
		this.y = y;
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
	 * @return 盤
	 */
	public Board getBoard() {
		return getBoard;
	}

	/**
	 * 盤をセットします
	 * @param board 盤
	 */
	public void setBoard(Board board) {
		this.getBoard = board;
	}
}
