package T9A1.common;

import java.awt.Image;
import java.io.Serializable;

/**
 * Represents an item.
 *
 * @author Johannes
 *
 */
public class Item implements Serializable, Searchable {
	private String name;
	private double price;
	private String description;
	private boolean inStock;
	private long imageID;
	private Location location;
	private long id;

	public Item(String name, double price, String description, boolean inStock,
			long imageID, Location location) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.inStock = inStock;
		this.imageID = imageID;
		this.location = location;
	}

	public Item(String name, double price, String description, boolean inStock,
			Location location) {
		this(name, price, description, inStock, 0, location);
	}

	public Item() {
		this("", 0, "", false, 0, null);
	}

	public String toString() {
		return name + ": \"" + description + "\" - " + price + "$ - " + (!inStock ? "not " : "") + "in stock - " + location + " - image name : " + imageID + ".gif";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getImageID() {
		return imageID;
	}
	public void setImageID(long imageID) {
		this.imageID = imageID;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isInStock() {
		return inStock;
	}
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
