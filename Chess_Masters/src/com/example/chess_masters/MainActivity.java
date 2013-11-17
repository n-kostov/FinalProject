package com.example.chess_masters;

import java.util.ArrayList;

import com.example.chess_masters.game.ChessEngine;
import com.example.chess_masters.game.GameState;
import com.example.chess_masters.game.PieceColor;
import com.example.chess_masters.game.Position;
import com.example.chess_masters.game.ResponseMessage;
import com.example.chess_masters.game.Square;
import com.example.chess_masters.pieces.BishopPiece;
import com.example.chess_masters.pieces.KnightPiece;
import com.example.chess_masters.pieces.Piece;
import com.example.chess_masters.pieces.QueenPiece;
import com.example.chess_masters.pieces.RookPiece;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ChessEngine engine;
	private ArrayAdapter<Square> adapter;
	private PieceResourceFinder resourceFinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		resourceFinder = new PieceResourceFinder();
		engine = new ChessEngine();
		adapter = new ChessboardAdapter(this, R.layout.chess_square,
				engine.getArray());
		GridView grid = (GridView) findViewById(R.id.gridview1);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ResponseMessage message = engine.move(new Position(arg2 / 8,
						arg2 % 8));
				handleResponseMessage(message);
				adapter.notifyDataSetChanged();
				// Collections.reverse(array);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
				break;
			case DRAW:
				msg = "Draw!";
				gameOver = true;
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
		}
	}

	private void handlePromotion(final ResponseMessage message) {
		if (message == null || message.getState() != GameState.PROMOTION) {
			return;
		}

		// final Class[] pieces = { KnightPiece.class, BishopPiece.class,
		// RookPiece.class, QueenPiece.class };
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
				// try {
				// piece = (Piece) pieces[which].getDeclaredConstructor(
				// new Class[] { PieceColor.class, Position.class })
				// .newInstance(message.getColor(), position);
				// } catch (IllegalArgumentException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (InstantiationException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IllegalAccessException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (InvocationTargetException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (NoSuchMethodException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				switch (which) {
				case 0:
					piece = new KnightPiece(message.getColor(), position);
					break;
				case 1:
					piece = new BishopPiece(message.getColor(), position);
					break;
				case 2:
					piece = new RookPiece(message.getColor(), position);
					break;
				case 3:
					piece = new QueenPiece(message.getColor(), position);
					break;
				default:
					break;
				}

				ResponseMessage promotionResponseMessage = engine
						.promote(piece);
				handleResponseMessage(promotionResponseMessage);
				adapter.notifyDataSetChanged();
			}
		});

		dialog.create().show();
	}

	private class ChessboardAdapter extends ArrayAdapter<Square> {

		private Context context;
		private ArrayList<Square> items;
		private int resource;

		public ChessboardAdapter(Context context, int resource,
				ArrayList<Square> array) {
			super(context, resource, array);
			this.context = context;
			this.resource = resource;
			this.items = array;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				iv = (ImageView) inflater.inflate(this.resource, parent, false);
			} else {
				iv = (ImageView) convertView;
			}

			Piece piece = this.items.get(position).getPiece();
			if (piece != null) {
				iv.setImageResource(resourceFinder.getResource(piece));
			} else {
				// TODO: constant
				iv.setImageResource(-1);
			}

			boolean offset = (position / 8) % 2 == 0;
			if ((offset && position % 2 == 0) || (!offset && position % 2 != 0)) {
				iv.setBackgroundColor(Color.WHITE);
			} else {
				iv.setBackgroundColor(Color.GRAY);
			}

			if (piece != null && this.items.get(position).isSelected()) {
				iv.setBackgroundColor(Color.CYAN);
			} else if (this.items.get(position).isAttacked()) {
				iv.setBackgroundColor(Color.BLUE);
			}

			return iv;
		}
	}

	private class PromotionAdapter extends ArrayAdapter<Integer> {
		private Context context;
		private ArrayList<Integer> items;
		private int resource;

		public PromotionAdapter(Context context, int resource,
				ArrayList<Integer> resources) {
			super(context, resource, resources);
			this.context = context;
			this.resource = resource;
			this.items = resources;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Viewholder viewHolder;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(this.resource, parent, false);
				viewHolder = new Viewholder();
				viewHolder.iv = (ImageView) convertView
						.findViewById(R.id.piece);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (Viewholder) convertView.getTag();
			}

			if (this.items.get(position) != null) {
				viewHolder.iv.setImageResource(this.items.get(position));
			}

			return convertView;
		}
	}

	private static class Viewholder {
		ImageView iv;
	}

}
