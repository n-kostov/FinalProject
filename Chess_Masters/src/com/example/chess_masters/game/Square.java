package com.example.chess_masters.game;

import com.example.chess_masters.pieces.Piece;

public class Square {
	private Piece piece;
	private boolean selected;
	private boolean attacked;

	public Square(Piece piece) {
		this.setPiece(piece);
		this.selected = false;
		this.attacked = false;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
}
