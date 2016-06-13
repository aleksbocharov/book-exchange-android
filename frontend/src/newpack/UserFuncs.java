package newpack;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.mysqltest.JSONParser;
import com.example.mysqltest.MainMenu;

public class UserFuncs {
	String username;
	String password;
	//private final Context context;
	Activity activity;
	JSONParser jsonParser = new JSONParser();
	Server server = new Server();
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    int success;
    
	public UserFuncs(Activity a)
	{
		this.activity = a;
	}
	public UserFuncs(String u, String p, Activity a)
	{
		this.username = u;
		this.password = p;
		this.activity = a;
	}
	

	
	public String Login()
	{
		try{
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));

        Log.d("request!", "starting");
        // getting product details by making HTTP request
        JSONObject json = jsonParser.makeHttpRequest(server.getServerAd("login"), "POST", params);
        
        // check your log for json response
        Log.d("Login attempt", json.toString());

        // json success tag
        success = json.getInt(TAG_SUCCESS);
        
        if(success==1){
        storeCreds();
        Log.d("Login Successful!", json.toString());
        Intent i = new Intent(activity, MainMenu.class);
        activity.finish();
        activity.startActivity(i);
    	return json.getString(TAG_MESSAGE);
		}else
		{
    	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
    	return json.getString(TAG_MESSAGE);
		}
        }
		catch (JSONException e) {
            e.printStackTrace();
            return e.toString();
        }
	}
	public void storeCreds()
	{
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor edit = sp.edit();
		edit.putString("username", username);
		edit.commit();
	}
}
