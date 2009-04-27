package T9A1.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import T9A1.common.Item;

import junit.framework.TestCase;

/**
 * Tests the CacheManager.
 * @author Johannes Liem
 *
 */
public class CacheManagerTest extends TestCase {

	private CacheManager cm;
	private HashMap<String, CacheEntry> map;
	private PriorityQueue<CacheEntry> heap;

	private ArrayList<Item> itemList;
	private String[] queries;

	private final String NOT_IN_CACHE = "query not in cache";
	private final String IN_CACHE = "query in cache";

	public CacheManagerTest() {
		itemList = new ArrayList<Item>();
		Item one = new Item();
		one.setName("Item one");
		Item two = new Item();
		two.setName("Item two");

		itemList.add(one);
		itemList.add(two);

		queries = new String[5];
		for (int i=0; i<queries.length; i++) {
			queries[i] = "Test Query " + (i+1);
		}
	}

	public void setUp() {
		cm = new CacheManager();
		map = cm.getMap();
		heap = cm.getHeap();
	}

	/**
	 * Tests a regular search.
	 */
	public void testDoSearch() {
		cm.add(this.IN_CACHE, itemList);

		List<Item> result = cm.doSearch(this.IN_CACHE);

		assertEquals(itemList, result);
	}

	/**
	 * Tests for cache hits after adding and removal.
	 */
	public void testIsCacheHit() {
		// Check for miss
		boolean isHit = cm.isCacheHit(this.IN_CACHE);

		assertFalse(isHit);

		// Add to cache
		cm.add(this.IN_CACHE, itemList);

		isHit = cm.isCacheHit(this.IN_CACHE);

		assertTrue(isHit);

		// Remove from cache
		cm.remove(this.IN_CACHE);

		isHit = cm.isCacheHit(this.IN_CACHE);

		assertFalse(isHit);
	}

	/**
	 * Tests for basic cache hit.
	 */
	public void testCacheHit() {
		cm.add(this.IN_CACHE, itemList);

		boolean isHit = cm.isCacheHit(this.IN_CACHE);

		assertTrue(isHit);
	}

	/**
	 * Tests for basic cache miss.
	 */
	public void testCacheMiss() {
		// Check that a miss is properly flagged as such
		boolean isHit = cm.isCacheHit(this.NOT_IN_CACHE);

		assertFalse(isHit);
	}

	/**
	 * Tests least-frequently-used removal algorithm.
	 */
	public void testLfuRemoval() {
		for (String query : queries) {
			cm.add(query, itemList);
		}

		cm.setMaxCacheEntries(5);

		// Access query 1 one time, query 2 two times, etc. so query
		// 5 will be accessed five times
		for (int i=0; i<queries.length; i++) {
			for (int j=0; j<(i+1); j++) {
				cm.doSearch(queries[j]);
			}
		}

		// Add another query. Query 1 should be removed from the cache.
		String testQuery = "new test query";
		cm.add(testQuery, null);

		assertFalse(cm.contains(queries[0]));
		assertTrue(cm.contains(testQuery));
		assertTrue(cm.contains(queries[1]));

		// Add another query. The test query we just added should be removed.
		cm.add(queries[0], null);
		assertFalse(cm.contains(testQuery));
		assertTrue(cm.contains(queries[0]));
	}

	/**
	 * Tests direct removal from cache.
	 */
	public void testDirectRemoval() {
		cm.add(this.IN_CACHE, itemList);

		// Construct a CacheEntry that looks like the one
		// used in the PriorityQueue
		CacheEntry entry = new CacheEntry(this.IN_CACHE, itemList);

		cm.remove(this.IN_CACHE);

		assertFalse(map.containsKey(this.IN_CACHE));
		assertFalse(heap.contains(entry));
	}

	/**
	 * Tests adding to the cache.
	 */
	public void testAdd() {

		// Test single add
		cm.add(this.IN_CACHE, itemList);

		// Construct a CacheEntry that looks like the one
		// used in thePriorityQueue
		CacheEntry entry = new CacheEntry(this.IN_CACHE, itemList);

		assertTrue(map.containsKey(this.IN_CACHE));
		assertTrue(heap.contains(entry));

		assertEquals(map.size(), 1);
		assertEquals(heap.size(), 1);

		// Test multiple adds
		cm.clear();

		for (int i=0; i<queries.length; i++) {
			cm.add(queries[i], itemList);
			entry = new CacheEntry(queries[i], itemList);

			assertTrue(map.containsKey(queries[i]));
			assertTrue(heap.contains(entry));

			assertEquals(map.size(), i+1);
			assertEquals(heap.size(), i+1);
		}
	}

	/**
	 * Tests replacing an existing entry in the cache.
	 */
	public void testReplace() {
		String q = this.IN_CACHE;
		ArrayList<Item> list1 = new ArrayList<Item>();
		list1.add(new Item());

		ArrayList<Item> list2 = new ArrayList<Item>();

		cm.add(q, list1);

		List<Item> cachedList = cm.doSearch(q);

		assertEquals(list1, cachedList);

		cm.add(q, list2);

		cachedList = cm.doSearch(q);

		assertEquals(list2, cachedList);
	}

	/**
	 * Tests clearing the cache.
	 */
	public void testClear() {
		cm.add(this.IN_CACHE, itemList);

		// Construct a CacheEntry that looks like the one
		// used in thePriorityQueue
		CacheEntry entry = new CacheEntry(this.IN_CACHE, itemList);

		assertTrue(map.containsKey(this.IN_CACHE));
		assertTrue(heap.contains(entry));

		assertEquals(map.size(), 1);
		assertEquals(heap.size(), 1);

		cm.clear();

		assertFalse(map.containsKey(this.IN_CACHE));
		assertFalse(heap.contains(entry));

		assertEquals(map.size(), 0);
		assertEquals(heap.size(), 0);
	}

	/**
	 * Tests the counts of items when they are accessed.
	 */
	public void testHeapCount() {
		String query = this.IN_CACHE;

		cm.add(query, itemList);

		// Construct a CacheEntry that looks like the one
		// used in thePriorityQueue
		CacheEntry entry = new CacheEntry(query, itemList);

		// Add a hit
		cm.doSearch(query);

		entry.incrementCount();

		CacheEntry actual = heap.peek();

		assertEquals(entry.getCount(), heap.peek().getCount());

		// Add another hit
		cm.doSearch(query);

		entry.incrementCount();

		assertEquals(entry.getCount(), heap.peek().getCount());
	}
}
