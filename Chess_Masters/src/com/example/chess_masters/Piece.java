package com.example.chess_masters;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Piece {
	private PieceColor pieceColor;
	private int resource;
	private boolean selected;
	private Position position;

	public Piece(PieceColor color, int resource, Position position) {
		this.pieceColor = color;
		this.resource = resource;
		this.selected = false;
		this.setPosition(position);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getResource() {
		return resource;
	}

	public PieceColor getPieceColor() {
		return pieceColor;
	}

	public void setPieceColor(PieceColor pieceColor) {
		this.pieceColor = pieceColor;
	}

	public abstract Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position currentPosition);

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
