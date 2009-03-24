package T9A1.client.data;

import T9A1.client.KioskClientFactory;
import T9A1.client.KioskClientMockFactory;

/**
 * Tests the InventoryManager subsystem without the use of the CacheManager.
 *
 * @author Johannes Liem
 *
 */
public class InventoryManagerNoCacheTest extends InventoryManagerTest {

	public InventoryManagerNoCacheTest() {
		kcf = new KioskClientMockFactory(null);
	}

	public void setUp() {
		super.setUp();
		im = ((KioskClientMockFactory)kcf).createInventoryManagerWithoutCache();
	}
}
