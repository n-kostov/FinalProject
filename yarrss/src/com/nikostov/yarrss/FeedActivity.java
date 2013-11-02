package com.nikostov.yarrss;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class FeedActivity extends Activity implements OnClickListener {
	AlertDialog addDialog;
	EditText addFeedAddress;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.btn_add_feed) {
			addDialog.show();
		}
		
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);
		
		addFeedAddress = new EditText(this);
		AlertDialog.Builder addDialogBuilder =  new AlertDialog.Builder(this);
		addDialogBuilder.setTitle(R.string.title_add_feed_address);
		addDialogBuilder.setView(addFeedAddress);
		addDialogBuilder.setPositiveButton(R.string.add_feed_address_ok, this);
		addDialogBuilder.setNegativeButton(R.string.add_feed_address_no, this);
		addDialog = addDialogBuilder.create();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feed, menu);
		return true;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(which == DialogInterface.BUTTON_POSITIVE) {
			((YarssApplication)getApplication()).getFeedsDb().AddUrl(addFeedAddress.getText().toString());
		}
	}

}
