package T9A1.client.data;

import java.util.List;

import T9A1.common.IConnection;
import T9A1.common.Item;
import T9A1.common.Project;
import T9A1.common.Request;

/**
 * Responsible for accessing data.
 *
 * @author JL
 *
 */
public class RequestManager {

	private ICacheManager cacheManager;
	private IConnection connectionManager;
	private int storeNumber;

	public RequestManager(ICacheManager cacheManager,
			IConnection connectionManager) {

		this.cacheManager = cacheManager;
		this.connectionManager = connectionManager;
	}

	public RequestManager(ICacheManager cacheManager,
			IConnection connectionManager, int storeNumber) {

		this.cacheManager = cacheManager;
		this.connectionManager = connectionManager;
		this.storeNumber = storeNumber;
	}

	public Item[] searchItems(String query) {

		List<Item> resultList = null;

		// Check if the item should be handled by the cache
		if (cacheManager.isCacheHit(query)) {
			resultList = cacheManager.doSearch(query);

		} else {
			Request response = (Request)connectionManager.sendRequest(Request.Type.item_search,
					query);

			resultList = (List<Item>)response.data;

			// This result wasn't in the cache (or it's stale), so add it now
			cacheManager.add(query, resultList);
		}

		// Return null if the server returned null (indicating an error)
		if (resultList == null) return null;

		return resultList.toArray(new Item[0]);
	}

	public Project[] searchProjects(String query) {
		List<Project> resultList = null;

		// Check if the item should be handled by the cache

		// TODO(jliem): Cache is disabled for project
		if (cacheManager.isCacheHit(query) && false) {
			//resultList = cacheManager.doSearch(query);

		} else {
			resultList = (List<Project>)connectionManager.sendRequest(Request.Type.project_search,
					query);

			// This result wasn't in the cache (or it's stale), so add it now
			//cacheManager.add(query, resultList);
		}

		// Return null if the server returned null (indicating an error)
		if (resultList == null) return null;

		return resultList.toArray(new Project[0]);
	}

	public int getStoreNumber() {
		return storeNumber;
	}
}
