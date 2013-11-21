package com.example.chess_masters;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.example.chess_masters.adapters.ChessboardAdapter;
import com.example.chess_masters.adapters.PromotionAdapter;
import com.example.chess_masters.db.PiecesDb;
import com.example.chess_masters.game.ChessEngine;
import com.example.chess_masters.game.GameState;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;
import com.example.chess_masters.game.ResponseMessage;
import com.example.chess_masters.game.Square;
import com.example.chess_masters.pieces.BishopPiece;
import com.example.chess_masters.pieces.KingPiece;
import com.example.chess_masters.pieces.KnightPiece;
import com.example.chess_masters.pieces.PawnPiece;
import com.example.chess_masters.pieces.Piece;
import com.example.chess_masters.pieces.QueenPiece;
import com.example.chess_masters.pieces.RookPiece;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ChessEngine engine;
	private ArrayAdapter<Square> chessboardAdapter;
	private boolean gameInProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (getIntent().hasExtra("continue")) {
			SharedPreferences pref = PreferenceManager
					.getDefaultSharedPreferences(this);
			PieceColor inTurn = null;
			String prefTurn = pref.getString("inTurn", null);
			if (prefTurn.equals(PieceColor.BLACK_COLOR.toString())) {
				inTurn = PieceColor.BLACK_COLOR;
			} else if (prefTurn.equals(PieceColor.WHITE_COLOR.toString())) {
				inTurn = PieceColor.WHITE_COLOR;
			}

			ArrayList<Piece> pieces = new ArrayList<Piece>();
			reloadGame(pieces);
			engine = new ChessEngine(inTurn, pieces);
			gameInProgress = true;
		} else {
			engine = new ChessEngine();
			gameInProgress = false;
		}

		chessboardAdapter = new ChessboardAdapter(this, R.layout.chess_square,
				engine.getChessSquares());
		GridView grid = (GridView) findViewById(R.id.chessboard_gridview);
		grid.setAdapter(chessboardAdapter);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ResponseMessage message = engine.move(new Position(arg2 / 8,
						arg2 % 8));
				handleResponseMessage(message);
				chessboardAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		PiecesDb db = ((ChessMastersApplication) getApplication())
				.getPiecesDb();
		if (gameInProgress) {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("inTurn", engine.getInTurn().toString());
			editor.commit();

			if (db.hasAnyRecords()) {
				db.deletePieces();
			}

			ArrayList<Square> squares = engine.getChessSquares();
			for (int i = 0; i < squares.size(); i++) {
				Piece piece = squares.get(i).getPiece();
				if (piece != null) {
					db.AddPiece(piece);
				}
			}
		} else {
			db.deletePieces();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.draw) {
			handleDraw();
		}

		return true;
	}

	private void reloadGame(ArrayList<Piece> pieces) {

		PiecesDb db = ((ChessMastersApplication) getApplication())
				.getPiecesDb();

		Cursor c = db.getPieces();
		if (c.moveToFirst()) {
			String[] classNames = { PawnPiece.class.getSimpleName(),
					KnightPiece.class.getSimpleName(),
					BishopPiece.class.getSimpleName(),
					RookPiece.class.getSimpleName(),
					QueenPiece.class.getSimpleName(),
					KingPiece.class.getSimpleName() };
			do {
				String pieceColorAsString = c.getString(c
						.getColumnIndex("color"));
				PieceColor pieceColor = null;
				if (pieceColorAsString
						.equals(PieceColor.BLACK_COLOR.toString())) {
					pieceColor = PieceColor.BLACK_COLOR;
				} else if (pieceColorAsString.equals(PieceColor.WHITE_COLOR
						.toString())) {
					pieceColor = PieceColor.WHITE_COLOR;
				}

				int x = c.getInt(c.getColumnIndex("x"));
				int y = c.getInt(c.getColumnIndex("y"));
				Position position = new Position(x, y);

				String type = c.getString(c.getColumnIndex("type"));
				Piece piece = null;
				if (type.equals(classNames[0])) {
					piece = new PawnPiece(pieceColor, position);
				} else if (type.equals(classNames[1])) {
					piece = new KnightPiece(pieceColor, position);
				} else if (type.equals(classNames[2])) {
					piece = new BishopPiece(pieceColor, position);
				} else if (type.equals(classNames[3])) {
					piece = new RookPiece(pieceColor, position);
				} else if (type.equals(classNames[4])) {
					piece = new QueenPiece(pieceColor, position);
				} else if (type.equals(classNames[5])) {
					piece = new KingPiece(pieceColor, position);
				}

				pieces.add(piece);

			} while (c.moveToNext());
		}

	}

	private void handleResponseMessage(ResponseMessage message) {
		if (message != null) {
			String msg = null;
			boolean gameOver = false;
			switch (message.getState()) {
			case CHECK:
				msg = "Check!";
				break;
			case CHECKMATE:
				msg = "Checkmate! " + message.getColor() + " wins!";
				gameOver = true;
				gameInProgress = false;
				break;
			case DRAW:
				msg = "Draw!";
				gameOver = true;
				gameInProgress = false;
				break;
			case INVALID_MOVE:
				msg = "Invalid move";
				break;
			case NO_VALID_MOVES:
				msg = "This piece does not have valid moves";
				break;
			case NOT_YOUR_TURN:
				msg = "It's " + message.getColor() + " turn";
				break;
			case STALEMATE:
				msg = "Stalemate!";
				gameOver = true;
				gameInProgress = false;
				break;
			case PROMOTION:
				handlePromotion(message);
				return;
			default:
				break;
			}

			if (gameOver) {
				AlertDialog.Builder endGameDialog = new AlertDialog.Builder(
						MainActivity.this);
				endGameDialog.setMessage(msg);
				endGameDialog.setPositiveButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(MainActivity.this,
								HomeActivity.class);
						startActivity(intent);
					}
				});

				endGameDialog.create().show();
			} else {
				Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG)
						.show();
			}
		} else {
			gameInProgress = true;
		}
	}

	private void handleDraw() {
		AlertDialog.Builder drawDialog = new AlertDialog.Builder(
				MainActivity.this);
		drawDialog.setMessage("Draw?");
		drawDialog.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				gameInProgress = false;
				Intent intent = new Intent(MainActivity.this,
						HomeActivity.class);
				startActivity(intent);
			}
		});

		drawDialog.setNegativeButton("No, thanks.", null);
		drawDialog.create().show();
	}

	private void handlePromotion(final ResponseMessage message) {
		if (message == null || message.getState() != GameState.PROMOTION) {
			return;
		}

		final Class<?>[] pieces = { KnightPiece.class, BishopPiece.class,
				RookPiece.class, QueenPiece.class };
		ArrayList<Integer> resources = new ArrayList<Integer>();
		if (message.getColor() == PieceColor.BLACK_COLOR) {
			resources.add(R.drawable.blackknight);
			resources.add(R.drawable.blackbishop);
			resources.add(R.drawable.blackrook);
			resources.add(R.drawable.blackqueen);
		} else {
			resources.add(R.drawable.whitehorse);
			resources.add(R.drawable.whitebishop);
			resources.add(R.drawable.whiterook);
			resources.add(R.drawable.whitequeen);
		}

		AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
		PromotionAdapter promotionAdapter = new PromotionAdapter(
				MainActivity.this, R.layout.promotion_piece_row, resources);
		dialog.setAdapter(promotionAdapter, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Piece piece = null;
				Position position = new Position(0, 0);
				try {
					piece = (Piece) pieces[which].getDeclaredConstructor(
							new Class[] { PieceColor.class, Position.class })
							.newInstance(message.getColor(), position);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}

				if (piece != null) {
					ResponseMessage promotionResponseMessage = engine
							.promote(piece);
					handleResponseMessage(promotionResponseMessage);
					chessboardAdapter.notifyDataSetChanged();
				}
			}
		});

		dialog.create().show();
	}
}