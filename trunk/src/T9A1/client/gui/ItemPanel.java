package T9A1.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicBorders;

import T9A1.common.Item;
import T9A1.common.Location;

/**
 * @author Catie Donnelly
 *
 * A long, thin panel containing information on a particular item. Intended
 * for use in a ListPanel as search results, the ItemPanel displays a picture of the
 * item, its name, price (including a discounted price if the item is on sale), stock
 * information, and location within the store.
 */
public class ItemPanel extends JPanel implements SearchResult{

	/** Holds the path for images */
	public static String IMAGE_PATH;
	/** The name of the image to use when no other image is available */
	private final String NO_IMAGE = "no_image";
	/** The filetype of the product images */
	private final String FILETYPE = ".jpg";

	/** The item that the panel represents */
	private Item item;
	/** The main GUI component */
	private KioskGUI gui;

	/** Determines whether or not the panel should react on mouseover */
	private boolean mouseover;

	/**
	 * Creates a new ItemPanel. Sets mouseover to <code>true</code> as a default.
	 * @param gui The main GUI component.
	 * @param item The Item that this panel represents.
	 */
	public ItemPanel(KioskGUI gui, Item item){
		this.item = item;
		this.gui = gui;

		mouseover = true;
		addMouseListener(new MouseOverListener());

		initialize();
	}

	/**
	 * Initializes and configures the ItemPanel components.
	 */
	public void initialize(){
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		Insets insets = new Insets(3, 10, 3, 10);

		setBackground(GUIConstants.LIGHT_ORANGE);
		setPreferredSize(new Dimension(gui.getWidth(), 110));

		ImageContainer i = new ImageContainer();
		con.gridheight = 4;
		con.gridwidth = 1;
		con.gridx = 0;
		con.gridy = 0;
		con.insets = new Insets(5, 70, 5, 40);
		add(i, con);

		JLabel name = new JLabel(item.getName());
		name.setFont(GUIConstants.MEDIUM_FONT);
		con.gridheight = 1;
		con.gridwidth = 4;
		con.gridx = 1;
		con.gridy = 0;
		con.insets = insets;
		con.anchor = GridBagConstraints.WEST;
		add(name, con);

		int x = 1;
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		JLabel price = new JLabel(nf.format(item.getPrice()));
		price.setFont(GUIConstants.SMALL_FONT);
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = x++;
		con.gridy = 1;
		con.insets = insets;
		add(price, con);

		if(item.getSalePrice() > 0){
			price.setText("<html><strike>" + price.getText() + "</strike></html>");
			JLabel sale = new JLabel("On Sale for " + nf.format(item.getSalePrice()));
			sale.setFont(GUIConstants.SMALL_FONT);
			sale.setForeground(Color.red);
			con.gridheight = 1;
			con.gridwidth = 1;
			con.gridx = x++;
			con.gridy = 1;
			con.insets = insets;
			add(sale, con);
		}

		JLabel inStock;
		if(item.isInStock()){
			inStock = new JLabel("In Stock");
			inStock.setForeground(Color.green);
		}else{
			inStock = new JLabel("Out of Stock");
			inStock.setForeground(Color.red);
		}
		inStock.setFont(GUIConstants.SMALL_FONT);
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = x++;
		con.gridy = 1;
		con.insets = insets;
		add(inStock, con);

		JLabel location = new JLabel(item.getLocation().toString());
		location.setFont(GUIConstants.SMALL_FONT);
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = x;
		con.gridy = 1;
		con.insets = insets;
		add(location, con);

		JLabel description = new JLabel(item.getDescription());
		description.setFont(GUIConstants.SMALL_FONT);
		con.gridheight = 1;
		con.gridwidth = 7;
		con.gridx = 1;
		con.gridy = 2;
		con.insets = insets;
		add(description, con);

		con.gridheight = 3;
		con.fill = GridBagConstraints.HORIZONTAL;
		con.gridwidth = 1;
		con.weightx = 1;
		con.gridx = 4;
		con.gridy = 0;
		con.insets = insets;
		add(Box.createGlue(), con);
	}

	/**
	 * Instructs the main GUI component to display the full panel associated with this object's item.
	 */
	private void showItem(){
		gui.showItem(this);
	}

	/**
	 * Provides the 
	 * @return
	 */
	public Item getItem(){
		return item;
	}

	public void setMouseover(boolean b){
		mouseover = b;
		if(b)
			setBackground(GUIConstants.LIGHT_ORANGE);
		else setBackground(GUIConstants.ORANGE);
		this.invalidate();
	}

	public ItemPanel copy(){
		return new ItemPanel(gui, item);
	}

	public JPanel getListPanel() {
		return this;
	}

	/**
	 * MouseListener for the user to chose an item.
	 * @author Catie
	 */
	private class MouseOverListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			if(mouseover)
				showItem();
		}

		public void mouseEntered(MouseEvent e) {
			if(mouseover)
				setBackground(GUIConstants.DARK_ORANGE);
		}

		public void mouseExited(MouseEvent e) {
			if(mouseover)
				setBackground(GUIConstants.LIGHT_ORANGE);
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}

	/**
	 * Displays the product's image.
	 * @author Catie
	 */
	private class ImageContainer extends JPanel{
		ImageIcon image;

		public ImageContainer(){
			this.setMinimumSize(new Dimension(100, 100));
			this.setPreferredSize(new Dimension(100, 100));

			image = new ImageIcon(IMAGE_PATH + item.getImageID() + FILETYPE);

			if(item.getImageID() == 0 || image.getIconHeight() < 0)
				image = new ImageIcon(IMAGE_PATH + NO_IMAGE + FILETYPE);
		}

		public void paintComponent(Graphics g){
			image.paintIcon(this, g, 0, 0);
		}
	}
}
