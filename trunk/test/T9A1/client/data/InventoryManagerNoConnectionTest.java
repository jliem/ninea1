package T9A1.client.data;

import T9A1.common.MockConnectionManager;

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
		im = new InventoryManager(new CacheManager(),
				new MockConnectionManager());
	}
}
