package T9A1.common;

import java.util.List;

public interface IConnectionManager {

	public Object sendRequest(Request.Type type, Object data);

	/**
	 * Send request method for client, that sends then blocks for reponse
	 * @author Chase
	 */
	public Object sendRequest(Request request);
}
