package T9A1.client.data;

import java.util.List;
import T9A1.common.IConnection;
import T9A1.common.Item;
import T9A1.common.Project;
import T9A1.common.Request;

/**
 * Handles all data requests coming from the client.
 *
 * @author Johannes Liem
 *
 */
public class RequestManager {

	private ICacheManager cacheManager;
	private IConnection connectionManager;
	private int storeNumber;

	public RequestManager(ICacheManager cacheManager,
			IConnection connectionManager) {

		this(cacheManager, connectionManager, 0);
	}

	public RequestManager(ICacheManager cacheManager,
			IConnection connectionManager, int storeNumber) {

		this.cacheManager = cacheManager;
		this.connectionManager = connectionManager;
		this.storeNumber = storeNumber;
	}

	/**
	 * Returns an array of Items that are on sale.
	 *
	 * @return an array of Item objects
	 */
	public Item[] getSaleItems() {
		List<Item> resultList = null;

		Request req = new Request(Request.Type.sale_search);
		req.put(Request.Key.store_id, this.storeNumber);

		resultList = (List<Item>)sendRequest(req);

		// Return null if the server returned null (indicating an error)
		if (resultList == null) return null;

		return resultList.toArray(new Item[0]);
	}

	/**
	 * Search for items that match the specified query.
	 *
	 * @param query the query to use when searching
	 * @return an array of Item objects that match the query
	 */
	public Item[] searchItems(String query) {

		List<Item> resultList = null;

		// Check if the item should be handled by the cache
		if (cacheManager.isCacheHit(query)) {
			resultList = cacheManager.doSearch(query);

		} else {
			Request req = new Request(Request.Type.item_search);
			req.put(Request.Key.store_id, this.storeNumber);
			req.put(Request.Key.query, query);

			// Send request to server
			resultList = (List<Item>)sendRequest(req);

			if (resultList != null) {
				// This result wasn't in the cache (or it's stale), so add it now
				cacheManager.add(query, resultList);
			}
		}

		// Return null if the server returned null (indicating an error)
		if (resultList == null) return null;

		return resultList.toArray(new Item[0]);
	}

	/**
	 * Search for projects that match the specified query.
	 *
	 * @param query the query to use when searching
	 * @return an array of Project objects that match the query
	 */
	public Project[] searchProjects(String query) {
		List<Project> resultList = null;

		Request req = new Request(Request.Type.project_search);
		req.put(Request.Key.store_id, this.storeNumber);
		req.put(Request.Key.query, query);

		resultList = (List<Project>)sendRequest(req);

		// Return null if the server returned null (indicating an error)
		if (resultList == null) {
			return null;
		}

		return resultList.toArray(new Project[0]);
	}

	/**
	 * Retrieve a customer's previously saved list of projects.
	 *
	 * @param email the customer's e-mail
	 * @return an array of Projects the customer had saved
	 */
	public Project[] getProjectList(String email) {
		List<Project> resultList = null;

		Request req = new Request(Request.Type.customer_project_list);
		req.put(Request.Key.store_id, this.storeNumber);
		req.put(Request.Key.email, email);

		resultList = (List<Project>)sendRequest(req);

		// Return null if the server returned null (indicating an error)
		if (resultList == null) return null;

		return resultList.toArray(new Project[0]);
	}

	/**
	 * Retrieve a customer's previously saved list of items.
	 *
	 * @param email the customer's e-mail
	 * @return an array of Items the customer had saved
	 */
	public Item[] getShoppingList(String email) {
		List<Item> resultList = null;

		Request req = new Request(Request.Type.customer_item_list);
		req.put(Request.Key.store_id, this.storeNumber);
		req.put(Request.Key.email, email);

		resultList = (List<Item>)sendRequest(req);

		// Return null if the server returned null (indicating an error)
		if (resultList == null) return null;

		return resultList.toArray(new Item[0]);
	}

	/**
	 * E-mails the project to the specified e-mail address.
	 *
	 * @param project the project to send
	 * @param email the destination email address
	 * @return true if the server request was made successfully, false otherwise
	 * (does not guarantee success of delivery)
	 */
	public boolean emailProject(Project project, String email) {
		// TODO: Not finished yet, need to send e-mail too
		Request r = new Request(Request.Type.project_email);
		r.put(Request.Key.store_id, storeNumber);
		r.put(Request.Key.project, project);

		sendRequest(r);

		return true;
	}

	/**
	 * Helper method to dispatch requests to the server.
	 *
	 * @param request the Request to send
	 * @return the response from the server
	 */
	private Object sendRequest(Request request) {
		Request response = (Request)connectionManager.sendRequest(request);

		if (response != null) {
			return response.get(Request.Key.data);
		}

		return null;
	}

	public int getStoreNumber() {
		return storeNumber;
	}
}
