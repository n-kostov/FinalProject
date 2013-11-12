package com.example.chess_masters;

import java.util.ArrayList;

public class KnightPiece extends Piece {

	public KnightPiece(PieceColor color, int resource) {
		super(color, resource);
	}

	@Override
	public ArrayList<Position> possibleMoves(Position currentPosition) {
		ArrayList<Position> moves = new ArrayList<Position>();
		int dx[] = { 1, 2, 2, 1, -1, -2, -2, -1 };
		int dy[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
		for (int i = 0; i < 8; ++i) {
			Position next = new Position(currentPosition.getX() + dx[i],
					currentPosition.getY() + dy[i]);
			if (next.isValid()) {
				moves.add(next);
			}
		}

		return moves;
	}

}
