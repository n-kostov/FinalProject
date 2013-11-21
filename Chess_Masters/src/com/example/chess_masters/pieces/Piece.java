package com.example.chess_masters.pieces;

import com.example.chess_masters.game.Direction;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;
import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Piece {
	protected ArrayList<Direction> attackDirections;
	private boolean hasMoved;
	private PieceColor pieceColor;
	private Position position;

	public Piece(PieceColor paramPieceColor, Position paramPosition) {
		this.pieceColor = paramPieceColor;
		setPosition(paramPosition);
		this.attackDirections = new ArrayList<Direction>();
		this.hasMoved = false;
	}

	public abstract boolean canAttackInDirection(Direction paramDirection);

	public PieceColor getPieceColor() {
		return this.pieceColor;
	}

	public Position getPosition() {
		return this.position;
	}

	public boolean isHasMoved() {
		return this.hasMoved;
	}

	public Hashtable<Direction, ArrayList<Position>> possibleMoves(
			Position paramPosition) {
		Hashtable<Direction, ArrayList<Position>> moves = new Hashtable<Direction, ArrayList<Position>>();
		for (int i = 0; i < this.attackDirections.size(); i++) {
			ArrayList<Position> movesInDirection = new ArrayList<Position>();
			for (int j = 1; j < 8; j++) {
				Position positionInDirection = position.getPositionInDirection(
						this.attackDirections.get(i), j);
				if (!positionInDirection.isValid()) {
					break;
				}

				movesInDirection.add(positionInDirection);
			}

			moves.put(this.attackDirections.get(i), movesInDirection);
		}

		return moves;
	}

	public void setHasMoved(boolean paramBoolean) {
		this.hasMoved = paramBoolean;
	}

	public void setPieceColor(PieceColor paramPieceColor) {
		this.pieceColor = paramPieceColor;
	}

	public void setPosition(Position paramPosition) {
		this.position = paramPosition;
	}
}