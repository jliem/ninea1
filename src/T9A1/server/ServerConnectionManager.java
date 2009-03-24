package T9A1.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import T9A1.common.Item;
import T9A1.common.Location;
/**
 * Server Connection Manager
 * @author Chase
 */

public class ServerConnectionManager{
	/**
	 * Class variables
	 * 
	 */
	public boolean debug = true;
	public ServerSocket server;
	/**
	 * The intializer method
	 * @author Chase
	 */
	public ServerConnectionManager(){
		try{
	         this.server = new ServerSocket(4321);
	         debug("Server Established");
	      }
	      catch (IOException e){
	         debug("Error on port: 4321 " + ", " + e);
	         System.exit(1);
	    }
	}
	/**
	 * Inner class to create producer thread
	 * @author Chase
	 */
	class Producer implements Runnable{
		public boolean debug=true;
		protected BlockingQueue connections;
		protected BlockingQueue buffer;
		Producer(BlockingQueue theBuffer, BlockingQueue theConnections) {
			this.buffer = theBuffer;
			this.connections = theConnections;
		}
		/**
		 * Run method from runnable interface
		 * @author Chase
		 */
		public void run(){
			debug("Producer Thread: STARTING");
			while(true){
				Socket client = null;
			    try {
			    	client = server.accept();
			    		if(client != null){
			    			try {
			    				BufferedReader streamIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
								String line;
								line = streamIn.readLine();
								ArrayList al = new ArrayList();
								al.add(client); al.add(line);
								buffer.add(al);
								debug(line + " added to buffer");
							}catch(IOException e){ 
								debug("IO Error in streams"); 
							}
			    		}
			    	}
			    catch (IOException e) {
			    	debug("Did not accept connection");
			    	System.exit(1);
			    }
			}
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

	/**
	 * Inner class to create consumer thread
	 * @author Chase
	 */
	class Consumer implements Runnable{
		public boolean debug = true;
		protected BlockingQueue buffer;
		protected BlockingQueue connections;
		Consumer(BlockingQueue theBuffer, BlockingQueue theConnections){
			this.buffer = theBuffer;
			this.connections = theConnections;
		}
		/**
		 * Run method from runnable interface
		 * @author Chase
		 */
		public void run(){
			debug("Consumer Thread: STARTING");
			try{
				while(true){
					System.out.println(buffer.size());
					ArrayList al = (ArrayList)(buffer.take());

					Socket client = (Socket)(al.get(0));
					System.out.println("second size is " + buffer.size());
					String search = (String)(al.get(1));

					System.out.println("Serach is " + search);
					ArrayList outgoing = null;
					/**
					 * @dodo IMPLEMENT SEARCH CODE HERE 
					 * OUTGOING = LIST
					 */
					sendRequest(client, outgoing);
				}
			}catch(Throwable e){
				e.printStackTrace();
				debug("Consumer error");
			}
		}
		/**
		 * Send request method
		 * @author Chase
		 * @returns boolean
		 * @param client, request
		 */
		public boolean sendRequest(Socket client, List request){
			try{
				ObjectOutputStream socketOut = new ObjectOutputStream(client.getOutputStream());
				socketOut.writeObject(request);
				debug("Wrote request");
			    socketOut.close(); client.close();
			    return true;
			}
			catch (UnknownHostException e)
			{ System.err.println("Unknown host"); return false;}
			catch (IOException e)
			{ e.printStackTrace(); System.err.println("I/O error"); return false;}
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
