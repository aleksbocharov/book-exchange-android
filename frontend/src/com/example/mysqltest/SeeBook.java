package com.example.mysqltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SeeBook extends Activity {
	private TextView tvtitle, tvauthor, tvisbn, tvowner;
	String title, author, isbn, owner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_see_book);
		Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	title = extras.getString("Title");
        	author = extras.getString("Author");
        	isbn = extras.getString("ISBN");
        	owner = extras.getString("User");
            // and get whatever type user account id is
        }
		tvtitle = (TextView) findViewById(R.id.tvFrom);
		tvauthor = (TextView) findViewById(R.id.tvTitle);
		tvisbn = (TextView) findViewById(R.id.tvMessage);
		tvowner =  (TextView) findViewById(R.id.tvOwner);
		tvtitle.setText("Title: " + title);
		tvauthor.setText("Author: " + author);
		tvisbn.setText("ISBN: " + isbn);
		tvowner.setText("Owner: " + owner);
	}
	
	public void sendMessage (View v)
	{
		Intent intent = new Intent(this, SendMessage.class);
		intent.putExtra("Receiver", owner);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.see_book, menu);
		return true;
	}

}
