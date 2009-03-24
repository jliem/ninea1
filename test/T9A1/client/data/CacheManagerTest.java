package T9A1.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import T9A1.common.Item;

import junit.framework.TestCase;

public class CacheManagerTest extends TestCase {

	private CacheManager cm;
	private ArrayList<Item> itemList;
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
	}

	public void setUp() {
		cm = new CacheManager();
	}

	public void testDoSearch() {
		cm.add(this.IN_CACHE, itemList);

		List<Item> result = cm.doSearch(this.IN_CACHE);

		assertEquals(itemList, result);
	}

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

	public void testCacheHit() {
		cm.add(this.IN_CACHE, itemList);

		boolean isHit = cm.isCacheHit(this.IN_CACHE);

		assertTrue(isHit);
	}

	public void testCacheMiss() {
		// Check that a miss is properly flagged as such
		boolean isHit = cm.isCacheHit(this.NOT_IN_CACHE);

		assertFalse(isHit);
	}

	public void testRemoval() {
		HashMap<String, CacheEntry> map = cm.getMap();
		PriorityQueue<CacheEntry> heap = cm.getHeap();

		cm.add(this.IN_CACHE, itemList);

		// Construct a CacheEntry that looks like the one
		// used in thePriorityQueue
		CacheEntry entry = new CacheEntry(this.IN_CACHE, itemList);

		cm.remove(this.IN_CACHE);

		assertFalse(map.containsKey(this.IN_CACHE));
		assertFalse(heap.contains(entry));
	}

	public void testAdd() {
		HashMap<String, CacheEntry> map = cm.getMap();
		PriorityQueue<CacheEntry> heap = cm.getHeap();

		cm.add(this.IN_CACHE, itemList);

		// Construct a CacheEntry that looks like the one
		// used in thePriorityQueue
		CacheEntry entry = new CacheEntry(this.IN_CACHE, itemList);

		assertTrue(map.containsKey(this.IN_CACHE));
		assertTrue(heap.contains(entry));

		assertEquals(map.size(), 1);
		assertEquals(heap.size(), 1);
	}

	public void testHeapCount() {
		HashMap<String, CacheEntry> map = cm.getMap();
		PriorityQueue<CacheEntry> heap = cm.getHeap();

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
