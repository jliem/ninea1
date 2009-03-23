package T9A1.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

	public List<Item> sendRequest(String request){
		debug("Starting send request in conn manager");
		List<Item> items = null;

		try{
			Inet4Address host = (Inet4Address) InetAddress.getLocalHost();
			Socket           client    = new Socket(host, 4321);
			DataOutputStream socketOut = new DataOutputStream(client.getOutputStream());

			debug("Gets here");
		    socketOut.writeBytes(request + '\n');
		    ObjectInputStream  socketIn = null;
		    try {
		    	//socketIn.wait();
				socketIn = new ObjectInputStream(client.getInputStream());
				items = (List<Item>) (socketIn.readObject());

				debug("Got arraylist " + items);

				Item i = items.get(0);
				System.out.println("Item's name: " + i.getName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        socketOut.close(); socketIn.close(); client.close();

			}
	      catch (UnknownHostException e)
	      { System.err.println("Unknown host"); }
	      catch (IOException e)
	      { System.err.println("I/O error"); }
	      return items;
	}


	public void debug(String s){
		if(this.debug){
			System.out.println(s);
		}
	}

}