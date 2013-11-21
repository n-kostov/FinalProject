package com.example.chess_masters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.util.ArrayList;

import com.example.chess_masters.R;

public class PromotionAdapter extends ArrayAdapter<Integer> {
	private Context context;
	private ArrayList<Integer> items;
	private int resource;

	public PromotionAdapter(Context context, int resource,
			ArrayList<Integer> items) {
		super(context, resource, items);
		this.context = context;
		this.resource = resource;
		this.items = items;
	}

	public View getView(int position, View contentView, ViewGroup parent) {
		Viewholder viewholder;
		if (contentView == null) {
			contentView = ((LayoutInflater) this.context
					.getSystemService("layout_inflater")).inflate(
					this.resource, parent, false);
			viewholder = new Viewholder();
			viewholder.promotionImageView = ((ImageView) contentView
					.findViewById(R.id.promotion_piece));
			contentView.setTag(viewholder);
		} else {
			viewholder = (Viewholder) contentView.getTag();
			viewholder.promotionImageView.setImageResource(this.items
					.get(position));
		}

		viewholder.promotionImageView
				.setImageResource(this.items.get(position));

		return contentView;
	}

	private static class Viewholder {
		ImageView promotionImageView;
	}
}