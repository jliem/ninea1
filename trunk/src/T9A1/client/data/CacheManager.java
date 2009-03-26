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

		System.out.println("Adding " + query + " to cache");
		if (map.size() >= maxCacheEntries) {
			// Using least-frequently-used cache algorithm
			String lfu = heap.poll().getQuery();

			map.remove(lfu);
		}

		// If we are replacing an existing entry, we need
		// to update the old entry in the heap instead of adding
		// a new one
		if (this.contains(query)) {
			CacheEntry entry = map.remove(query);

			heap.remove(entry);
		}

		// Create entry in cache
		CacheEntry entry = new CacheEntry(query, items);
		map.put(query, entry);

		heap.add(entry);

		verifyMapAndHeap();
	}

	public void remove(String query) {
		map.remove(query);

		// Find the corresponding entry in the heap
		CacheEntry target = new CacheEntry(query, null);
		heap.remove(target);

		verifyMapAndHeap();
	}

	public void clear() {
		map.clear();
		heap.clear();
	}

	protected boolean contains(String query) {
		return map.containsKey(query);
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

	private void verifyMapAndHeap() {
		if (map.size() != heap.size()) {
			System.err.println("Map size is " + map.size()
					+ " and heap size is " + heap.size()
					+ ", expected them to be equal");
		}
	}

}

