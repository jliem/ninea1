package T9A1.common;
/**
 * Class
 * @author Chase
 */

public class ConnectionManager implements IConnectionManager {
	/**
	 * Variables
	 * kioskID: identifies the kiosk
	 * kioskPassword: allows the kiosk to access the server
	 */
	private char[] kioskID;
	private char[] kioskPassword;
	public ConnectionManager(){


	}

	public String[] serializeRequest(){

		return null;
	}




	public void setKioskPassword(char[] kioskPassword) {
		this.kioskPassword = kioskPassword;
	}

	public char[] getKioskPassword() {
		return kioskPassword;
	}

	public void setKioskID(char[] kioskID) {
		this.kioskID = kioskID;
	}

	public char[] getKioskID() {
		return kioskID;
	}




}