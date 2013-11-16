package com.example.chess_masters;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Piece {
	private PieceColor pieceColor;
	private boolean selected;
	private Position position;
	protected ArrayList<Direction> attackDIrections;
	private boolean hasMoved;

	public Piece(PieceColor color, Position position) {
		this.pieceColor = color;
		this.selected = false;
		this.setPosition(position);
		this.attackDIrections = new ArrayList<Direction>();
		this.hasMoved = false;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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
