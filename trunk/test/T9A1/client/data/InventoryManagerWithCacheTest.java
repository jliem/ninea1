package T9A1.client.data;

import T9A1.client.KioskClientFactory;

/**
 * Tests the InventoryManager subsystem without the use of the CacheManager.
 *
 * @author Johannes Liem
 *
 */
public class InventoryManagerWithCacheTest extends InventoryManagerTest {

	public InventoryManagerWithCacheTest() {
		kcf = new KioskClientFactory(null);
	}

	public void setUp() {
		super.setUp();
		im = ((KioskClientFactory)kcf).createInventoryManager();
	}
}
