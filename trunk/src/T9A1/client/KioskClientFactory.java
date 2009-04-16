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

	/** The path for resources */
	private String resource_path;

	/**
	 * Creates a new KioskClientFactory.
	 * @param args an array whose first element should be the store number
	 */
	public KioskClientFactory(String[] args) {
		resource_path = "resources/";
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-ide_resource_path")) {
				resource_path = "client/gui/";
			} else {
				try{
					storeNumber = Integer.parseInt(args[i]);
				}catch(Exception e){}
			}
		}
	}

	/**
	 * Creates a new KioskGUI and displays it.
	 * @return a KioskGUI that represents the kiosk
	 */
	public KioskGUI createKioskGUI(){
		RequestManager im = this.createInventoryManager();
		KioskGUI gui = new KioskGUI(im, resource_path);

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
