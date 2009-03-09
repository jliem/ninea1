package T9A1.client.data;

import T9A1.common.IConnectionManager;
import T9A1.common.Item;

public class InventoryManager {

	private ICacheManager cacheManager;
	private IConnectionManager connectionManager;

	public InventoryManager(ICacheManager cacheManager,
			IConnectionManager connectionManager) {

		this.cacheManager = cacheManager;
		this.connectionManager = connectionManager;
	}

	public Item[] doSearch(String query) {
		return null;
	}
}
