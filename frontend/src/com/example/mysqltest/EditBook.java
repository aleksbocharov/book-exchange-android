package com.example.mysqltest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditBook extends Activity implements OnClickListener {
	private String title, author, isbn, bookID;
	private EditText eTitle, eAuthor, eIsbn;
	private Button  mSubmit, mDelete;
	JSONParser jsonParser = new JSONParser();
	 private ProgressDialog pDialog;
	 private static final String EDIT_BOOK_URL = "http://10.0.2.2/android_web/editbook.php";
	 private static final String DELETE_BOOK_URL = "http://10.0.2.2/android_web/deletebook.php";

	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_MESSAGE = "message";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_book);
		Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	bookID = extras.getString("BookID");
        	title = extras.getString("Title");
        	author = extras.getString("Author");
        	isbn = extras.getString("ISBN");
            // and get whatever type user account id is
        }
        eTitle = (EditText)findViewById(R.id.editTextTitle);
        eAuthor = (EditText)findViewById(R.id.editTextAuthor);
        eIsbn = (EditText)findViewById(R.id.editTextIsbn);
        eTitle.setText(title);
        eAuthor.setText(author);
        eIsbn.setText(isbn);
		mSubmit = (Button)findViewById(R.id.submit);
		mSubmit.setOnClickListener(this);
		mDelete = (Button)findViewById(R.id.delete);
		mDelete.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			new UpdateBook().execute();
			break;
		case R.id.delete:
			new DeleteBook().execute();
			break;
		default:
			break;
		}
}
	
	class UpdateBook extends AsyncTask<String, String, String> {
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditBook.this);
            pDialog.setMessage("Loading Book Info...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;
            String post_title = eTitle.getText().toString();
            String post_author = eAuthor.getText().toString();
            String post_isbn = eIsbn.getText().toString();
            //Retrieving Saved Username Data:

            
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("book_id", bookID));
                params.add(new BasicNameValuePair("book_title", post_title));
                params.add(new BasicNameValuePair("author", post_author));
                params.add(new BasicNameValuePair("isbn", post_isbn));
                Log.d("request!", "starting");
                
                //Posting user data to script 
                JSONObject json = jsonParser.makeHttpRequest(
                		EDIT_BOOK_URL, "POST", params);
 
                // full json response
                Log.d("Update Book attempt", json.toString());
 
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Log.d("Book Updated!", json.toString());    
                	finish();
                	return json.getString(TAG_MESSAGE);
                }else{
                	Log.d("Book Update Failure!", json.getString(TAG_MESSAGE));
                	return json.getString(TAG_MESSAGE);
                	
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
			
		}
		
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(EditBook.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
	
	class DeleteBook extends AsyncTask<String, String, String> {
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditBook.this);
            pDialog.setMessage("Deleting Book...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;

            
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("book_id", bookID));
                Log.d("request!", "starting");
                
                //Posting user data to script 
                JSONObject json = jsonParser.makeHttpRequest(
                		DELETE_BOOK_URL, "POST", params);
 
                // full json response
                Log.d("Deleting Book attempt", json.toString());
 
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Log.d("Book Deleted!", json.toString());    
                	finish();
                	return json.getString(TAG_MESSAGE);
                }else{
                	Log.d("Book Delete Failure!", json.getString(TAG_MESSAGE));
                	return json.getString(TAG_MESSAGE);
                	
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
			
		}
		
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(EditBook.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_book, menu);
		return true;
	}

}
