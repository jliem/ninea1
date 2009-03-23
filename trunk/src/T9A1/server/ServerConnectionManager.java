package T9A1.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
 *
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
	 *
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
	 *
	 */
	class Producer implements Runnable{
		public boolean debug=true;
		protected BlockingQueue connections;
		protected BlockingQueue buffer;
		Producer(BlockingQueue theBuffer, BlockingQueue theConnections) {
			this.buffer = theBuffer;
			this.connections = theConnections;
		}
		public void run(){
			try{
				debug("Producer Thread: STARTING");
				while(true){
					Socket client = null;
				    try {
				    	client = server.accept();
				    	try{
				    		if(client != null){
				    			try {
									DataInputStream streamIn = new
						                  DataInputStream(new
						                  BufferedInputStream(client.getInputStream()));
									String line;
									line = streamIn.readLine();
									ArrayList al = new ArrayList();
									al.add(client); al.add(line);
									buffer.add(al);
									debug(line + " added to buffer");
								}catch(IOException e)
								{ System.out.println("IO Error in streams " + e); }
				    			int len = buffer.size();
				    			debug("Buffer is now size " + len);

				    		}
				    	}catch(Throwable e){debug("Queue error");}
				    }
				    catch (IOException e) {
				    	debug("Did not accept connection");
				    	System.exit(1);
				    }
				}
			}catch(Throwable e){
				debug("Producer error");
			}
		}
		public void debug(String s){
			if(this.debug){
				System.out.println(s);
			}
		}
	}

	/**
	 * Inner class to create consumer thread
	 * @author Chase
	 *
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
		 * @author Chase
		 * @category Consumer method
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

					/**
					 * IMPLEMENT SEARCH CODE HERE
					 * SERIALIZE
					 */
					ArrayList outgoing = new ArrayList();
					Item item = new Item();
					item.setName(search);
					Item item2 = new Item();
					item2.setName("Test item");
					item2.setLocation(new Location(1, 2));
					outgoing.add(item);
					outgoing.add(item2);

					sendRequest(client, outgoing);
				}
			}catch(Throwable e){
				e.printStackTrace();
				debug("Consumer error");
			}
		}
		/**
		 * @author Chase
		 * @category Consumer method
		 * @returns a boolean on whether or not it got sent
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
		 * @author Chase
		 * @category Consumer method
		 * Shows debugging information if debug mode is
		 * activated.
		 */
		public void debug(String s){
			if(this.debug){
				System.out.println(s);
			}
		}
	}

	public String[] serializeSearch(String[] s){

		return null;
	}

	public String[] deserializeRequest(String[] r){

		return null;
	}

	public void debug(String s){
		if(this.debug){
			System.out.println(s);
		}
	}

}
