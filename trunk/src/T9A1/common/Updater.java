package T9A1.common;

import java.util.Calendar;

public class Updater implements Runnable{

	public Updater(){
		
	}
	public void run() {
		while(true){
			//Check the time once every minute
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(Calendar.HOUR_OF_DAY >= 22 && Calendar.MINUTE > 30){
				
			}
		}
	}
	
}
