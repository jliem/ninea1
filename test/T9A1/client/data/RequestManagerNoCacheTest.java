package T9A1.client.data;

import T9A1.common.Connection;

/**
 * Tests the RequestManager subsystem without the use of the CacheManager.
 *
 * @author Johannes Liem
 *
 */
public class RequestManagerNoCacheTest extends RequestManagerTest {

	public void setUp() {
		super.setUp();
		requestManager = new RequestManager(new MockCacheManager(),
				new Connection());
	}
}
