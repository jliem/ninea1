package T9A1.common;

import java.io.Serializable;

/**
 * Represents a location on the map.
 *
 * @author Catie
 *
 */
public class Location implements Serializable {
	private int aisle;
	private int bin;
	public Location(int aisle, int bin) {
		super();
		this.aisle = aisle;
		this.bin = bin;
	}

	public int getAisle() {
		return aisle;
	}
	public void setAisle(int aisle) {
		this.aisle = aisle;
	}
	public int getBin() {
		return bin;
	}
	public void setBin(int bin) {
		this.bin = bin;
	}

	public String toString(){
		return "Aisle " + aisle + ", Bin " + bin;
	}
}
