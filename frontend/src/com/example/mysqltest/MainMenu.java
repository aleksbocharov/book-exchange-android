package com.example.mysqltest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;




public class MainMenu extends Activity implements OnClickListener {
	private TextView welcomebox;
	private String username;
	private Button mAddBook, mMyBooks, mSearchBooks, mInbox, mAddComment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		welcomebox = (TextView) findViewById(R.id.textWelcome);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);
		username = sp.getString("username", "anon");
        welcomebox.setText("Welcome, " + username + "!");
        //setup buttons
      	mAddBook = (Button)findViewById(R.id.btnAddBook);
      	mMyBooks = (Button)findViewById(R.id.btnMyBooks);
      	mSearchBooks = (Button)findViewById(R.id.btnSrchBooks);
      	mInbox = (Button)findViewById(R.id.btnInbox);
      	mAddComment = (Button)findViewById(R.id.btnAddComment);
      	//register listeners
      	mAddBook.setOnClickListener(this);
      	mMyBooks.setOnClickListener(this);
      	mSearchBooks.setOnClickListener(this);
      	mInbox.setOnClickListener(this);
      	mAddComment.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {	
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnAddBook:
			Intent i1 = new Intent(this, AddBook.class);
			startActivity(i1);
			break;
		case R.id.btnMyBooks:
			Intent i2 = new Intent(this, MyBooks.class);
			startActivity(i2);
			break;
		case R.id.btnSrchBooks:
			Intent i3 = new Intent(this, SearchBooks.class);
			startActivity(i3);
			break;
		case R.id.btnInbox:
				Intent i4 = new Intent(this, ReadComments.class);
				startActivity(i4);
			break;
		case R.id.btnAddComment:
			Intent i5 = new Intent(this, AddComment.class);
			startActivity(i5);
		break;
		default:
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}
