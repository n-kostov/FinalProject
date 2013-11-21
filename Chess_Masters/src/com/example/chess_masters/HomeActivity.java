package com.example.chess_masters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
	private Button continueGameBtn;
	private Button startNewGameBtn;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903040);
		this.startNewGameBtn = ((Button) findViewById(R.id.start_new_game_btn));
		this.startNewGameBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramView) {
				Intent localIntent = new Intent(HomeActivity.this,
						MainActivity.class);
				HomeActivity.this.startActivity(localIntent);
			}
		});
		this.continueGameBtn = ((Button) findViewById(R.id.continue_game_btn));
		this.continueGameBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramView) {
				Intent localIntent = new Intent(HomeActivity.this,
						MainActivity.class);
				localIntent.putExtra("continue", true);
				HomeActivity.this.startActivity(localIntent);
			}
		});
	}

	protected void onResume() {
		super.onResume();
		if (!((ChessMastersApplication) getApplication()).getPiecesDb()
				.hasAnyRecords()) {
			this.continueGameBtn.setEnabled(false);
			return;
		}
		this.continueGameBtn.setEnabled(true);
	}
}

/*
 * Location: C:\Users\spind\Desktop\ChessMasters_dex2jar.jar Qualified Name:
 * com.example.chess_masters.HomeActivity JD-Core Version: 0.6.0
 */