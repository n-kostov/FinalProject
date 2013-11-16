package com.example.chess_masters;

import java.util.ArrayList;
import java.util.Hashtable;

public class PawnPiece extends Piece {

	public PawnPiece(PieceColor color, Position position) {
		super(color, position);
		if (color == PieceColor.BLACK_COLOR) {
			this.attackDIrections.add(Direction.SOUTH_EAST);
			this.attackDIrections.add(Direction.SOUTH_WEST);
		} else {
			this.attackDIrections.add(Direction.NORTH_EAST);
			this.attackDIrections.add(Direction.NORTH_WEST);
		}
	}

	@Override
	public Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position currentPosition) {
		ArrayList<Position> movesInDirection = new ArrayList<Position>();
		Direction dir = Direction.SOUTH;
		int offset = 1;
		if (getPieceColor() == PieceColor.WHITE_COLOR) {
			offset = -1;
			dir = Direction.NORTH;
		}

		movesInDirection.add(new Position(currentPosition.getX() + offset,
				currentPosition.getY()));

		if (!this.isHasMoved()) {
			movesInDirection.add(new Position(currentPosition.getX() + 2
					* offset, currentPosition.getY()));
		}

		Hashtable<Direction, ArrayList<Position>> moves = new Hashtable<Direction, ArrayList<Position>>();
		moves.put(dir, movesInDirection);
		return moves;
	}

	@Override
	public boolean canAttackInDirection(Direction direction) {
		return this.attackDIrections.contains(direction);
	}

}
