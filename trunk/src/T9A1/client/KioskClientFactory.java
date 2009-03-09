package T9A1.client;

import T9A1.client.data.CacheManager;
import T9A1.client.data.InventoryManager;
import T9A1.gui.KioskGUI;
import T9A1.common.ConnectionManager;

public class KioskClientFactory {

	public KioskClientFactory(String[] args) {

	}

	public KioskGUI createKioskGUI(){
		InventoryManager im = this.createInventoryManager();
		KioskGUI gui = new KioskGUI(im);

		return gui;
	}

	public InventoryManager createInventoryManager() {
		InventoryManager im = new InventoryManager(new CacheManager(),
				new ConnectionManager());

		return im;
	}

}
