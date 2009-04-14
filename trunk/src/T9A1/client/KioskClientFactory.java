package T9A1.client;

import T9A1.client.data.CacheManager;
import T9A1.client.data.RequestManager;
import T9A1.client.gui.KioskGUI;
import T9A1.common.Connection;

/**
 * The class used to initialize the kiosk components.
 * @author Catie
 */
public class KioskClientFactory {

	/** The store's number. */
	private int storeNumber = 0;

	/**
	 * Creates a new KioskClientFactory.
	 * @param args an array whose first element should be the store number
	 */
	public KioskClientFactory(String[] args) {
		if (args.length > 0) {
			storeNumber = Integer.parseInt(args[0]);
		}
	}

	/**
	 * Creates a new KioskGUI and displays it.
	 * @return a KioskGUI that represents the kiosk
	 */
	public KioskGUI createKioskGUI(){
		RequestManager im = this.createInventoryManager();
		KioskGUI gui = new KioskGUI(im);

		return gui;
	}

	/**
	 * @return an InventoryManager for the kiosk
	 */
	public RequestManager createInventoryManager() {
		RequestManager im = new RequestManager(new CacheManager(),
				new Connection(), storeNumber);

		return im;
	}

}
