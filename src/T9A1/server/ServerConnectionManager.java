package T9A1.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import T9A1.common.Item;
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
	public int numPThreads = 0;
	public int numCThreads = 0;
	public int numConnections = 0;

	private KioskServer kioskServer;

	/**
	 * The intializer method
	 *
	 */
	public ServerConnectionManager(KioskServer kioskServer){
		this.kioskServer = kioskServer;

		try{
	         this.server = new ServerSocket(4321);
	         debug("Server Established");
	      }
	      catch (IOException e){
	         debug("Error on port: 4321 " + ", " + e);
	         System.exit(1);
	    }
	    new Thread(new ThreadStart(5)).start();

	}
	/**
	 * Inner class to create producer thread
	 * @author Chase
	 *
	 */
	class Producer implements Runnable{
		public boolean debug=true;
		public boolean running=true;
		protected int threadNum;
		protected BlockingQueue connections;
		protected BlockingQueue buffer;
		Producer(BlockingQueue theBuffer, BlockingQueue theConnections, int threadNum) {
			numPThreads = numPThreads+1;
			this.threadNum = threadNum;
			this.buffer = theBuffer;
			this.connections = theConnections;
		}
		/**
		 * Run method from runnable interface
		 * @author Chase
		 */
		public void run(){
			debug("Producer Thread " + threadNum + ": STARTING");
			while(running){
				Socket client = null;
			    try {
			    	client = server.accept();
			    	numConnections = numConnections + 1;
			    		if(client != null){
			    			try {
			    				BufferedReader streamIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
								String line;
								line = streamIn.readLine();
								ArrayList al = new ArrayList();
								al.add(client); al.add(line);
								buffer.add(al);
								debug("Producer Thread " + threadNum + ": " + line + " added to buffer");
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
		protected int threadNum;
		protected BlockingQueue buffer;
		protected BlockingQueue connections;
		Consumer(BlockingQueue theBuffer, BlockingQueue theConnections, int threadNum){
			numCThreads = numCThreads + 1;
			this.buffer = theBuffer;
			this.connections = theConnections;
			this.threadNum = threadNum;
		}
		/**
		 * Run method from runnable interface
		 * @author Chase
		 */
		public void run(){
			debug("Consumer Thread " + threadNum + ": STARTING");
			try{
				while(true){
					ArrayList al = (ArrayList)(buffer.take());

					Socket client = (Socket)(al.get(0));
					String search = (String)(al.get(1));

					System.out.println("Consumer Thread " + threadNum + ": " + search + " is removed from the buffer" );
					List<Item> outgoing = kioskServer.handleRequest(search);
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
			    socketOut.close(); client.close();
			    numConnections = numConnections - 1;
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
	 * This class is a thread that watches the number of connections and creates a thread
	 * whenever the # of connections grow
	 * @author Chase
	 */
	class ThreadStart implements Runnable{
		public int consumers;
		public ThreadStart(int c){
			this.consumers = c;
		}

		public void run() {
			BlockingQueue buffer = new LinkedBlockingQueue();
			BlockingQueue connections = new LinkedBlockingQueue();
			Vector<Thread> threads = new Vector<Thread>();
			Thread tempThread;

			//On startup create two threads

			for(int i=0;i<consumers;i++){
				new Thread(new Consumer(buffer, connections,numCThreads)).start();
			}
			new Thread(new Producer(buffer, connections,numPThreads)).start();
			while(true){
				if (numConnections < 0){
					System.out.println("This is bad");
					numConnections = 0;
				}
				else if((numConnections + 1) >= numPThreads){
					tempThread = new Thread(new Producer(buffer, connections,numPThreads));
					threads.add(tempThread);
					tempThread.start();
				}
			}
		}
	}
	public void debug(String s){
		if(this.debug){
			System.out.println(s);
		}
	}

}
