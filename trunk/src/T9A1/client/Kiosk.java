package T9A1.client;


/**
 * Class.
 * @author Catie
 *
 */
public class Kiosk {
	public static void main(String args[]){
		if (args.length > 0) {
			new KioskClientFactory(args).createKioskGUI();
		} else {
			System.out.println("Usage: java Kiosk [storeNumber]");
		}
	}
}