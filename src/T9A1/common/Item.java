package T9A1.common;

import java.awt.Image;

/**
 * Represents an item.
 *
 * @author Johannes
 *
 */
public class Item {
	private String name;
	private double price;
	private String description;
	private boolean inStock;
	private Image image;
	private Location location;

	public Item(String name, double price, String description, boolean inStock,
			Image image, Location location) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.inStock = inStock;
		this.image = image;
		this.location = location;
	}

	public Item(String name, double price, String description, boolean inStock,
			Location location) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.inStock = inStock;
		this.location = location;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
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

}
