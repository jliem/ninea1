package T9A1.client;


/**
 * The main class for the kiosk.
 * @author Catie
 */
public class Kiosk {
	/**
	 * @param args takes the store number in at the command line
	 */
	public static void main(String args[]){
		if (args.length > 0) {
			new KioskClientFactory(args).createKioskGUI();
		} else {
			System.out.println("Usage: java Kiosk [storeNumber] [-ide_image_path]");
		}
	}
}
