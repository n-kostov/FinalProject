package com.example.chess_masters.pieces;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.chess_masters.game.Direction;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;

public abstract class Piece {
	private PieceColor pieceColor;
	private Position position;
	protected ArrayList<Direction> attackDIrections;
	private boolean hasMoved;

	public Piece(PieceColor color, Position position) {
		this.pieceColor = color;
		this.setPosition(position);
		this.attackDIrections = new ArrayList<Direction>();
		this.hasMoved = false;
	}

	public PieceColor getPieceColor() {
		return pieceColor;
	}

	public void setPieceColor(PieceColor pieceColor) {
		this.pieceColor = pieceColor;
	}

	public abstract Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position currentPosition);

	public abstract boolean canAttackInDirection(Direction direction);

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean isHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
}
