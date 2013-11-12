package com.example.chess_masters;

import java.util.ArrayList;

public class KingPiece extends Piece {

	private boolean hasMoved;

	public KingPiece(PieceColor color, int resource) {
		super(color, resource);
		this.setHasMoved(false);
	}

	@Override
	public ArrayList<Position> possibleMoves(Position currentPosition) {
		ArrayList<Position> moves = new ArrayList<Position>();

		int dx[] = { 1, 1, -1, -1, 1, 0, -1, 0 };
		int dy[] = { 1, -1, 1, -1, 0, -1, 0, 1 };
		for (int i = 1; i < 8; ++i) {
			Position next = new Position(currentPosition.getX() + dx[i],
					currentPosition.getY() + dy[i]);
			if (next.isValid()) {
				moves.add(next);
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
