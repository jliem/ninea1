package T9A1.client.data;

import java.util.List;

import T9A1.common.Item;

/**
 * Represents an entry in the cache. Used by the CacheManager.
 *
 * @author JL
 *
 */
public class CacheEntry implements Comparable<CacheEntry> {
	private List<Item> items;
	private int count;
	private String query;

	public CacheEntry(String query, List<Item> items) {
		this.query = query;
		this.items = items;

		count = 0;
	}

	public void incrementCount() {
		count++;
	}

	public String getQuery() {
		return query;
	}

	public List<Item> getItems() {
		return items;
	}

	public int getCount() {
		return count;
	}

	public int compareTo(CacheEntry ci) {
		return this.count - ci.count;
	}

	public boolean equals(Object o) {
		if (o instanceof CacheEntry) {
			CacheEntry ce = (CacheEntry)o;

			if (this.query != null) {
				return (this.query.equals(ce.query));
			}
		}

		return false;
	}

	public String toString() {
		return String.format("Query: \"%s\", hits: %d, num items: %d", query,
				count, (items != null ? items.size() : 0));
	}
}
