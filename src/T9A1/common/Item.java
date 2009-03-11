package T9A1.common;

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
