package com.example.chess_masters.pieces;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.chess_masters.game.Direction;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;

public class BishopPiece extends Piece {

	public BishopPiece(PieceColor color, Position position) {
		super(color, position);
		this.attackDirections.add(Direction.SOUTH_EAST);
		this.attackDirections.add(Direction.SOUTH_WEST);
		this.attackDirections.add(Direction.NORTH_EAST);
		this.attackDirections.add(Direction.NORTH_WEST);
	}

	@Override
	public Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position currentPosition) {
		Hashtable<Direction, ArrayList<Position>> moves = new Hashtable<Direction, ArrayList<Position>>();
		// Direction[] directions = { Direction.SOUTH_EAST,
		// Direction.SOUTH_WEST,
		// Direction.NORTH_EAST, Direction.NORTH_WEST };
		int dx[] = { 1, 1, -1, -1 };
		int dy[] = { 1, -1, 1, -1 };
		for (int j = 0; j < 4; j++) {
			ArrayList<Position> movesInDirection = new ArrayList<Position>();
			for (int i = 1; i < 8; ++i) {
				Position next = new Position(
						currentPosition.getX() + i * dx[j],
						currentPosition.getY() + i * dy[j]);
				if (next.isValid()) {
					movesInDirection.add(next);
				}
			}

			moves.put(this.attackDirections.get(j), movesInDirection);
		}

		return moves;
	}

	@Override
	public boolean canAttackInDirection(Direction direction) {
		return this.attackDirections.contains(direction);
	}

}
