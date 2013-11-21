package com.example.chess_masters;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import com.example.chess_masters.db.DbHelper;
import com.example.chess_masters.db.PiecesDb;

public class ChessMastersApplication extends Application
{
  private SQLiteDatabase db = null;
  private PiecesDb piecessDb = null;

  public SQLiteDatabase getDb()
  {
    if (this.db == null)
      this.db = new DbHelper(getApplicationContext()).open();
    return this.db;
  }

  public PiecesDb getPiecesDb()
  {
    if (this.piecessDb == null)
      this.piecessDb = new PiecesDb(getDb());
    return this.piecessDb;
  }
}

/* Location:           C:\Users\spind\Desktop\ChessMasters_dex2jar.jar
 * Qualified Name:     com.example.chess_masters.ChessMastersApplication
 * JD-Core Version:    0.6.0
 */