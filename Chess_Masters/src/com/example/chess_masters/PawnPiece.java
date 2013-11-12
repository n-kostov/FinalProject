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
		if(!this.hasMoved) {
			moves.add(new Position(currentPosition.getX() + 2, currentPosition.getY()));
		}
		
		moves.add(new Position(currentPosition.getX() + 1, currentPosition.getY()));
		return moves;
	}

}
