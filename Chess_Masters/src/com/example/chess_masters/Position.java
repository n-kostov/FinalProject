package com.example.chess_masters;

public class Position {
	private final int x;
	private final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return Character.toString((char) ('A' + x))
				+ Character.toString((char) ('1' + y));
	}
	
	public boolean isValid() {
		return x >= 0 && x < 8 && y >= 0 && y < 8;
	}
}
