package T9A1.common;

import java.util.List;

/**
 * Interface for a connection to the server.
 *
 * @author Johannes Liem
 *
 */
public interface IConnection {

	/**
	 * Sends a request from the client to the server, then blocks until the server
	 * responds.
	 *
	 * @param request the Request being sent from the client to the server
	 * @return the response from the server
	 */
	public Object sendRequest(Request request);
}
