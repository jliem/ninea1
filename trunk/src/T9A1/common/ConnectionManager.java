package T9A1.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class
 * @author Chase
 */

public class ConnectionManager implements IConnectionManager {
	/**
	 * Variables
	 * kioskID: identifies the kiosk
	 * kioskPassword: allows the kiosk to access the server
	 */
	private char[] kioskID;
	public Inet4Address serverIP;
	public boolean debug = true;
	public ConnectionManager(){


	}

	public String[] serializeRequest(String[] r){

		return null;
	}

	public String[] deserializeSearch(String[] s){

		return null;
	}

	public void sendRequest(String request){
		try{
			Inet4Address host = (Inet4Address) InetAddress.getLocalHost();
			Socket           client    = new Socket(host, 4321);
			DataOutputStream socketOut = new DataOutputStream(client.getOutputStream());
			DataInputStream  socketIn  = new DataInputStream(client.getInputStream());
			debug("Gets here");
		    socketOut.writeBytes(request + '\n');
	        socketOut.close(); socketIn.close(); client.close();
			}
	      catch (UnknownHostException e)
	      { System.err.println("Unknown host"); }
	      catch (IOException e)
	      { System.err.println("I/O error"); }
	}


	public void debug(String s){
		if(this.debug){
			System.out.println(s);
		}
	}

}