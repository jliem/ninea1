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

	public void add(String query, List<Item> items) {
		// TODO Auto-generated method stub

	}

	public void clear() {
		// TODO Auto-generated method stub

	}

	public void remove(String query) {
		// TODO Auto-generated method stub

	}

}
