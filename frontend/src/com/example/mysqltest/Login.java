package com.example.mysqltest;

import newpack.Server;
import newpack.UserFuncs;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{
	private EditText username, pass;
	private Button mSubmit, mRegister;
	Server server = new Server();
	 // Progress Dialog
    private ProgressDialog pDialog;
    public UserFuncs user;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //php login script location:

    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
   // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/login.php";

    //testing on Emulator:
    //////////////////////////////private static final String LOGIN_URL;

  //testing from a real server:
    //private static final String LOGIN_URL = "http://www.yourdomain.com/webservice/login.php";

    //JSON element ids from repsonse of php script:
    //private static final String TAG_SUCCESS = "success";
    //private static final String TAG_MESSAGE = "message";
    private boolean svyaz;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		//setup input fields
		
		username = (EditText)findViewById(R.id.username);
		pass = (EditText)findViewById(R.id.password);

		//setup buttons
		mSubmit = (Button)findViewById(R.id.login);
		mRegister = (Button)findViewById(R.id.register);

		//register listeners
		mSubmit.setOnClickListener(this);
		mRegister.setOnClickListener(this);
		svyaz = false; 
		new CheckConnection().execute();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
				new CheckConnection().execute();
				if(svyaz==true){
				new AttemptLogin().execute();
				}
				else
				{Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();}
			break;
		case R.id.register:
				Intent i = new Intent(this, Register.class);
				startActivity(i);
			break;

		default:
			break;
		}
	}
	class CheckConnection extends AsyncTask<Boolean, Boolean, Boolean>{
		
		 @Override
			protected Boolean doInBackground(Boolean... args){
			 try{
				 
				 svyaz = server.isConnected(2000);
				 Log.d("Svyaz !!!!", Boolean.toString(svyaz));
				 }
			 catch (Exception e) {
	                e.printStackTrace();
	            }
			 return true;
		 }
		 
		
	}
	class AttemptLogin extends AsyncTask<String, String, String> {

		 /**
         * Before starting background thread Show Progress Dialog
         * */
		boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            String success= "";
            String usernametxt = username.getText().toString();
            String password = pass.getText().toString();
            user = new UserFuncs(usernametxt, password, Login.this);
            success = user.Login();
            
            //user.storeCreds();
            return success;
            

		}
		
		
		/**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }
            //destroy user object
            user = null;
        }

	}

}
