package T9A1.client.data;

import T9A1.common.ConnectionManager;

/**
 * Tests the InventoryManager subsystem without the use of the CacheManager.
 *
 * @author Johannes Liem
 *
 */
public class InventoryManagerNoCacheTest extends InventoryManagerTest {

	public void setUp() {
		super.setUp();
		im = new InventoryManager(new MockCacheManager(),
				new ConnectionManager());
	}
}