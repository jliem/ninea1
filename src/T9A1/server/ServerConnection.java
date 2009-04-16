package T9A1.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import T9A1.common.Request;

/**
 *
 * Connects the server to the client.
 *
 * @author Chase
 */

public class ServerConnection{
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
	public ServerConnection(KioskServer kioskServer){
		this.kioskServer = kioskServer;

		try{
	         this.server = new ServerSocket(4321);
	         this.server.setSoTimeout(0);
	         debug("Server Established");
	      }
	      catch (IOException e){
	         debug("Error on port: 4321 " + ", " + e);
	         System.exit(1);
	    }
	    new Thread(new ServerStart(5)).start();

	}
	/**
	 * Inner class to create producer thread
	 * @author Chase
	 *
	 */
	class Producer implements Runnable{
		public boolean debug=false;
		public boolean running=true;
		protected int threadNum;
		protected BlockingQueue buffer;
		Producer(BlockingQueue theBuffer, int threadNum) {
			numPThreads = numPThreads+1;
			this.threadNum = threadNum;
			this.buffer = theBuffer;
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
			    	//THIS IS WHERE THE THREAD BLOCKS
			    	client = server.accept();
			    	numConnections = numConnections + 1;
			    		if(client != null){
			    			try {
			    				//BufferedReader streamIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
			    				ObjectInputStream streamIn = new ObjectInputStream(client.getInputStream());
			    				Request request = (Request)streamIn.readObject();

								ArrayList<Object> al = new ArrayList<Object>();
								al.add(client); al.add(request);
								buffer.add(al);
								debug("Producer Thread " + threadNum + ": " + request.data + " added to buffer");
							}catch(IOException e){
								debug("IO Error in streams");
							} catch (ClassNotFoundException e) {
								debug("Class not found exception in server");
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
		public boolean debug = false;
		protected int threadNum;
		protected BlockingQueue buffer;
		Consumer(BlockingQueue theBuffer, int threadNum){
			numCThreads = numCThreads + 1;
			this.buffer = theBuffer;
			this.threadNum = threadNum;
		}
		/**
		 * Run method from runnable interface
		 * @author Chase
		 */
		public void run(){
			debug("Consumer Thread " + threadNum + ": STARTING");
			while(true){
				Request request = null;
				Socket client = null;
				try{
					//THIS IS WHERE THE THREAD BLOCKS
					ArrayList al = (ArrayList)(buffer.take());

					client = (Socket)(al.get(0));
					request = (Request)(al.get(1));

					if (request != null) {
						request.data = kioskServer.handleRequest(request);
						request.type = Request.Type.results;
					}
					sendRequest(client, request);  // send response
				}catch(Throwable e){
					e.printStackTrace();
					debug("Consumer error");
				}

			}
		}
		/**
		 * Send request method
		 * @author Chase
		 * @returns boolean
		 * @param client, request
		 */
		public boolean sendRequest(Socket client, Request request){
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
	class ServerStart implements Runnable{
		public int consumers;
		public ServerStart(int c){
			this.consumers = c;
		}

		public void run() {
			BlockingQueue buffer = new LinkedBlockingQueue();
			Vector<Thread> threads = new Vector<Thread>();
			Thread tempThread;

			//On startup create two threads

			for(int i=0;i<consumers;i++){
				new Thread(new Consumer(buffer,numCThreads)).start();
			}
			new Thread(new Producer(buffer,numPThreads)).start();
			while(true){
				if (numConnections < 0){
					System.out.println("This is bad");
					numConnections = 0;
				}
				else if((numConnections + 1) >= numPThreads){
					tempThread = new Thread(new Producer(buffer,numPThreads));
					threads.add(tempThread);
					tempThread.start();
				}
				try {
					//Determines the amount of sleep time for the main thread
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
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
