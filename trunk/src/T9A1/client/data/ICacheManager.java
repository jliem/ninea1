package T9A1.client.data;

import java.util.List;

import T9A1.common.Item;

/**
 * Interface for a cache mechanism.
 *
 * @author JL
 *
 */
public interface ICacheManager {

	/**
	 * Adds an entry to the cache.
	 *
	 * @param query the search query
	 * @param items the list of items to associate with this query
	 */
	public void add(String query, List<Item> items);

	/**
	 * Checks whether this query is both in the cache and is not stale.
	 *
	 * @param query the search query
	 * @return true if the query is a cache hit (in the cache and not stale),
	 * false otherwise.
	 */
	public boolean isCacheHit(String query);

	/**
	 * Performs a search using the given search query.
	 *
	 * @param query the search query
	 * @return a list of results
	 */
	public List<Item> doSearch(String query);

	/**
	 * Removes an entry from the cache if it exists, does nothing otherwise.
	 *
	 * @param query the search query to remove
	 */
	public void remove(String query);

	/**
	 * Clears all entries from the cache.
	 */
	public void clear();
}
