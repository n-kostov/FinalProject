package com.example.chess_masters;

import java.util.ArrayList;

public class RookPiece extends Piece {

	public RookPiece(PieceColor color, int resource) {
		super(color, resource);
	}

	@Override
	public ArrayList<Position> possibleMoves(Position currentPosition) {
		ArrayList<Position> moves = new ArrayList<Position>();

		int dx[] = { 1, 0, -1, 0 };
		int dy[] = { 0, -1, 0, 1 };
		for (int j = 0; j < 4; j++) {
			for (int i = 1; i < 8; ++i) {
				Position next = new Position(currentPosition.getX() + i
						* dx[j], currentPosition.getY() + i * dy[j]);
				if (next.isValid()) {
					moves.add(next);
				}
			}
		}

		return moves;
	}
}
