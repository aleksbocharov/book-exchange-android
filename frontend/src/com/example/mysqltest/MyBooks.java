package com.example.mysqltest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyBooks extends ListActivity {
	String owner;
	// Progress Dialog
	private ProgressDialog pDialog;
 
	//php read comments script
    
    //localhost :  
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
   // private static final String READ_COMMENTS_URL = "http://xxx.xxx.x.x:1234/webservice/comments.php";
    
    //testing on Emulator:
    private static final String MY_BOOKS_URL = "http://10.0.2.2/android_web/mybooks.php";
    
  //testing from a real server:
    //private static final String READ_COMMENTS_URL = "http://www.mybringback.com/webservice/comments.php";
   
  //JSON IDS:
    private static final String TAG_ID = "book_id";
    private static final String TAG_TITLE = "book_title";
    private static final String TAG_POSTS = "posts";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_ISBN = "isbn";
    

   //An array of all of our comments
    private JSONArray mBooks = null;
    //manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //note that use read_comments.xml instead of our single_post.xml
        setContentView(R.layout.activity_my_books); 
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyBooks.this);
        owner = sp.getString("username", "");

    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	//loading the comments via AsyncTask
    	new LoadBooks().execute();
    }

	public void addComment(View v)
    {
        Intent i = new Intent(MyBooks.this, AddComment.class);
        startActivity(i);
    }

    /**
     * Retrieves json data of comments
     */
    public void updateJSONdata(String owner) {

        // Instantiate the arraylist to contain all the JSON data.
    	// we are going to use a bunch of key-value pairs, referring
    	// to the json element name, and the content, for example,
    	// message it the tag, and "I'm awesome" as the content..
    	
    	mBookList = new ArrayList<HashMap<String, String>>();
        
        // Bro, it's time to power up the J parser 
        JSONParser jParser = new JSONParser();
        // Feed the beast our comments url, and it spits us
        //back a JSON object.  Boo-yeah Jerome.
        //JSONObject json = jParser.getJSONFromUrl(READ_COMMENTS_URL);

        //when parsing JSON stuff, we should probably
        //try to catch any exceptions:
try {
            
        	//I know I said we would check if "Posts were Avail." (success==1)
        	//before we tried to read the individual posts, but I lied...
        	//mComments will tell us how many "posts" or comments are
        	//available
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", owner));
			JSONObject json = jParser.makeHttpRequest(MY_BOOKS_URL, "POST", params);
			mBooks = json.getJSONArray(TAG_POSTS);

            // looping through all posts according to the json object returned
            for (int i = 0; i < mBooks.length(); i++) {
                JSONObject c = mBooks.getJSONObject(i);

                //gets the content of each tag
                String book_id = c.getString(TAG_ID);
                String title = c.getString(TAG_TITLE);
                String author = c.getString(TAG_AUTHOR);
                String isbn = c.getString(TAG_ISBN);
                

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                
                map.put(TAG_ID, book_id);
                map.put(TAG_TITLE, title);
                map.put(TAG_AUTHOR, author);
                map.put(TAG_ISBN, isbn);
             
                // adding HashList to ArrayList
                mBookList.add(map);
                
                //annndddd, our JSON data is up to date same with our array list
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts the parsed data into our listview
     */
    private void updateList() {
		// For a ListActivity we need to set the List Adapter, and in order to do
		//that, we need to create a ListAdapter.  This SimpleAdapter,
		//will utilize our updated Hashmapped ArrayList, 
		//use our single_post xml template for each item in our list,
		//and place the appropriate info from the list to the
		//correct GUI id.  Order is important here.
		ListAdapter adapter = new SimpleAdapter(this, mBookList,
				R.layout.single_book, new String[] { TAG_TITLE, TAG_AUTHOR, TAG_ISBN }, new int[] { R.id.title, R.id.author, R.id.isbn });

		// I shouldn't have to comment on this one:
		setListAdapter(adapter);
		
		// Optional: when the user clicks a list item we 
		//could do something.  However, we will choose
		//to do nothing...
		ListView lv = getListView();	
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(), EditBook.class);
				intent.putExtra("BookID", mBookList.get(position).get(TAG_ID));
				intent.putExtra("Title", mBookList.get(position).get(TAG_TITLE));
				intent.putExtra("Author", mBookList.get(position).get(TAG_AUTHOR));
				intent.putExtra("ISBN", mBookList.get(position).get(TAG_ISBN));
				//intent.putExtra("User", mBookList.get(position).get(TAG_USERNAME));
				Log.d("Data send", mBookList.get(position).get(TAG_TITLE));
		    	startActivity(intent);

				

			}
		});
	}       

    public class LoadBooks extends AsyncTask<Void, Void, Boolean> {

    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MyBooks.this);
			pDialog.setMessage("Loading the List of Your Books...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
        @Override
        protected Boolean doInBackground(Void... arg0) {
        	//we will develop this method in version 2
            updateJSONdata(owner);
            return null;

        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
          //we will develop this method in version 2
            updateList();
        }
    }
}
