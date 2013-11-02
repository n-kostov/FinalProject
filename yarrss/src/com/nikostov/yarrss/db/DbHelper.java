package com.nikostov.yarrss.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "yarss";
	private static final int DB_VERSION = 1;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table feeds "
				+ "(_id integer primary key autoincrement, feed_url text not null);");
		db.execSQL("create table items "
				+ "(_id integer primary key autoincrement, title text not null, "
				+ "description text not null, link text not null, guid text not null, date_time text not null);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public SQLiteDatabase open() {
		return getWritableDatabase();
	}

	public void close() {
		close();
	}
}
