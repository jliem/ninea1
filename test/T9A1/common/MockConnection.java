package T9A1.common;

import java.util.ArrayList;
import java.util.List;

import T9A1.common.Request.Type;

public class MockConnection implements IConnection {

	private Object response = new ArrayList<Item>();

	public Object sendRequest(Type type, Object data) {
		return response;
	}

	public Object sendRequest(Request request) {
		return response;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
}
