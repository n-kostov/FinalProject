package com.example.chess_masters.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.example.chess_masters.PieceResourceFinder;
import com.example.chess_masters.game.Square;
import com.example.chess_masters.pieces.Piece;
import java.util.ArrayList;

public class ChessboardAdapter extends ArrayAdapter<Square> {
	private Context context;
	private ArrayList<Square> items;
	private int resource;

	public ChessboardAdapter(Context context, int resoureceId,
			ArrayList<Square> items) {
		super(context, resoureceId, items);
		this.context = context;
		this.resource = resoureceId;
		this.items = items;
	}

	public View getView(int position, View contentView, ViewGroup parent) {
		ImageView iv;
		if (contentView == null) {
			iv = (ImageView) ((LayoutInflater) this.context
					.getSystemService("layout_inflater")).inflate(
					this.resource, parent, false);
		} else {
			iv = (ImageView) contentView;
		}

		Square square = this.items.get(position);
		Piece piece = square.getPiece();
		iv.setImageResource(PieceResourceFinder.getResource(piece));
		if ((position / 8) % 2 == 0 && position % 2 != 0) {
			iv.setBackgroundColor(Color.DKGRAY);
		} else if ((position / 8) % 2 != 0 && position % 2 == 0) {
			iv.setBackgroundColor(Color.DKGRAY);
		} else {
			iv.setBackgroundColor(Color.WHITE);
		}

		if (square.isAttacked()) {
			iv.setBackgroundColor(Color.BLUE);
		} else if (square.isSelected()) {
			iv.setBackgroundColor(Color.CYAN);
		}

		return iv;
	}
}