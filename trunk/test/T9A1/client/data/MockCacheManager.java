package T9A1.client.data;

import java.util.List;

import T9A1.common.Item;

public class MockCacheManager implements ICacheManager {


	public boolean isCacheHit(String query) {
		return false;
	}

	@Override
	public List<Item> doSearch(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
