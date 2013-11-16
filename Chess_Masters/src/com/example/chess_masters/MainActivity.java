package com.example.chess_masters;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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
				ResponseMessage message = engine.move(MainActivity.this,
						new Position(arg2 / 8, arg2 % 8));
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
			switch (message.getState()) {
			case CHECK:
				msg = "Check!";
				break;
			case CHECKMATE:
				msg = "Checkmate! " + message.getColor() + " wins!";
				break;
			case DRAW:
				msg = "Draw!";
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
				break;
			default:
				break;
			}

			Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
		}
	}

	private class ChessboardAdapter extends ArrayAdapter<Square> {

		private Context context;
		private ArrayList<Square> items;

		public ChessboardAdapter(Context context, int resource,
				ArrayList<Square> array) {
			super(context, resource, array);
			this.context = context;
			this.items = array;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				iv = (ImageView) inflater.inflate(R.layout.chess_square,
						parent, false);
			} else {
				iv = (ImageView) convertView;
			}

			Piece piece = this.items.get(position).getPiece();
			if (piece != null) {
				iv.setImageResource(resourceFinder.getResource(piece));
			} else {
				iv.setImageResource(-1);
			}

			boolean offset = (position / 8) % 2 == 0;
			if ((offset && position % 2 == 0) || (!offset && position % 2 != 0)) {
				iv.setBackgroundColor(Color.WHITE);
			} else {
				iv.setBackgroundColor(Color.GRAY);
			}

			if (piece != null && piece.isSelected()) {
				iv.setBackgroundColor(Color.CYAN);
			} else if (this.items.get(position).isAttacked()) {
				iv.setBackgroundColor(Color.BLUE);
			}

			return iv;
		}
	}

	private class PromotionAdapter extends ArrayAdapter<Piece> {

		private Context context;
		private ArrayList<Piece> items;

		public PromotionAdapter(Context context, int resource,
				ArrayList<Piece> array) {
			super(context, resource, array);
			this.context = context;
			this.items = array;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				iv = (ImageView) inflater.inflate(R.layout.chess_square,
						parent, false);
			} else {
				iv = (ImageView) convertView;
			}

			if (this.items.get(position) != null) {
				iv.setImageResource(resourceFinder.getResource(this.items
						.get(position)));
			}

			return iv;
		}
	}

}
