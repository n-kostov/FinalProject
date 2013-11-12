package com.example.chess_masters;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		engine = new ChessEngine();
		adapter = new MyAdapter(this, R.layout.chess_square, engine.getArray());
		GridView grid = (GridView) findViewById(R.id.gridview1);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Piece clickedElement = engine.getArray().get(arg2).getPiece();
				if (clickedElement != null) {
					if (clickedElement.getPieceColor() != engine.getInTurn()) {
						Toast.makeText(
								MainActivity.this,
								"It's " + engine.getInTurn().toString()
										+ " turn", Toast.LENGTH_LONG).show();
					} else {
						clickedElement.setSelected(!clickedElement.isSelected());
						Hashtable<Direction, ArrayList<Position>> selectedMoves = engine
								.getArray()
								.get(arg2)
								.getPiece()
								.possibleMoves(new Position(arg2 / 8, arg2 % 8));
						Enumeration<Direction> keys = selectedMoves.keys();
						while (keys.hasMoreElements()) {
							Direction currentDirection = keys.nextElement();
							for (Position movePosition : selectedMoves
									.get(currentDirection)) {

								Square square = engine.getArray().get(
										movePosition.getX() * 8
												+ movePosition.getY());
								Piece currentSquarePiece = square.getPiece();
								if (currentSquarePiece != null) {
									if (currentSquarePiece.getPieceColor() != clickedElement
											.getPieceColor()) {
										square.setAttacked(!square.isAttacked());
									}

									break;
								}

								square.setAttacked(!square.isAttacked());
							}
						}

						adapter.notifyDataSetChanged();
					}
				}

				// Collections.reverse(array);

				// Toast.makeText(MainActivity.this, String.valueOf(arg2),
				// Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class MyAdapter extends ArrayAdapter<Square> {

		private Context context;
		private ArrayList<Square> items;

		public MyAdapter(Context context, int resource, ArrayList<Square> array) {
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

			if (this.items.get(position).getPiece() != null) {
				iv.setImageResource(this.items.get(position).getPiece()
						.getResource());
			}

			boolean offset = (position / 8) % 2 == 0;
			if ((offset && position % 2 == 0) || (!offset && position % 2 != 0)) {
				iv.setBackgroundColor(Color.GRAY);
			} else {
				iv.setBackgroundColor(Color.WHITE);
			}

			if (this.items.get(position).getPiece() != null
					&& this.items.get(position).getPiece().isSelected()) {
				iv.setBackgroundColor(Color.CYAN);
			} else if (this.items.get(position).isAttacked()) {
				iv.setBackgroundColor(Color.BLUE);
			}

			return iv;
		}
	}

}
