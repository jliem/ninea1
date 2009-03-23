package T9A1.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class KioskServer {

	public static void main(String args[]) {
		int numProducers = 1;
		int numConsumers = 1;

		startThreads(numProducers, numConsumers);
	}

	public static void startThreads(int p, int c){
		BlockingQueue buffer = new LinkedBlockingQueue();
		BlockingQueue connections = new LinkedBlockingQueue();

		ServerConnectionManager scm = new ServerConnectionManager();

		for(int i=0;i<p;i++){
			new Thread(scm.new Producer(buffer, connections)).start();
		}

		for(int i=0;i<c;i++){
			new Thread(scm.new Consumer(buffer, connections)).start();
		}
	}
}
