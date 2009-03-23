package T9A1.common;

import junit.framework.TestCase;

public class ConnectionManagerTest extends TestCase {

	private ConnectionManager cm;

	public void setUp() {
		cm = new ConnectionManager();
	}

	public void testSendRequest() {
		cm.sendRequest("Sucker sauce");
	}
}
