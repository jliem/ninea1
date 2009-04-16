package T9A1.client.data;

import T9A1.common.Connection;

/**
 * Tests the RequestManager subsystem with a working CacheManager and
 * ConnectionManager.
 *
 * @author Johannes Liem
 *
 */
public class RequestManagerFullTest extends RequestManagerTest {

	public void setUp() {
		super.setUp();
		requestManager = new RequestManager(new CacheManager(), new Connection());
	}
}
