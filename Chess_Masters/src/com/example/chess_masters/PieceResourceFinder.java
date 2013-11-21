package com.example.chess_masters;

import android.graphics.Color;

import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.pieces.BishopPiece;
import com.example.chess_masters.pieces.KingPiece;
import com.example.chess_masters.pieces.KnightPiece;
import com.example.chess_masters.pieces.PawnPiece;
import com.example.chess_masters.pieces.Piece;
import com.example.chess_masters.pieces.QueenPiece;
import com.example.chess_masters.pieces.RookPiece;

public class PieceResourceFinder {
	private static int[] blackPiecesResources;
	private static String[] names;
	private static int[] whitePiecesResources;

	static {
		initiliazeResources();
	}

	public static int getResource(Piece piece) {
		if (piece == null) {
			return Color.TRANSPARENT;
		}

		String type = piece.getClass().getSimpleName();
		if (piece.getPieceColor() == PieceColor.BLACK_COLOR) {
			for (int i = 0; i < blackPiecesResources.length; i++) {
				if (names[i].equals(type)) {
					return blackPiecesResources[i];
				}
			}
		} else {
			for (int i = 0; i < whitePiecesResources.length; i++) {
				if (names[i].equals(type)) {
					return whitePiecesResources[i];
				}
			}
		}

		return Color.TRANSPARENT;
	}

	private static void initiliazeResources() {
		names = new String[6];
		names[0] = PawnPiece.class.getSimpleName();
		names[1] = KnightPiece.class.getSimpleName();
		names[2] = BishopPiece.class.getSimpleName();
		names[3] = RookPiece.class.getSimpleName();
		names[4] = QueenPiece.class.getSimpleName();
		names[5] = KingPiece.class.getSimpleName();
		whitePiecesResources = new int[] { R.drawable.whitepawn,
				R.drawable.whitehorse, R.drawable.whitebishop,
				R.drawable.whiterook, R.drawable.whitequeen,
				R.drawable.whiteking };
		blackPiecesResources = new int[] { R.drawable.blackpawn,
				R.drawable.blackknight, R.drawable.blackbishop,
				R.drawable.blackrook, R.drawable.blackqueen,
				R.drawable.blackking };
	}
}