package T9A1.common;

import java.util.ArrayList;
import java.util.List;

public class MockConnectionManager implements IConnectionManager {

	private List<Item> response = new ArrayList<Item>();

	public List<Item> sendRequest(String request) {
		return response;
	}

	protected List<Item> getResponse() {
		return response;
	}

	protected void setResponse(List<Item> response) {
		this.response = response;
	}
}
