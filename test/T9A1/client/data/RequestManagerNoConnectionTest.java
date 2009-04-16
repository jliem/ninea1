package T9A1.client.data;

import java.util.ArrayList;

import T9A1.common.MockConnection;
import T9A1.common.Request;

/**
 * Tests the RequestManager subsystem without the use of the
 * ConnectionManager.
 *
 * @author Johannes Liem
 *
 */
public class RequestManagerNoConnectionTest extends RequestManagerTest {

	private MockConnection mockConn;
	public void setUp() {
		super.setUp();
		mockConn = new MockConnection();
		requestManager = new RequestManager(new CacheManager(), mockConn);
	}

	public void testEmptySearch() {
		mockConn.setResponse(new Request(Request.Type.item_search,
				new ArrayList()));

		super.testEmptySearch();
	}
}
