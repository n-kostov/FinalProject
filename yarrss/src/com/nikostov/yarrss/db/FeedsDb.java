package com.nikostov.yarrss.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class FeedsDb {
	private SQLiteDatabase db;
	private static final String TABLE_NAME = "feeds";
	
	public FeedsDb(SQLiteDatabase db) {
		this.db = db;
	}
	
	public void AddUrl(String url) throws NullPointerException {
		if(url == null) {
			throw new NullPointerException();
		}
		
		String trimmedUrl = url.trim();
		ContentValues values = new ContentValues();
		values.put("feed_url", trimmedUrl);
		this.db.insert(TABLE_NAME, null, values);
	}
}
