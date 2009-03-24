package T9A1.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import T9A1.client.data.ICacheManager;
import T9A1.common.Item;

public class CacheManager implements ICacheManager {

	/**
	 * Maximum size of the cache. Should be final, but making
	 * it non-final for testing.
	 */
	private static int maxCacheEntries = 500;

	private HashMap<String, CacheEntry> map;
	private PriorityQueue<CacheEntry> heap;

	public CacheManager() {
		map = new HashMap<String, CacheEntry>();
		heap = new PriorityQueue<CacheEntry>();
	}

	public boolean isCacheHit(String query) {
		// TODO(jliem): Also need to check if data has been updated in DB
		return map.containsKey(query);
	}

	public List<Item> doSearch(String query) {

		List<Item> result = new ArrayList<Item>();

		if (map.containsKey(query)) {
			CacheEntry data = map.get(query);
			data.incrementCount();

			result = data.getItems();
		}

		return result;
	}

	public void add(String query, List<Item> items) {
		if (map.size() >= maxCacheEntries) {
			// Using least-recently-used cache algorithm
			String lru = heap.poll().getQuery();

			map.remove(lru);
		}

		// Create entry in cache
		CacheEntry entry = new CacheEntry(query, items);
		map.put(query, entry);
		heap.add(entry);
	}

	public void remove(String query) {
		map.remove(query);

		// TODO(jliem): Is there a better way to do this?

		// Find the corresponding entry in the heap
		Iterator<CacheEntry> iter = heap.iterator();
		CacheEntry target = null, temp = null;;

		while (iter.hasNext() && target == null) {
			temp = iter.next();

			if (temp.getQuery().equals(query)) {
				target = temp;
			}
		}

		if (target != null) {
			heap.remove(target);
		}
	}

	public void clear() {
		map.clear();
		heap.clear();
	}

	protected boolean contains(Object o) {
		return map.containsKey(o);
	}

	/**
	 * Getter for testing purposes.
	 *
	 * @return the heap
	 */
	protected PriorityQueue<CacheEntry> getHeap() {
		return heap;
	}

	/**
	 * Getter for testing purposes.
	 * @return the map
	 */
	protected HashMap<String, CacheEntry> getMap() {
		return map;
	}

	protected void setMaxCacheEntries(int maxCacheEntries) {
		CacheManager.maxCacheEntries = maxCacheEntries;
	}

}

