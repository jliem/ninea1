package T9A1.client.data;

import java.util.ArrayList;
import java.util.List;

import T9A1.common.Item;

/**
 * A mock implementation of the cache manager.
 * @author JL
 *
 */
public class MockCacheManager implements ICacheManager {


	public boolean isCacheHit(String query) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Item> doSearch(String query) {
		// TODO Auto-generated method stub
		return new ArrayList<Item>();
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(String query, List<Item> items) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(String query) {
		// TODO Auto-generated method stub

	}

}
