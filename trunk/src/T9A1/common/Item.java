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

	public Item(String name, Image image, double price, String description, boolean inStock) {
		this.name = name;
		this.image = image;
		this.price = price;
		this.description = description;
		this.inStock = inStock;
	}

	public Item(String name, double price, String description, boolean inStock) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.inStock = inStock;
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
}
