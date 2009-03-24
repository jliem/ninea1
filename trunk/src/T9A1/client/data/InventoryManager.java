package T9A1.client.data;


import java.util.List;

import T9A1.common.IConnectionManager;
import T9A1.common.Item;
import T9A1.common.Location;

public class InventoryManager {

	private ICacheManager cacheManager;
	private IConnectionManager connectionManager;

	public InventoryManager(ICacheManager cacheManager,
			IConnectionManager connectionManager) {

		this.cacheManager = cacheManager;
		this.connectionManager = connectionManager;
	}

	public Item[] doSearch(String query) {

		List<Item> resultList = null;

		// Check if the item should be handled by the cache
		if (cacheManager.isCacheHit(query)) {
			resultList = cacheManager.doSearch(query);
		} else {

			// TODO: Finish me
			List<Item> list = connectionManager.sendRequest("foo");
			System.out.println(list.get(0));
			list.get(0).setLocation(new Location(2, 3));
		}

		return resultList.toArray(new Item[0]);
	}
}
