package com.example.chess_masters.pieces;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.chess_masters.game.Direction;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;

public class QueenPiece extends Piece {

	public QueenPiece(PieceColor color, Position position) {
		super(color, position);
		this.attackDirections.add(Direction.SOUTH_EAST);
		this.attackDirections.add(Direction.SOUTH_WEST);
		this.attackDirections.add(Direction.NORTH_EAST);
		this.attackDirections.add(Direction.NORTH_WEST);
		this.attackDirections.add(Direction.SOUTH);
		this.attackDirections.add(Direction.WEST);
		this.attackDirections.add(Direction.NORTH);
		this.attackDirections.add(Direction.EAST);
	}

	@Override
	public Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position currentPosition) {
		Hashtable<Direction, ArrayList<Position>> moves = new Hashtable<Direction, ArrayList<Position>>();
		// Direction[] directions = { Direction.SOUTH_EAST,
		// Direction.SOUTH_WEST,
		// Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH,
		// Direction.WEST, Direction.NORTH, Direction.EAST };
		int dx[] = { 1, 1, -1, -1, 1, 0, -1, 0 };
		int dy[] = { 1, -1, 1, -1, 0, -1, 0, 1 };
		for (int j = 0; j < 8; j++) {
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
