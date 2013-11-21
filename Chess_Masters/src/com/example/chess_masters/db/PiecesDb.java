package com.example.chess_masters.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.example.chess_masters.pieces.Piece;

public class PiecesDb {
	private static final String TABLE_NAME = "pieces";
	private SQLiteDatabase db;

	public PiecesDb(SQLiteDatabase paramSQLiteDatabase) {
		this.db = paramSQLiteDatabase;
	}

	public void AddPiece(Piece piece) {
		if (piece == null) {
			throw new SQLiteException("The piece cannot be null!");
		}

		ContentValues values = new ContentValues();
		values.put("color", piece.getPieceColor().toString());
		values.put("x", piece.getPosition().getX());
		values.put("y", piece.getPosition().getY());
		String type = piece.getClass().getSimpleName();
		Cursor typeCursor = this.db.rawQuery(
				"select _id from piece_types where type = ?",
				new String[] { type });
		typeCursor.moveToFirst();
		values.put("piece_type_id",
				typeCursor.getInt(typeCursor.getColumnIndex("_id")));
		this.db.insert(TABLE_NAME, null, values);
	}

	public void deletePieces() {
		this.db.execSQL("delete from pieces");
	}

	public Cursor getPieces() {
		return this.db
				.rawQuery(
						"select p._id, p.color, p.x, p.y, t.type from pieces p, piece_types t where p.piece_type_id = t._id",
						null);
	}

	public boolean hasAnyRecords() {
		Cursor cursor = this.db.rawQuery("select count(*) from pieces", null);
		cursor.moveToFirst();
		return cursor.getInt(0) > 0;
	}
}