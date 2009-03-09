package T9A1.common;

public interface IConnectionManager {

	public String[] serializeRequest();

	public void setKioskPassword(char[] kioskPassword);

	public char[] getKioskPassword();

	public void setKioskID(char[] kioskID);

	public char[] getKioskID();
}
