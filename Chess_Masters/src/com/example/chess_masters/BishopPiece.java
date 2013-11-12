package com.example.chess_masters;

import java.util.ArrayList;

public class BishopPiece extends Piece {

	public BishopPiece(PieceColor color, int resource) {
		super(color, resource);
	}

	@Override
	public ArrayList<Position> possibleMoves(Position currentPosition) {
		ArrayList<Position> moves = new ArrayList<Position>();
		int dx[] = { 1, 1, -1, -1 };
		int dy[] = { 1, -1, 1, -1 };
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
