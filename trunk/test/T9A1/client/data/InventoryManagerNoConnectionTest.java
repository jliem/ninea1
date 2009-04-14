package T9A1.client.data;

import T9A1.common.MockConnection;

/**
 * Tests the InventoryManager subsystem without the use of the
 * ConnectionManager.
 *
 * @author Johannes Liem
 *
 */
public class InventoryManagerNoConnectionTest extends InventoryManagerTest {

	public void setUp() {
		super.setUp();
		im = new RequestManager(new CacheManager(),
				new MockConnection());
	}
}
