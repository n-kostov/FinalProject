package com.example.chess_masters;

import java.util.ArrayList;
import java.util.Hashtable;

public class KingPiece extends Piece {

	private boolean hasMoved;

	public KingPiece(PieceColor color, int resource) {
		super(color, resource);
		this.setHasMoved(false);
	}

	@Override
	public Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position currentPosition) {

		Hashtable<Direction, ArrayList<Position>> moves = new Hashtable<Direction, ArrayList<Position>>();
		Direction[] directions = { Direction.SOUTH_EAST, Direction.SOUTH_WEST,
				Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH,
				Direction.WEST, Direction.NORTH, Direction.EAST };
		int dx[] = { 1, 1, -1, -1, 1, 0, -1, 0 };
		int dy[] = { 1, -1, 1, -1, 0, -1, 0, 1 };
		for (int i = 0; i < 8; ++i) {
			ArrayList<Position> movesInDirection = new ArrayList<Position>();
			Position next = new Position(currentPosition.getX() + dx[i],
					currentPosition.getY() + dy[i]);
			if (next.isValid()) {
				movesInDirection.add(next);
				moves.put(directions[i], movesInDirection);
			}
		}

		return moves;
	}

	public boolean isHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

}
