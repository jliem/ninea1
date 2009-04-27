package T9A1.client.data;

import T9A1.common.Item;
import T9A1.common.Project;
import T9A1.server.KioskServer;
import T9A1.server.KioskServerFactory;

import junit.framework.TestCase;

/**
 * Parent class for all request manager tests.
 * @author Johannes Liem
 *
 */
public abstract class RequestManagerTest extends TestCase {

	protected RequestManager requestManager;
	protected String email = "test@cs4911seniordesign.abc";

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
		Item[] arr = requestManager.searchItems("thisqueryshouldreturnnoresults ***");

		assertNotNull("Zero length array expected, got null array", arr);
		assertEquals(arr.length, 0);
	}

	/**
	 * Tests retrieving a project list.
	 */
	public void testGetProjectList() {
		Project[] expected = null;

		Project[] actual = requestManager.getProjectList(email);

		this.assertEquals(expected, actual);
	}

	/**
	 * Tests retrieving an item list.
	 */
	public void testGetShoppingList() {
		Item[] expected = null;

		Item[] actual = requestManager.getShoppingList(email);

		this.assertEquals(expected, actual);
	}

	/**
	 * Tests e-mailing a project.
	 */
	public void testEmailProject() {
		Project project = new Project();

		assertTrue(requestManager.emailProject(project, email));
	}
}
