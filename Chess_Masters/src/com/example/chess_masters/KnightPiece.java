package com.example.chess_masters;

import java.util.ArrayList;
import java.util.Hashtable;

public class KnightPiece extends Piece {

	public KnightPiece(PieceColor color, int resource) {
		super(color, resource);
	}

	@Override
	public Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position currentPosition) {

		ArrayList<Position> knightMoves = new ArrayList<Position>();
		int dx[] = { 1, 2, 2, 1, -1, -2, -2, -1 };
		int dy[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
		for (int i = 0; i < 8; ++i) {
			Position next = new Position(currentPosition.getX() + dx[i],
					currentPosition.getY() + dy[i]);
			if (next.isValid()) {
				knightMoves.add(next);
			}
		}

		Hashtable<Direction, ArrayList<Position>> moves = new Hashtable<Direction, ArrayList<Position>>();
		moves.put(Direction.KNIGHT, knightMoves);
		return moves;
	}

}
