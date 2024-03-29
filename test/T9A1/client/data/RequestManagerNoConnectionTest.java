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

	/**
	 * Runs a search that is expected to return no results.
	 */
	public void testEmptySearch() {
		Request r = new Request(Request.Type.item_search);
		r.put(Request.Key.data, new ArrayList());
		mockConn.setResponse(r);

		super.testEmptySearch();
	}
}
