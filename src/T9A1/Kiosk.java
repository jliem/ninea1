package T9A1;

import T9A1.client.KioskClientFactory;

/**
 * Class.
 * @author Catie
 *
 */
public class Kiosk {
	public static void main(String args[]){
		new KioskClientFactory(args).createKioskGUI();
	}
}