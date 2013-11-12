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

public class MainActivity extends Activity {

	private ArrayList<Piece> array;
	private ArrayAdapter<Piece> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		array = new ArrayList<Piece>(64);
		array.add(0,
				new RookPiece(PieceColor.BLACK_COLOR, R.drawable.blackrook));
		array.add(1, new KnightPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackknight));
		array.add(2, new BishopPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackbishop));
		array.add(3,
				new KingPiece(PieceColor.BLACK_COLOR, R.drawable.blackking));
		array.add(4, new QueenPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackqueen));
		array.add(5, new BishopPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackbishop));
		array.add(6, new KnightPiece(PieceColor.BLACK_COLOR,
				R.drawable.blackknight));
		array.add(7,
				new RookPiece(PieceColor.BLACK_COLOR, R.drawable.blackrook));
		for (int i = 8; i < 16; i++) {
			array.add(i, new PawnPiece(PieceColor.BLACK_COLOR,
					R.drawable.blackpawn));
		}

		for (int i = 16; i < 48; i++) {
			array.add(i, null);
		}

		for (int i = 48; i < 56; i++) {
			array.add(i, new PawnPiece(PieceColor.WHITE_COLOR,
					R.drawable.whitepawn));
		}

		array.add(56,
				new RookPiece(PieceColor.WHITE_COLOR, R.drawable.whiterook));
		array.add(57, new KnightPiece(PieceColor.WHITE_COLOR,
				R.drawable.whitehorse));
		array.add(58, new BishopPiece(PieceColor.WHITE_COLOR,
				R.drawable.whitebishop));
		array.add(59,
				new KingPiece(PieceColor.WHITE_COLOR, R.drawable.whiteking));
		array.add(60, new QueenPiece(PieceColor.WHITE_COLOR,
				R.drawable.whitequeen));
		array.add(61, new BishopPiece(PieceColor.WHITE_COLOR,
				R.drawable.whitebishop));
		array.add(62, new KnightPiece(PieceColor.WHITE_COLOR,
				R.drawable.whitehorse));
		array.add(63,
				new RookPiece(PieceColor.WHITE_COLOR, R.drawable.whiterook));

		adapter = new MyAdapter(this, R.layout.chess_square, array);
		GridView grid = (GridView) findViewById(R.id.gridview1);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
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

	private class MyAdapter extends ArrayAdapter<Piece> {

		private Context context;
		private ArrayList<Piece> items;

		public MyAdapter(Context context, int resource, ArrayList<Piece> array) {
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
				iv.setImageResource(this.items.get(position).getResource());
			}

			boolean offset = (position / 8) % 2 == 0;
			if ((offset && position % 2 == 0) || (!offset && position % 2 != 0)) {
				iv.setBackgroundColor(Color.GRAY);
			} else {
				iv.setBackgroundColor(Color.WHITE);
			}

			return iv;
			// return super.getView(position, convertView, parent);
		}
	}

}
