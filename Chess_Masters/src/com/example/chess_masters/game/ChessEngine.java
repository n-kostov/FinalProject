package com.example.chess_masters.game;

import com.example.chess_masters.pieces.BishopPiece;
import com.example.chess_masters.pieces.KingPiece;
import com.example.chess_masters.pieces.KnightPiece;
import com.example.chess_masters.pieces.PawnPiece;
import com.example.chess_masters.pieces.Piece;
import com.example.chess_masters.pieces.QueenPiece;
import com.example.chess_masters.pieces.RookPiece;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class ChessEngine {
	private Piece blackKing;
	private Position eastCastlePosition;
	private Position enPassantPosition;
	private PieceColor inTurn;
	private boolean isUnderPromotion = false;
	private ArrayList<Position> possibleMoves;
	private Piece selectedPiece;
	private ArrayList<Square> squares;
	private Position westCastlePosition;
	private Piece whiteKing;

	public ChessEngine() {
		this.inTurn = PieceColor.WHITE_COLOR;
		this.squares = new ArrayList<Square>(64);
		this.possibleMoves = new ArrayList<Position>(64);
		initializeGame();
	}

	public ChessEngine(PieceColor paramPieceColor, ArrayList<Piece> pieces) {
		this.inTurn = paramPieceColor;
		this.squares = new ArrayList<Square>(64);
		this.possibleMoves = new ArrayList<Position>(64);
		reloadGame(pieces);
	}

	private void addPawnAttackingPosition(Piece piece, Direction direction,
			ArrayList<Position> moves) {
		if (piece instanceof PawnPiece) {
			Position position = piece.getPosition().getPositionInDirection(
					direction, 1);
			if (position.isValid()) {
				Piece attackedPiece = this.squares.get(
						8 * position.getX() + position.getY()).getPiece();
				if (attackedPiece != null
						&& attackedPiece.getPieceColor() != piece
								.getPieceColor()) {
					moves.add(position);
				}
			}
		}
	}

	private void addPawnPositions(Piece piece, ArrayList<Position> moves) {
		if (piece instanceof PawnPiece) {
			Direction westAttackingDirection = Direction.NORTH_WEST;
			Direction eastAttackingDirection = Direction.NORTH_EAST;
			if (piece.getPieceColor() == PieceColor.BLACK_COLOR) {
				westAttackingDirection = Direction.SOUTH_WEST;
				eastAttackingDirection = Direction.SOUTH_EAST;
			}

			addPawnAttackingPosition(piece, westAttackingDirection, moves);
			addPawnAttackingPosition(piece, eastAttackingDirection, moves);

			// check for special move en passant
			if (this.enPassantPosition != null
					&& (this.enPassantPosition.equals(piece.getPosition()
							.getPositionInDirection(westAttackingDirection, 1)) || this.enPassantPosition
							.equals(piece.getPosition().getPositionInDirection(
									eastAttackingDirection, 1)))) {
				moves.add(this.enPassantPosition);
			}
		}
	}

	private void changeTurn() {
		if (this.inTurn == PieceColor.BLACK_COLOR) {
			this.inTurn = PieceColor.WHITE_COLOR;
		} else {
			this.inTurn = PieceColor.BLACK_COLOR;
		}
	}

	private void checkCastlePositions(Piece piece, ArrayList<Position> moves) {
		if (piece instanceof KingPiece && !piece.isHasMoved()
				&& !hasAttackers(piece)) {
			this.westCastlePosition = getCastlePosition(piece, Direction.WEST);
			if (this.westCastlePosition != null) {
				moves.add(this.westCastlePosition);
			}

			this.eastCastlePosition = getCastlePosition(piece, Direction.EAST);
			if (this.eastCastlePosition != null) {
				moves.add(this.eastCastlePosition);
			}
		}
	}

	private ResponseMessage checkGameState() {
		Piece kingToCheck = this.whiteKing;
		if (this.inTurn == PieceColor.BLACK_COLOR) {
			kingToCheck = this.blackKing;
		}

		ArrayList<Position> moves = new ArrayList<Position>();
		boolean hasAttackers = hasAttackers(kingToCheck);
		getPieceMoves(kingToCheck, moves);
		if (hasAttackers) {
			if (moves.size() > 0 || hasPiecesToMove()) {
				return new ResponseMessage(GameState.CHECK, this.inTurn);
			} else {
				return new ResponseMessage(GameState.CHECKMATE, this.inTurn);
			}
		} else {
			if (moves.size() == 0 && !hasPiecesToMove()) {
				return new ResponseMessage(GameState.STALEMATE, this.inTurn);
			}
		}

		return null;
	}

	private Position getCastlePosition(Piece kingPiece, Direction direction) {
		int offset;
		if (direction == Direction.EAST) {
			offset = 3;
		} else if (direction == Direction.WEST) {
			offset = 4;
		} else {
			return null;
		}

		for (int i = 1; i <= offset; i++) {
			Position position = kingPiece.getPosition().getPositionInDirection(
					direction, i);
			Square square = this.squares.get(8 * position.getX()
					+ position.getY());
			if (i < offset) {
				if (square.getPiece() != null) {
					return null;
				}

				PawnPiece dummyPiece = new PawnPiece(kingPiece.getPieceColor(),
						position);
				square.setPiece(dummyPiece);
				if (hasAttackers(dummyPiece)) {
					square.setPiece(null);
					return null;
				}

				square.setPiece(null);
			} else {
				Piece piece = this.squares.get(
						8 * position.getX() + position.getY()).getPiece();
				if (piece != null && (piece instanceof RookPiece)
						&& !piece.isHasMoved()
						&& piece.getPieceColor() == kingPiece.getPieceColor()
						&& !hasAttackers(piece)) {
					return kingPiece.getPosition().getPositionInDirection(
							direction, 2);
				}
			}
		}

		return null;
	}

	private void getPieceMoves(Piece piece, ArrayList<Position> moves) {
		Hashtable<Direction, ArrayList<Position>> possibleMoves = piece
				.possibleMoves(piece.getPosition());
		Enumeration<Direction> directions = possibleMoves.keys();
		while (directions.hasMoreElements()) {
			Direction currentDirection = directions.nextElement();
			ArrayList<Position> movesInDirection = possibleMoves
					.get(currentDirection);
			for (int i = 0; i < movesInDirection.size(); i++) {
				Position currentPosition = movesInDirection.get(i);
				Piece currentPositionPiece = this.squares.get(
						8 * currentPosition.getX() + currentPosition.getY())
						.getPiece();
				if (currentPositionPiece != null) {
					if (currentPositionPiece.getPieceColor() != piece
							.getPieceColor() && !(piece instanceof PawnPiece)) {
						moves.add(currentPosition);

					}

					if (currentDirection == Direction.KNIGHT) {
						continue;
					}

					break;
				}

				moves.add(currentPosition);
			}
		}

		if (piece instanceof PawnPiece) {
			addPawnPositions(piece, moves);
		} else if (piece instanceof KingPiece) {
			checkCastlePositions(piece, moves);
		}

		this.removeImpossibleMoves(piece, moves);
	}

	private boolean hasAttackers(Piece piece) {
		Position piecePosition = piece.getPosition();
		Direction[] directions = { Direction.NORTH_WEST, Direction.NORTH_EAST,
				Direction.SOUTH_WEST, Direction.SOUTH_EAST, Direction.NORTH,
				Direction.EAST, Direction.SOUTH, Direction.WEST };
		for (int i = 0; i < directions.length; i++) {
			for (int j = 1; j < 8; j++) {
				Position currentPosition = piecePosition
						.getPositionInDirection(directions[i], j);
				if (!currentPosition.isValid()) {
					break;
				}

				Piece currentPositionPiece = this.squares.get(
						8 * currentPosition.getX() + currentPosition.getY())
						.getPiece();
				if (currentPositionPiece != null) {
					if (currentPositionPiece.getPieceColor() != piece
							.getPieceColor()
							&& currentPositionPiece
									.canAttackInDirection(directions[i]
											.getOppositeDirection())) {
						if ((currentPositionPiece instanceof PawnPiece || currentPositionPiece instanceof KingPiece)
								&& j > 1) {
							break;
						}

						return true;
					} else {
						break;
					}
				}
			}
		}

		int[] dx = new int[] { 1, 2, 2, 1, -1, -2, -2, -1 };
		int[] dy = new int[] { 2, 1, -1, -2, -2, -1, 1, 2 };
		for (int i = 0; i < dx.length; i++) {
			Position currentPosition = new Position(piece.getPosition().getX()
					+ dx[i], piece.getPosition().getY() + dy[i]);
			if (!currentPosition.isValid()) {
				continue;
			}

			Piece currentPositionPiece = this.squares.get(
					8 * currentPosition.getX() + currentPosition.getY())
					.getPiece();
			if (currentPositionPiece != null
					&& currentPositionPiece.getPieceColor() != piece
							.getPieceColor()
					&& currentPositionPiece
							.canAttackInDirection(Direction.KNIGHT)) {
				return true;
			}
		}

		return false;
	}

	private boolean hasPiecesToMove() {
		for (int i = 0; i < this.squares.size(); i++) {
			Piece piece = this.squares.get(i).getPiece();
			if ((piece == null) || ((piece instanceof KingPiece))
					|| (piece.getPieceColor() != this.inTurn)) {
				continue;
			}

			ArrayList<Position> moves = new ArrayList<Position>();
			getPieceMoves(piece, moves);
			if (moves.size() > 0) {
				return true;
			}
		}

		return false;
	}

	private void initializeGame() {
		this.squares.add(0, new Square(new RookPiece(PieceColor.BLACK_COLOR,
				new Position(0, 0))));
		this.squares.add(1, new Square(new KnightPiece(PieceColor.BLACK_COLOR,
				new Position(0, 1))));
		this.squares.add(2, new Square(new BishopPiece(PieceColor.BLACK_COLOR,
				new Position(0, 2))));
		this.squares.add(3, new Square(new QueenPiece(PieceColor.BLACK_COLOR,
				new Position(0, 3))));
		this.blackKing = new KingPiece(PieceColor.BLACK_COLOR, new Position(0,
				4));
		this.squares.add(4, new Square(this.blackKing));
		this.squares.add(5, new Square(new BishopPiece(PieceColor.BLACK_COLOR,
				new Position(0, 5))));
		this.squares.add(6, new Square(new KnightPiece(PieceColor.BLACK_COLOR,
				new Position(0, 6))));
		this.squares.add(7, new Square(new RookPiece(PieceColor.BLACK_COLOR,
				new Position(0, 7))));
		for (int i = 8; i < 16; i++) {
			this.squares.add(i, new Square(new PawnPiece(
					PieceColor.BLACK_COLOR, new Position(1, i % 8))));
		}

		for (int i = 16; i < 48; i++) {
			this.squares.add(i, new Square(null));
		}

		for (int i = 48; i < 56; i++) {
			this.squares.add(i, new Square(new PawnPiece(
					PieceColor.WHITE_COLOR, new Position(6, i % 8))));
		}

		this.squares.add(56, new Square(new RookPiece(PieceColor.WHITE_COLOR,
				new Position(7, 0))));
		this.squares.add(57, new Square(new KnightPiece(PieceColor.WHITE_COLOR,
				new Position(7, 1))));
		this.squares.add(58, new Square(new BishopPiece(PieceColor.WHITE_COLOR,
				new Position(7, 2))));
		this.squares.add(59, new Square(new QueenPiece(PieceColor.WHITE_COLOR,
				new Position(7, 3))));
		this.whiteKing = new KingPiece(PieceColor.WHITE_COLOR, new Position(7,
				4));
		this.squares.add(60, new Square(this.whiteKing));
		this.squares.add(61, new Square(new BishopPiece(PieceColor.WHITE_COLOR,
				new Position(7, 5))));
		this.squares.add(62, new Square(new KnightPiece(PieceColor.WHITE_COLOR,
				new Position(7, 6))));
		this.squares.add(63, new Square(new RookPiece(PieceColor.WHITE_COLOR,
				new Position(7, 7))));

	}

	private ResponseMessage movePiece(Position positionToMove) {

		this.squares.get(
				8 * this.selectedPiece.getPosition().getX()
						+ this.selectedPiece.getPosition().getY()).setSelected(
				false);
		ResponseMessage responseMessage;
		if (!this.possibleMoves.contains(positionToMove)) {
			responseMessage = new ResponseMessage(GameState.INVALID_MOVE,
					this.inTurn);
		} else {
			performMove(positionToMove);
			if (this.isUnderPromotion) {
				return new ResponseMessage(GameState.PROMOTION, this.inTurn);
			}

			changeTurn();
			responseMessage = checkGameState();
		}

		selectAttackedSquares();
		this.selectedPiece = null;
		this.possibleMoves.clear();
		return responseMessage;
	}

	private void performCastleMove(Position positionToMove) {
		if (positionToMove.getY() == -2
				+ this.selectedPiece.getPosition().getY()) {
			Square rookSquare = this.squares
					.get(-2
							+ (8 * this.westCastlePosition.getX() + this.westCastlePosition
									.getY()));
			Piece rookPiece = rookSquare.getPiece();
			rookSquare.setPiece(null);
			rookPiece.setHasMoved(true);
			rookPiece.setPosition(this.westCastlePosition
					.getPositionInDirection(Direction.EAST, 1));
			Square destinationSquare = this.squares.get(8
					* rookPiece.getPosition().getX()
					+ rookPiece.getPosition().getY());
			destinationSquare.setSelected(false);
			destinationSquare.setPiece(rookPiece);
			this.westCastlePosition = null;
			this.eastCastlePosition = null;
		} else if (positionToMove.getY() == 2 + this.selectedPiece
				.getPosition().getY()) {
			Square rookSquare = this.squares
					.get(1 + (8 * this.eastCastlePosition.getX() + this.eastCastlePosition
							.getY()));
			Piece rookPiece = rookSquare.getPiece();
			rookSquare.setPiece(null);
			rookPiece.setHasMoved(true);
			rookPiece.setPosition(this.eastCastlePosition
					.getPositionInDirection(Direction.WEST, 1));
			Square destinationSquare = this.squares.get(8
					* rookPiece.getPosition().getX()
					+ rookPiece.getPosition().getY());
			destinationSquare.setSelected(false);
			destinationSquare.setPiece(rookPiece);
			this.westCastlePosition = null;
			this.eastCastlePosition = null;
		}
	}

	private void performMove(Position positionToMove) {
		this.squares.get(
				8 * this.selectedPiece.getPosition().getX()
						+ this.selectedPiece.getPosition().getY()).setPiece(
				null);
		this.selectedPiece.setHasMoved(true);
		boolean possibleEnPassant = this.enPassantPosition == null;
		if (this.selectedPiece instanceof PawnPiece) {
			performPawnMove(positionToMove);
			possibleEnPassant = possibleEnPassant
					&& this.enPassantPosition != null;
		} else if (this.selectedPiece instanceof KingPiece
				&& (this.westCastlePosition != null || this.eastCastlePosition != null)) {
			performCastleMove(positionToMove);
		}

		this.selectedPiece.setPosition(positionToMove);
		this.squares.get(8 * positionToMove.getX() + positionToMove.getY())
				.setPiece(this.selectedPiece);
		if (!possibleEnPassant) {
			this.enPassantPosition = null;
		}
	}

	private void performPawnMove(Position positionToMove) {
		if (this.selectedPiece instanceof PawnPiece) {
			PieceColor selectedPieceColor = this.selectedPiece.getPieceColor();
			if ((selectedPieceColor == PieceColor.BLACK_COLOR && positionToMove
					.getX() == 7)
					|| (selectedPieceColor == PieceColor.WHITE_COLOR && positionToMove
							.getX() == 0)) {
				this.isUnderPromotion = true;
			} else if (Math.abs(this.selectedPiece.getPosition().getX()
					- positionToMove.getX()) == 2) {
				Piece westPiece = this.squares.get(
						-1
								+ (8 * positionToMove.getX() + positionToMove
										.getY())).getPiece();
				Piece eastPiece = this.squares
						.get(1 + (8 * positionToMove.getX() + positionToMove
								.getY())).getPiece();
				if ((-1 + positionToMove.getY() >= 0 && westPiece != null && westPiece
						.getPieceColor() != selectedPieceColor)
						|| (1 + positionToMove.getY() <= 7 && eastPiece != null && eastPiece
								.getPieceColor() != selectedPieceColor)) {
					if (selectedPieceColor == PieceColor.BLACK_COLOR) {
						this.enPassantPosition = positionToMove
								.getPositionInDirection(Direction.NORTH, 1);
					} else {
						this.enPassantPosition = positionToMove
								.getPositionInDirection(Direction.SOUTH, 1);
					}
				}
			}

			if (this.enPassantPosition != null
					&& this.enPassantPosition.equals(positionToMove)) {
				int offset = 1;
				if (selectedPieceColor == PieceColor.BLACK_COLOR) {
					offset = -1;
				}

				this.squares.get(
						8 * (offset + this.enPassantPosition.getX())
								+ this.enPassantPosition.getY()).setPiece(null);
			}
		}
	}

	private void reloadGame(ArrayList<Piece> pieces) {
		for (int i = 0; i < 64; i++) {
			this.squares.add(i, new Square(null));
		}

		for (int i = 0; i < pieces.size(); i++) {
			Piece piece = pieces.get(i);
			if (piece != null) {
				Position position = piece.getPosition();
				if (piece instanceof KingPiece) {
					if (piece.getPieceColor() == PieceColor.BLACK_COLOR) {
						this.blackKing = piece;
					} else {
						this.whiteKing = piece;
					}
				}

				this.squares.get(8 * position.getX() + position.getY())
						.setPiece(piece);
			}
		}

		this.checkForPromotion();
	}

	private void checkForPromotion() {
		for (int i = 0; i < 8; i++) {
			Piece piece = this.squares.get(i).getPiece();
			if (piece instanceof PawnPiece) {
				this.selectedPiece = piece;
				this.isUnderPromotion = true;
				return;
			}
		}

		for (int i = 56; i < 64; i++) {
			Piece piece = this.squares.get(i).getPiece();
			if (piece instanceof PawnPiece) {
				this.selectedPiece = piece;
				this.isUnderPromotion = true;
				return;
			}
		}
	}

	private void removeImpossibleMoves(Piece piece, ArrayList<Position> moves) {
		if (moves.size() == 0 || piece == null) {
			return;
		}

		Position currentPiecePosition = piece.getPosition();
		Square currentPieceSquare = this.squares.get(8
				* currentPiecePosition.getX() + currentPiecePosition.getY());
		currentPieceSquare.setPiece(null);
		Piece currentPieceKing = this.blackKing;
		if (piece.getPieceColor() == PieceColor.WHITE_COLOR) {
			currentPieceKing = this.whiteKing;
		}

		ArrayList<Position> impossibleMoves = new ArrayList<Position>();
		for (int i = 0; i < moves.size(); i++) {
			piece.setPosition(moves.get(i));
			Square destination = this.squares.get(8 * moves.get(i).getX()
					+ moves.get(i).getY());
			Piece destinationPiece = destination.getPiece();
			destination.setPiece(piece);
			if (this.hasAttackers(currentPieceKing)) {
				impossibleMoves.add(moves.get(i));
			}

			destination.setPiece(destinationPiece);
		}

		piece.setPosition(currentPiecePosition);
		currentPieceSquare.setPiece(piece);
		moves.removeAll(impossibleMoves);
	}

	private void selectAttackedSquares() {
		for (int i = 0; i < this.possibleMoves.size(); i++) {
			Position position = this.possibleMoves.get(i);
			Square square = this.squares.get(8 * position.getX()
					+ position.getY());
			square.setAttacked(!square.isAttacked());
		}
	}

	private ResponseMessage selectPiece(Piece piece) {
		if (piece.getPieceColor() != this.inTurn) {
			return new ResponseMessage(GameState.NOT_YOUR_TURN, this.inTurn);
		}

		getPieceMoves(piece, this.possibleMoves);
		if (this.possibleMoves.size() > 0) {
			selectAttackedSquares();
			Square square = this.squares.get(8 * piece.getPosition().getX()
					+ piece.getPosition().getY());

			square.setSelected(true);
			this.selectedPiece = piece;
			return null;
		}

		return new ResponseMessage(GameState.NO_VALID_MOVES, this.inTurn);
	}

	public ArrayList<Square> getChessSquares() {
		return this.squares;
	}

	public PieceColor getInTurn() {
		return this.inTurn;
	}

	public ResponseMessage move(Position positionToMove) {
		if (this.isUnderPromotion) {
			return new ResponseMessage(GameState.PROMOTION, this.inTurn);
		}

		if (this.selectedPiece == null) {
			Piece localPiece = this.squares.get(
					8 * positionToMove.getX() + positionToMove.getY())
					.getPiece();
			if (localPiece == null) {
				return null;
			}

			return selectPiece(localPiece);
		} else {
			return movePiece(positionToMove);
		}
	}

	public ResponseMessage promote(Piece piece) {
		if ((piece == null)
				|| (!this.isUnderPromotion)
				|| (piece.getPieceColor() != this.selectedPiece.getPieceColor())) {
			return null;
		}

		Position position = this.selectedPiece.getPosition();
		this.selectedPiece = piece;
		this.selectedPiece.setPosition(position);
		this.squares.get(8 * position.getX() + position.getY()).setPiece(
				this.selectedPiece);
		changeTurn();
		ResponseMessage responseMessage = checkGameState();
		this.squares.get(
				8 * this.selectedPiece.getPosition().getX()
						+ this.selectedPiece.getPosition().getY()).setSelected(
				false);
		this.selectedPiece = null;
		selectAttackedSquares();
		this.possibleMoves.clear();
		this.isUnderPromotion = false;
		return responseMessage;
	}
}