package T9A1.client.data;


import java.util.ArrayList;
import java.util.List;

import T9A1.common.IConnection;
import T9A1.common.Item;
import T9A1.common.Location;
import T9A1.common.Request;

public class InventoryManager {

	private ICacheManager cacheManager;
	private IConnection connectionManager;
	private int storeNumber;

	public InventoryManager(ICacheManager cacheManager,
			IConnection connectionManager) {

		this.cacheManager = cacheManager;
		this.connectionManager = connectionManager;
	}

	public InventoryManager(ICacheManager cacheManager,
			IConnection connectionManager, int storeNumber) {

		this.cacheManager = cacheManager;
		this.connectionManager = connectionManager;
		this.storeNumber = storeNumber;
	}

	public Item[] doSearch(String query) {

		List<Item> resultList = null;

		// Check if the item should be handled by the cache
		if (cacheManager.isCacheHit(query)) {
			resultList = cacheManager.doSearch(query);

		} else {
			resultList = (List<Item>)connectionManager.sendRequest(Request.Type.item_search,
					query);

			// This result wasn't in the cache (or it's stale), so add it now
			cacheManager.add(query, resultList);
		}

		// Return null if the server returned null (indicating an error)
		if (resultList == null) return null;

		return resultList.toArray(new Item[0]);
	}

	public int getStoreNumber() {
		return storeNumber;
	}
}
