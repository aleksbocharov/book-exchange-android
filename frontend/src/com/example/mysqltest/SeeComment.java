package com.example.mysqltest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SeeComment extends Activity implements OnClickListener {
	private TextView tvfrom, tvtitle, tvmessage;
	private String sender, title, message, message_id;
	private Button  mReply, mDelete;
	private static final String DELETE_MESSAGE_URL = "http://10.0.2.2/android_web/deletemessage.php";
	JSONParser jsonParser = new JSONParser();
	private ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_see_comment);
		Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	sender = extras.getString("fromwho");
        	title = extras.getString("title");
        	message = extras.getString("message");
        	message_id = extras.getString("message_ID");
        }
        tvfrom = (TextView) findViewById(R.id.tvFrom);
		tvtitle = (TextView) findViewById(R.id.tvTitle);
		tvmessage = (TextView) findViewById(R.id.tvMessage);
		tvtitle.setTypeface(null, Typeface.BOLD);
		tvfrom.setText("From: " + sender);
		tvtitle.setText(title);
		tvmessage.setText(message);
		mReply = (Button)findViewById(R.id.btnReply);
		mReply.setOnClickListener(this);
		mDelete = (Button)findViewById(R.id.btnDeleteMes);
		mDelete.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnReply:
			String retitle = "RE: " + title;
			Intent intent = new Intent(this, SendMessage.class);
			intent.putExtra("Receiver", sender);
			intent.putExtra("Title", retitle);
			startActivity(intent);
			break;
		case R.id.btnDeleteMes:
			new DeleteMessage().execute();
			break;
		default:
			break;
		}
	}
	
	class DeleteMessage extends AsyncTask<String, String, String> {
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SeeComment.this);
            pDialog.setMessage("Deleting Message...");
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
                params.add(new BasicNameValuePair("post_id", message_id));
                Log.d("request!", "starting");
                
                //Posting user data to script 
                JSONObject json = jsonParser.makeHttpRequest(
                		DELETE_MESSAGE_URL, "POST", params);
 
                // full json response
                Log.d("Deleting Message attempt", json.toString());
 
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Log.d("Message Deleted!", json.toString());    
                	finish();
                	return json.getString(TAG_MESSAGE);
                }else{
                	Log.d("Message Delete Failure!", json.getString(TAG_MESSAGE));
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
            	Toast.makeText(SeeComment.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.see_comment, menu);
		return true;
	}

}
