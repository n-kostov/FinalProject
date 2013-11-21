package com.example.chess_masters.game;

public class Position {
	private final int x;
	private final int y;

	public Position(int paramInt1, int paramInt2) {
		this.x = paramInt1;
		this.y = paramInt2;
	}

	public boolean equals(Object paramObject) {
		Position localPosition = (Position) paramObject;
		if (localPosition == null) {
			return false;
		}

		return getX() == localPosition.getX() && getY() == localPosition.getY();
	}

	public Position getPositionInDirection(Direction direction, int steps) {
		if (steps <= 0)
			return this;
		switch (direction) {
		default:
			return null;
		case EAST:
			return new Position(this.x, steps + this.y);
		case NORTH:
			return new Position(this.x - steps, this.y);
		case NORTH_EAST:
			return new Position(this.x - steps, steps + this.y);
		case NORTH_WEST:
			return new Position(this.x - steps, this.y - steps);
		case SOUTH:
			return new Position(steps + this.x, this.y);
		case SOUTH_EAST:
			return new Position(steps + this.x, steps + this.y);
		case SOUTH_WEST:
			return new Position(steps + this.x, this.y - steps);
		case WEST:
			return new Position(this.x, this.y - steps);
		}
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean isValid() {
		return (this.x >= 0) && (this.x < 8) && (this.y >= 0) && (this.y < 8);
	}

	public String toString() {
		return Character.toString((char) (65 + this.x))
				+ Character.toString((char) (49 + this.y));
	}
}