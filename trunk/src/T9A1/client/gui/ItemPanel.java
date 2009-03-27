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

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicBorders;

import T9A1.common.Item;
import T9A1.common.Location;

/**
 * A class for getting different item displays. The compact panel can be
 * used in a list of items, while the full panel displays the item's information
 * along with the item's location plotted on a store map.
 *
 * @author Catie
 */
public class ItemPanel extends JPanel{

	/** The location of the product images. */
	private final String IMAGE_PATH = "client/gui/images/";
	/** The name of the default product image. */
	private final String NO_IMAGE = "no_image";
	/** The filetype of the product images. */
	private final String FILETYPE = ".jpg";

	/** The item that the panels represent. */
	private Item item;
	/** The main GUI component. */
	private KioskGUI gui;

	private boolean mouseover;

	/**
	 * Creates a new ItemPanel and initializes the compact panel.
	 * @param g The main GUI component.
	 * @param i The Item that this panel represents.
	 */
	public ItemPanel(KioskGUI gui, Item item){
		this.item = item;
		this.gui = gui;
		mouseover = true;

		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		Insets insets = new Insets(3, 10, 3, 10);

		addMouseListener(new MouseOverListener());
		setBackground(GUIColors.LIGHT_ORANGE);
		setPreferredSize(new Dimension(gui.getWidth() - 10, 110));

		ImageContainer i = new ImageContainer();
		con.gridheight = 4;
		con.gridwidth = 1;
		con.gridx = 0;
		con.gridy = 0;
		con.insets = new Insets(5, 70, 5, 40);
		add(i, con);

		JLabel name = new JLabel(item.getName());
		name.setFont(GUIFonts.MEDIUM);
		con.gridheight = 1;
		con.gridwidth = 3;
		con.gridx = 1;
		con.gridy = 0;
		con.insets = insets;
		con.anchor = GridBagConstraints.WEST;
		add(name, con);

		JLabel price = new JLabel("$" + Double.toString(item.getPrice()));
		price.setFont(GUIFonts.SMALL);
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 1;
		con.gridy = 1;
		con.insets = insets;
		add(price, con);

		JLabel inStock;
		if(item.isInStock()){
			inStock = new JLabel("In Stock");
			inStock.setForeground(Color.green);
		}else{
			inStock = new JLabel("Out of Stock");
			inStock.setForeground(Color.red);
		}
		inStock.setFont(GUIFonts.SMALL);
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 2;
		con.gridy = 1;
		con.insets = insets;
		add(inStock, con);

		JLabel location = new JLabel(item.getLocation().toString());
		location.setFont(GUIFonts.SMALL);
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 3;
		con.gridy = 1;
		con.insets = insets;
		add(location, con);

		JLabel description = new JLabel(item.getDescription());
		description.setFont(GUIFonts.SMALL);
		con.gridheight = 1;
		con.gridwidth = 6;
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
	 * Instructs the main GUI component to display the image.
	 */
	private void showItem(){
		gui.showItem(this);
	}

	public Item getItem(){
		return item;
	}

	public void setMouseover(boolean b){
		mouseover = b;
		if(b)
			setBackground(GUIColors.LIGHT_ORANGE);
		else setBackground(GUIColors.ORANGE);
		this.invalidate();
	}

	public ItemPanel copy(){
		return new ItemPanel(gui, item);
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
				setBackground(GUIColors.DARK_ORANGE);
		}

		public void mouseExited(MouseEvent e) {
			if(mouseover)
				setBackground(GUIColors.LIGHT_ORANGE);
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
