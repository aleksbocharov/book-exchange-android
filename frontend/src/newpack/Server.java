package newpack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server {
	
	String serveraddress;
	String login = "login.php";
	
	public Server()
	{
		serveraddress = "http://10.0.2.2/android_web/";
		//login = "login.php";
	}
	public Server(String sa)
	{
		serveraddress = sa;
		//login = lg;
	}
	
	public String getServerAd(String function)
	{
		if(function.equalsIgnoreCase("login"))
		return serveraddress + login;
		else
		return "";
	}
	
	public boolean isConnected(int timeout){

	    try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(serveraddress).openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        connection.setRequestMethod("HEAD");
	        int responseCode = connection.getResponseCode();
	        return (200 <= responseCode && responseCode <= 399);
	    } catch (IOException exception) {
	        return false;
	    }
	}
}
