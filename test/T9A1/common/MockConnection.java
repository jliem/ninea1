package T9A1.common;

import java.util.ArrayList;
import java.util.List;

import T9A1.common.Request.Type;

/**
 * Mock implementation of the connection class.
 *
 * @author Johannes Liem
 *
 */
public class MockConnection implements IConnection {

	private Object response = null;

	/**
	 * {@inheritDoc}
	 */
	public Object sendRequest(Type type, Object data) {
		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object sendRequest(Request request) {
		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getResponse() {
		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setResponse(Object response) {
		this.response = response;
	}
}
