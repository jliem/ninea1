package T9A1.client.data;

import T9A1.common.Item;
import T9A1.server.KioskServer;
import T9A1.server.KioskServerFactory;

import junit.framework.TestCase;

/**
 * Parent class for all inventory manager tests.
 * @author JL
 *
 */
public abstract class InventoryManagerTest extends TestCase {

	protected RequestManager im;

	/**
	 * Initialize a server. The same server instance will be used for all
	 * tests. Subclasses should call super.setUp() to initialize the server
	 * or omit it to conduct tests without a working server.
	 */
	public void setUp() {
		KioskServer ks = KioskServerFactory.getServerInstance();
	}

	/**
	 * Runs a search that is expected to return no results.
	 */
	public void testEmptySearch() {
		Item[] arr = im.searchItems("thisqueryshouldreturnnoresults ***");

		assertNotNull("Zero length array expected, got null array", arr);
		assertEquals(arr.length, 0);
	}
}
