package T9A1.server;

public final class KioskTestServerFactory {

	private static KioskServer serverInstance;

	private KioskTestServerFactory() {

	}

	public static KioskServer getServerInstance() {
		if (serverInstance == null) {
			serverInstance = new KioskServer(DBManager.getDBM());
		}

		return serverInstance;
	}
}
