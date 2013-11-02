package com.nikostov.yarrss;

import com.nikostov.yarrss.db.DbHelper;
import com.nikostov.yarrss.db.FeedsDb;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class YarssApplication extends Application {
	private SQLiteDatabase db = null;
	private FeedsDb feedsDb = null;
	
	public SQLiteDatabase getDb() {
		if(this.db == null) {
			this.db = new DbHelper(getApplicationContext()).open();
		}
		
		return this.db;
	}
	
	public FeedsDb getFeedsDb() {
		if(this.feedsDb == null) {
			this.feedsDb = new FeedsDb(getDb());
		}
		
		return this.feedsDb;
	}
}
