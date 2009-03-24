package T9A1.server;

public final class KioskTestServer {

	private static KioskServer serverInstance;

	private KioskTestServer() {

	}

	public static KioskServer getServerInstance() {
		if (serverInstance == null) {
			serverInstance = new KioskServer();
			serverInstance.main(null);
		}

		return serverInstance;
	}
}
