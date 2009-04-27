package T9A1.common;

import java.util.Calendar;

public class Updater implements Runnable{

	public boolean timeToUpdate;
	public boolean updatedForDay;
	public Updater(boolean update){
		this.timeToUpdate = update;
		this.updatedForDay = false;
	}
	public void run() {
		while(true){
			//Check the time once every minute
			if(!updatedForDay){	
				try {
					Thread.sleep(1 * 60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
				if(Calendar.HOUR_OF_DAY >= 22 && Calendar.MINUTE > 30){
					this.timeToUpdate = true;
					this.updatedForDay = true;
				} else {
					this.timeToUpdate = false;
				}
			}
			else{
				try {
					Thread.sleep(60 * 60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(Calendar.HOUR_OF_DAY > 15 && Calendar.HOUR_OF_DAY < 22){
					this.updatedForDay = false;
				}
			}
		}
	}
	public boolean Update(){
		if(this.timeToUpdate && !this.updatedForDay){
			return true;
		}else{
			return false;
		}
	}
	
}
