package T9A1.common;

import java.util.List;

public interface IConnectionManager {

	/**
	 * Sends a request to the server.
	 *
	 * @param request the request to send
	 * @return a list of results
	 */
	public List<Item> sendRequest(String request);
}
