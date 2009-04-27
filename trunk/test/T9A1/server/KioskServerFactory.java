package T9A1.server;

/**
 * Factory to make kiosk servers.
 *
 * @author Johannes Liem
 *
 */
public final class KioskServerFactory {

	private static KioskServer serverInstance;

	private KioskServerFactory() {

	}

	public static KioskServer getServerInstance() {
		if (serverInstance == null) {
			serverInstance = new KioskServer(DBManager.getDBM());
		}

		return serverInstance;
	}
}
