package com.example.chess_masters.pieces;

import com.example.chess_masters.game.Direction;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;

public class QueenPiece extends Piece {
	public QueenPiece(PieceColor paramPieceColor, Position paramPosition) {
		super(paramPieceColor, paramPosition);
		this.attackDirections.add(Direction.SOUTH_EAST);
		this.attackDirections.add(Direction.SOUTH_WEST);
		this.attackDirections.add(Direction.NORTH_EAST);
		this.attackDirections.add(Direction.NORTH_WEST);
		this.attackDirections.add(Direction.SOUTH);
		this.attackDirections.add(Direction.WEST);
		this.attackDirections.add(Direction.NORTH);
		this.attackDirections.add(Direction.EAST);
	}

	public boolean canAttackInDirection(Direction paramDirection) {
		return this.attackDirections.contains(paramDirection);
	}

	// public Hashtable<Direction, ArrayList<Position>> possibleMoves(
	// Position position) {
	// Hashtable<Direction, ArrayList<Position>> moves = new
	// Hashtable<Direction, ArrayList<Position>>();
	// for (int i = 0; i < this.attackDirections.size(); i++) {
	// ArrayList<Position> movesInDirection = new ArrayList<Position>();
	// for (int j = 1; j < 8; j++) {
	// Position positionInDirection = position.getPositionInDirection(
	// this.attackDirections.get(i), j);
	// if (!positionInDirection.isValid()) {
	// break;
	// }
	//
	// movesInDirection.add(positionInDirection);
	// }
	//
	// moves.put(this.attackDirections.get(i), movesInDirection);
	// }
	//
	// return moves;
	// }
}