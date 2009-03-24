package T9A1.client.data;

import java.util.ArrayList;
import java.util.List;

import T9A1.common.Item;

public class MockCacheManager implements ICacheManager {


	public boolean isCacheHit(String query) {
		return false;
	}

	public List<Item> doSearch(String query) {
		// TODO Auto-generated method stub
		return new ArrayList<Item>();
	}

}
