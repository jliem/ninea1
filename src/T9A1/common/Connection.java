package T9A1.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class Connection implements IConnection {
	/**
	 * Variables
	 *
	 */
	public boolean debug = true;
	public Connection(){


	}

	public Object sendRequest(Request.Type type, Object data) {
		return sendRequest(new Request(type, data));
	}
	/**
	 * Send request method for client, that sends then blocks for reponse
	 * @author Chase
	 */
	public Object sendRequest(Request request){
		debug("Starting send request in conn manager");
		Object response = null;

		try{
			Inet4Address host = (Inet4Address) InetAddress.getLocalHost();
			Socket           client    = new Socket(host, 4321);
			ObjectOutputStream socketOut = new ObjectOutputStream(client.getOutputStream());

		    socketOut.writeObject(request);
		    ObjectInputStream  socketIn = null;
		    try {
		    	// TODO if blocks for too long it should error
				socketIn = new ObjectInputStream(client.getInputStream());
				response = (List<Item>) (socketIn.readObject());

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
	      return response;
	}

	/**
	 * Debug method to show debugging information
	 * @author Chase
	 */
	public void debug(String s){
		if(this.debug){
			System.out.println(s);
		}
	}
}

