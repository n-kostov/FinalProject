package com.example.chess_masters.game;

public enum Direction {
	NORTH, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, EAST, KNIGHT;

	public Direction getOppositeDirection() {
		switch (this) {
		case EAST:
			return WEST;
		case KNIGHT:
			return KNIGHT;
		case NORTH:
			return SOUTH;
		case NORTH_EAST:
			return SOUTH_WEST;
		case NORTH_WEST:
			return SOUTH_EAST;
		case SOUTH:
			return NORTH;
		case SOUTH_EAST:
			return NORTH_WEST;
		case SOUTH_WEST:
			return NORTH_EAST;
		case WEST:
			return EAST;
		default:
			return null;
		}
	}
}