package com.example.chess_masters.pieces;

import com.example.chess_masters.game.Direction;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;
import java.util.ArrayList;
import java.util.Hashtable;

public class PawnPiece extends Piece {
	public PawnPiece(PieceColor paramPieceColor, Position paramPosition) {
		super(paramPieceColor, paramPosition);
		if (paramPieceColor == PieceColor.BLACK_COLOR) {
			this.attackDirections.add(Direction.SOUTH_EAST);
			this.attackDirections.add(Direction.SOUTH_WEST);
		} else {
			this.attackDirections.add(Direction.NORTH_EAST);
			this.attackDirections.add(Direction.NORTH_WEST);
		}
	}

	public boolean canAttackInDirection(Direction direction) {
		return this.attackDirections.contains(direction);
	}

	public Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position position) {
		ArrayList<Position> movesInDirection = new ArrayList<Position>();
		Direction movingDirection = Direction.SOUTH;
		if (this.getPieceColor() == PieceColor.WHITE_COLOR) {
			movingDirection = Direction.NORTH;
		}

		movesInDirection.add(position
				.getPositionInDirection(movingDirection, 1));
		if (!isHasMoved()) {
			movesInDirection.add(position.getPositionInDirection(
					movingDirection, 2));
		}

		Hashtable<Direction, ArrayList<Position>> moves = new Hashtable<Direction, ArrayList<Position>>();
		moves.put(movingDirection, movesInDirection);
		return moves;
	}
}