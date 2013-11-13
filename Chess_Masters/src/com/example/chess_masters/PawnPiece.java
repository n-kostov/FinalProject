package com.example.chess_masters;

import java.util.ArrayList;
import java.util.Hashtable;

public class PawnPiece extends Piece {
	private boolean hasMoved;

	public PawnPiece(PieceColor color, int resource, Position position) {
		super(color, resource, position);
		this.hasMoved = false;
	}

	public boolean isHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	@Override
	public Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position currentPosition) {
		ArrayList<Position> movesInDirection = new ArrayList<Position>();
		Direction dir = Direction.SOUTH;
		int offset = 1;
		if (getPieceColor() == PieceColor.WHITE_COLOR) {
			offset = -1;
			dir = Direction.NORTH;
		}

		if (!this.hasMoved) {
			movesInDirection.add(new Position(currentPosition.getX() + 2
					* offset, currentPosition.getY()));
		}

		movesInDirection.add(new Position(currentPosition.getX() + offset,
				currentPosition.getY()));

		Hashtable<Direction, ArrayList<Position>> moves = new Hashtable<Direction, ArrayList<Position>>();
		moves.put(dir, movesInDirection);
		return moves;
	}

}
