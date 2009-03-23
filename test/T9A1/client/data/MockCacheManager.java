package T9A1.client.data;

import T9A1.common.Item;

public class MockCacheManager implements ICacheManager {

	public Item[] doSearch(String query) {
		return new Item[0];
	}

	public boolean isCacheHit(String query) {
		return false;
	}

}
