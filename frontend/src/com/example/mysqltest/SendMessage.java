package com.example.mysqltest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessage extends Activity implements OnClickListener {
	private String receiver, sender, title;
	private EditText edTitle, edMessage;
	private ProgressDialog pDialog;
	private static final String POST_COMMENT_URL = "http://10.0.2.2/android_web/addcomment.php";
    
	  //testing from a real server:
	    //private static final String POST_COMMENT_URL = "http://www.mybringback.com/webservice/addcomment.php";
	    
	    //ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private Button  mSubmit;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_message);
		edTitle = (EditText)findViewById(R.id.etTitle);
		edMessage = (EditText)findViewById(R.id.etMessage);
		mSubmit = (Button)findViewById(R.id.btnReply);
		mSubmit.setOnClickListener(this);
		
		Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	receiver = extras.getString("Receiver");
        	title = extras.getString("Title");
            // and get whatever type user account id is
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SendMessage.this);
        sender = sp.getString("username", "");
        edTitle.setText(title);
	}
	
	@Override
	public void onClick(View v) {
				new PostComment().execute();
	}
	
class PostComment extends AsyncTask<String, String, String> {
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SendMessage.this);
            pDialog.setMessage("Sending Message...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;
            String post_title = edTitle.getText().toString();
            String post_message = edMessage.getText().toString();
            //Retrieving Saved Username Data:
            
            
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", sender));
                params.add(new BasicNameValuePair("title", post_title));
                params.add(new BasicNameValuePair("message", post_message));
                params.add(new BasicNameValuePair("receiver", receiver));
                Log.d("request!", "starting");
                
                //Posting user data to script 
                JSONObject json = jsonParser.makeHttpRequest(
                		POST_COMMENT_URL, "POST", params);
 
                // full json response
                Log.d("Attempt Send Message", json.toString());
 
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Log.d("Message Sent!", json.toString());    
                	finish();
                	return json.getString(TAG_MESSAGE);
                }else{
                	Log.d("Sending Failure!", json.getString(TAG_MESSAGE));
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
            	Toast.makeText(SendMessage.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_message, menu);
		return true;
	}

}
