package com.example.chess_masters.game;


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

	@Override
	public boolean equals(Object o) {
		Position pos = (Position) o;
		if (pos == null) {
			return false;
		}

		return this.getX() == pos.getX() && this.getY() == pos.getY();
	}

	public boolean isValid() {
		return x >= 0 && x < 8 && y >= 0 && y < 8;
	}

	// TODO: use everywhere
	public Position getPositionInDirection(Direction direction, int steps) {
		if (steps <= 0) {
			return this;
		}

		switch (direction) {
		case EAST:
			return new Position(this.x, this.y + steps);
		case KNIGHT:
			return this;
		case NORTH:
			return new Position(this.x - steps, this.y);
		case NORTH_EAST:
			return new Position(this.x - steps, this.y + steps);
		case NORTH_WEST:
			return new Position(this.x - steps, this.y - steps);
		case SOUTH:
			return new Position(this.x + steps, this.y);
		case SOUTH_EAST:
			return new Position(this.x + steps, this.y + steps);
		case SOUTH_WEST:
			return new Position(this.x + steps, this.y - steps);
		case WEST:
			return new Position(this.x, this.y - steps);
		default:
			return null;
		}
	}
}
