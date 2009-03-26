package T9A1.client.data;

import T9A1.client.IKioskClientFactory;
import T9A1.client.KioskClientFactory;
import T9A1.common.Item;
import T9A1.server.KioskServer;
import T9A1.server.KioskServerFactory;

import junit.framework.TestCase;

public abstract class InventoryManagerTest extends TestCase {

	protected IKioskClientFactory kcf;
	protected InventoryManager im;

	public void setUp() {
		KioskServer ks = KioskServerFactory.getServerInstance();
	}

	/**
	 * Runs a search that is expected to return no results.
	 */
	public void testEmptySearch() {
		Item[] arr = im.doSearch("thisqueryshouldreturnnoresults ***");

		assertNotNull("Zero length array expected, got null array", arr);
		assertEquals(arr.length, 0);
	}
}
