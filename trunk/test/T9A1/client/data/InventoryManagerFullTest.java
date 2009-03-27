package T9A1.client.data;

import T9A1.common.Connection;

/**
 * Tests the InventoryManager subsystem with a working CacheManager and
 * ConnectionManager.
 *
 * @author Johannes Liem
 *
 */
public class InventoryManagerFullTest extends InventoryManagerTest {

	public void setUp() {
		super.setUp();
		im = new InventoryManager(new CacheManager(), new Connection());
	}
}
