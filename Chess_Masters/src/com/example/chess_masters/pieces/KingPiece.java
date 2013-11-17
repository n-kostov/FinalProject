package com.example.chess_masters.pieces;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.chess_masters.game.Direction;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;

public class KingPiece extends Piece {

	public KingPiece(PieceColor color, Position position) {
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
		for (int i = 0; i < 8; ++i) {
			ArrayList<Position> movesInDirection = new ArrayList<Position>();
			Position next = new Position(currentPosition.getX() + dx[i],
					currentPosition.getY() + dy[i]);
			if (next.isValid()) {
				movesInDirection.add(next);
				moves.put(this.attackDirections.get(i), movesInDirection);
			}
		}

		return moves;
	}

	@Override
	public boolean canAttackInDirection(Direction direction) {
		return this.attackDirections.contains(direction);
	}

}
