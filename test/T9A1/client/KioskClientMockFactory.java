package T9A1.client;

import T9A1.client.data.InventoryManager;
import T9A1.client.data.MockCacheManager;
import T9A1.common.ConnectionManager;

public class KioskClientMockFactory implements IKioskClientFactory {

	public KioskClientMockFactory(String[] args) {

	}

	public InventoryManager createInventoryManagerWithoutCache() {
		InventoryManager im = new InventoryManager(new MockCacheManager(),
				new ConnectionManager());

		return im;
	}
}
