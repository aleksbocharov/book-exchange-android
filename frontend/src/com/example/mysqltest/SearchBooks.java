package com.example.mysqltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SearchBooks extends Activity {
	private EditText bttext;
	private String booktitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_books);
		bttext = (EditText) findViewById(R.id.searchTitle);
		//booktitle = bttext.getText().toString();
	}
	
	public void searchBooks (View v)
	{	
		booktitle = bttext.getText().toString();
		Intent intent = new Intent(getApplicationContext(), SearchBookResults.class);
		intent.putExtra("Book Title", booktitle);
    	startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_books, menu);
		return true;
	}

}
