package com.example.chess_masters;

import java.util.ArrayList;

import javax.crypto.spec.PSource;

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

	private ArrayList<Square> array;
	private ArrayAdapter<Square> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		array = new ArrayList<Square>(64);
		array.add(0, new Square(new RookPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackrook), new Position(0, 0)));
		array.add(1, new Square(new KnightPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackknight), new Position(0, 1)));
		array.add(2, new Square(new BishopPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackbishop), new Position(0, 2)));
		array.add(3, new Square(new KingPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackking), new Position(0, 3)));
		array.add(4, new Square(new QueenPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackqueen), new Position(0, 4)));
		array.add(5, new Square(new BishopPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackbishop), new Position(0, 5)));
		array.add(6, new Square(new KnightPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackknight), new Position(0, 6)));
		array.add(7, new Square(new RookPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackrook), new Position(0, 7)));
		for (int i = 8; i < 16; i++) {
			array.add(i, new Square(new PawnPiece(PieceColor.BLACK_COLOR,
					R.drawable.blackpawn), new Position(1, i % 8)));
		}

		for (int i = 16; i < 48; i++) {
			array.add(i, new Square(null, new Position(i / 8, i % 8)));
		}

		for (int i = 48; i < 56; i++) {
			array.add(i, new Square(new PawnPiece(PieceColor.WHITE_COLOR,
					R.drawable.whitepawn), new Position(6, i % 8)));
		}

		array.add(56, new Square(new RookPiece(PieceColor.WHITE_COLOR,
				R.drawable.whiterook), new Position(7, 0)));
		array.add(57, new Square(new KnightPiece(PieceColor.WHITE_COLOR,
				R.drawable.whitehorse), new Position(7, 1)));
		array.add(58, new Square(new BishopPiece(PieceColor.WHITE_COLOR,
				R.drawable.whitebishop), new Position(7, 2)));
		array.add(59, new Square(new KingPiece(PieceColor.WHITE_COLOR,
				R.drawable.whiteking), new Position(7, 3)));
		array.add(60, new Square(new QueenPiece(PieceColor.WHITE_COLOR,
				R.drawable.whitequeen), new Position(7, 4)));
		array.add(61, new Square(new BishopPiece(PieceColor.WHITE_COLOR,
				R.drawable.whitebishop), new Position(7, 5)));
		array.add(62, new Square(new KnightPiece(PieceColor.WHITE_COLOR,
				R.drawable.whitehorse), new Position(7, 6)));
		array.add(63, new Square(new RookPiece(PieceColor.WHITE_COLOR,
				R.drawable.whiterook), new Position(7, 7)));

		adapter = new MyAdapter(this, R.layout.chess_square, array);
		GridView grid = (GridView) findViewById(R.id.gridview1);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Piece clickedElement = array.get(arg2).getPiece();
				if (clickedElement != null) {
					clickedElement.setSelected(!clickedElement.isSelected());
					ArrayList<Position> selectedMoves = array.get(arg2)
							.getPiece()
							.possibleMoves(new Position(arg2 / 8, arg2 % 8));
					for (Position position : selectedMoves) {
						Square square = array.get(position.getX() * 8
								+ position.getY());
						square.setAttacked(!square.isAttacked());
					}
				}

				// Collections.reverse(array);
				adapter.notifyDataSetChanged();
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
