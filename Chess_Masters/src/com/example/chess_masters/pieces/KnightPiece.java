package com.example.chess_masters.pieces;

import com.example.chess_masters.game.Direction;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;
import java.util.ArrayList;
import java.util.Hashtable;

public class KnightPiece extends Piece {
	public KnightPiece(PieceColor paramPieceColor, Position paramPosition) {
		super(paramPieceColor, paramPosition);
		this.attackDirections.add(Direction.KNIGHT);
	}

	public boolean canAttackInDirection(Direction paramDirection) {
		return this.attackDirections.contains(paramDirection);
	}

	public Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position position) {
		ArrayList<Position> movesInDirection = new ArrayList<Position>();
		int[] dx = { 1, 2, 2, 1, -1, -2, -2, -1 };
		int[] dy = { 2, 1, -1, -2, -2, -1, 1, 2 };
		for (int i = 0; i < dx.length; i++) {
			Position positionInDirection = new Position(
					position.getX() + dx[i], position.getY() + dy[i]);
			if (positionInDirection.isValid()) {
				movesInDirection.add(positionInDirection);
			}
		}

		Hashtable<Direction, ArrayList<Position>> moves = new Hashtable<Direction, ArrayList<Position>>();
		moves.put(this.attackDirections.get(0), movesInDirection);
		return moves;
	}
}