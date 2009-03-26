package T9A1.client;

import T9A1.client.data.CacheManager;
import T9A1.client.data.InventoryManager;
import T9A1.client.gui.KioskGUI;
import T9A1.common.ConnectionManager;

public class KioskClientFactory {

	private int storeNumber = 0;

	public KioskClientFactory(String[] args) {
		if (args.length > 1) {
			storeNumber = Integer.parseInt(args[0]);
		}
	}

	public KioskGUI createKioskGUI(){
		InventoryManager im = this.createInventoryManager();
		KioskGUI gui = new KioskGUI(im);

		return gui;
	}

	public InventoryManager createInventoryManager() {
		InventoryManager im = new InventoryManager(new CacheManager(),
				new ConnectionManager(), storeNumber);

		return im;
	}

}
