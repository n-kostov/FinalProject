package com.example.chess_masters;

import java.util.ArrayList;
import java.util.Hashtable;

public class RookPiece extends Piece {

	public RookPiece(PieceColor color, int resource, Position position) {
		super(color, resource, position);
		this.attackDIrections.add(Direction.SOUTH);
		this.attackDIrections.add(Direction.WEST);
		this.attackDIrections.add(Direction.NORTH);
		this.attackDIrections.add(Direction.EAST);
	}

	@Override
	public Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position currentPosition) {

		Hashtable<Direction, ArrayList<Position>> moves = new Hashtable<Direction, ArrayList<Position>>();
		// Direction[] directions = { Direction.SOUTH, Direction.WEST,
		// Direction.NORTH, Direction.EAST };
		int dx[] = { 1, 0, -1, 0 };
		int dy[] = { 0, -1, 0, 1 };
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

			moves.put(this.attackDIrections.get(j), movesInDirection);
		}

		return moves;
	}

	@Override
	public boolean canAttackInDirection(Direction direction) {
		return this.attackDIrections.contains(direction);
	}
}
