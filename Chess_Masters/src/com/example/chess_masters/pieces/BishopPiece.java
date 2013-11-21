package com.example.chess_masters.pieces;

import com.example.chess_masters.game.Direction;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;

public class BishopPiece extends Piece {
	public BishopPiece(PieceColor paramPieceColor, Position paramPosition) {
		super(paramPieceColor, paramPosition);
		this.attackDirections.add(Direction.SOUTH_EAST);
		this.attackDirections.add(Direction.SOUTH_WEST);
		this.attackDirections.add(Direction.NORTH_EAST);
		this.attackDirections.add(Direction.NORTH_WEST);
	}

	public boolean canAttackInDirection(Direction paramDirection) {
		return this.attackDirections.contains(paramDirection);
	}
}