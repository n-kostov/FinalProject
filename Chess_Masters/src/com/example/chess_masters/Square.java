package com.example.chess_masters;

public class Square {
	private Piece piece;
//	private Position position;
	private boolean selected;
	private boolean attacked;

	public Square(Piece piece) {
		this.setPiece(piece);
//		this.position = position;
		this.selected = false;
		this.attacked = false;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

//	public Position getPosition() {
//		return position;
//	}
//
//	public void setPosition(Position position) {
//		this.position = position;
//	}

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
