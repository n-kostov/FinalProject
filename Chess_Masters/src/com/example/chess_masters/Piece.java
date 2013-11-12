package com.example.chess_masters;

import java.util.ArrayList;

public abstract class Piece {
	private PieceColor pieceColor;
	private int resource;
	private boolean selected;

	public Piece(PieceColor color, int resource) {
		this.pieceColor = color;
		this.resource = resource;
		this.selected = false;
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

	public abstract ArrayList<Position> possibleMoves(Position currentPosition);
}
