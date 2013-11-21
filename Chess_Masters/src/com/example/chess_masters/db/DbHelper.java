package com.example.chess_masters.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.chess_masters.pieces.BishopPiece;
import com.example.chess_masters.pieces.KingPiece;
import com.example.chess_masters.pieces.KnightPiece;
import com.example.chess_masters.pieces.PawnPiece;
import com.example.chess_masters.pieces.QueenPiece;
import com.example.chess_masters.pieces.RookPiece;

public class DbHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "CHESS_MASTERS";
	private static final int DB_VERSION = 1;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public void close() {
		close();
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
		paramSQLiteDatabase
				.execSQL("create table piece_types (_id integer primary key autoincrement, type text not null);");
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("type", PawnPiece.class.getSimpleName());
		paramSQLiteDatabase.insert("piece_types", null, localContentValues);
		localContentValues.put("type", KnightPiece.class.getSimpleName());
		paramSQLiteDatabase.insert("piece_types", null, localContentValues);
		localContentValues.put("type", BishopPiece.class.getSimpleName());
		paramSQLiteDatabase.insert("piece_types", null, localContentValues);
		localContentValues.put("type", RookPiece.class.getSimpleName());
		paramSQLiteDatabase.insert("piece_types", null, localContentValues);
		localContentValues.put("type", QueenPiece.class.getSimpleName());
		paramSQLiteDatabase.insert("piece_types", null, localContentValues);
		localContentValues.put("type", KingPiece.class.getSimpleName());
		paramSQLiteDatabase.insert("piece_types", null, localContentValues);
		paramSQLiteDatabase
				.execSQL("create table pieces (_id integer primary key autoincrement, color text not null, x integer not null, y integer not null, piece_type_id integer not null constraint 'FK_Pieces_Types' references 'pieces_types'('_id'));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

	public SQLiteDatabase open() {
		return getWritableDatabase();
	}
}