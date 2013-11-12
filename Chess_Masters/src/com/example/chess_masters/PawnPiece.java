package com.example.chess_masters;

import java.util.ArrayList;

public class PawnPiece extends Piece {

	private boolean hasMoved;

	public PawnPiece(PieceColor color, int resource) {
		super(color, resource);
		this.hasMoved = false;
	}

	public boolean isHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	@Override
	public ArrayList<Position> possibleMoves(Position currentPosition) {
		ArrayList<Position> moves = new ArrayList<Position>();
		int offset = 1;
		if (getPieceColor() == PieceColor.WHITE_COLOR) {
			offset = -1;
		}

		if (!this.hasMoved) {
			moves.add(new Position(currentPosition.getX() + 2 * offset,
					currentPosition.getY()));
		}

		moves.add(new Position(currentPosition.getX() + offset, currentPosition
				.getY()));
		return moves;
	}

}
