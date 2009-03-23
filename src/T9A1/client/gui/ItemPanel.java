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
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import T9A1.common.Item;
import T9A1.common.Location;

public class ItemPanel{

	private Item item;
	private Map map;

	private KioskGUI gui;

	private JPanel compactPanel, fullPanel;

	public ItemPanel(KioskGUI g, Item i){
		item = i;
		gui = g;
		map = gui.getMap();

		initializeCompactPanel();
	}

	public JPanel getCompactPanel(){
		return compactPanel;
	}

	public JPanel getFullPanel(){
		if(fullPanel == null)
			initializeFullPanel();

		return fullPanel;
	}

	private void initializeCompactPanel(){
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		Insets insets = new Insets(2, 2, 2, 2);

		JButton i = new JButton(new ImageIcon("client/gui/images/no_image.gif"));
		i.addActionListener(new ShowItemListener());
		con.gridheight = 2;
		con.gridwidth = 1;
		con.gridx = 0;
		con.gridy = 0;
		con.insets = insets;
		panel.add(i, con);

		JLabel name = new JLabel(item.getName());
		con.gridheight = 1;
		con.gridwidth = 3;
		con.gridx = 1;
		con.gridy = 0;
		con.insets = insets;
		panel.add(name, con);

		JLabel price = new JLabel("$" + Double.toString(item.getPrice()));
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 1;
		con.gridy = 1;
		con.insets = insets;
		panel.add(price, con);

		JLabel inStock;
		if(item.isInStock()){
			inStock = new JLabel("In Stock");
			inStock.setForeground(Color.green);
		}else{
			inStock = new JLabel("Out of Stock");
			inStock.setForeground(Color.red);
		}
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 2;
		con.gridy = 1;
		con.insets = insets;
		panel.add(inStock, con);

		JLabel location = new JLabel("Aisle " + item.getLocation().getAisle() + " - Bin " + item.getLocation().getBin());
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 3;
		con.gridy = 1;
		con.insets = insets;
		panel.add(location, con);

		compactPanel = panel;
	}

	private void initializeFullPanel(){
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		Insets insets = new Insets(2, 2, 2, 2);

		ImageContainer i = new ImageContainer(item.getImage());
		con.gridheight = 3;
		con.gridwidth = 1;
		con.gridx = 0;
		con.gridy = 0;
		con.insets = insets;
		//con.fill = GridBagConstraints.BOTH;
		//con.ipadx = 100;
		//con.ipady = 100;
		panel.add(i, con);

		JLabel name = new JLabel(item.getName());
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 1;
		con.gridy = 0;
		con.insets = insets;
		con.ipadx = 0;
		con.ipady = 0;
		panel.add(name, con);

		JLabel price = new JLabel("$" + Double.toString(item.getPrice()));
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 1;
		con.gridy = 1;
		con.insets = insets;
		panel.add(price, con);

		JLabel inStock;
		if(item.isInStock()){
			inStock = new JLabel("In Stock");
			inStock.setForeground(Color.green);
		}else{
			inStock = new JLabel("Out of Stock");
			inStock.setForeground(Color.red);
		}
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 2;
		con.gridy = 1;
		con.insets = insets;
		panel.add(inStock, con);

		JLabel location = new JLabel("Aisle " + item.getLocation().getAisle() + " - Bin " + item.getLocation().getBin());
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 3;
		con.gridy = 1;
		con.insets = insets;
		panel.add(location, con);

		JLabel description = new JLabel(item.getDescription());
		con.gridheight = 1;
		con.gridwidth = 6;
		con.gridx = 1;
		con.gridy = 2;
		con.insets = insets;
		con.anchor = GridBagConstraints.WEST;
		panel.add(description, con);

		MapPanel mapPanel = new MapPanel();
		con.gridheight = 1;
		con.gridwidth = 7;
		con.gridx = 0;
		con.gridy = 3;
		con.insets = insets;
		con.anchor = GridBagConstraints.CENTER;
		panel.add(mapPanel, con);

		JButton back = new JButton("Back to Results");
		back.addActionListener(new ReturnResultsListener());
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 5;
		con.gridy = 4;
		con.insets = insets;
		con.anchor = GridBagConstraints.EAST;
		panel.add(back, con);

		JButton clear = new JButton("New Search");
		clear.addActionListener(new NewSearchListener());
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 6;
		con.gridy = 4;
		con.insets = insets;
		con.anchor = GridBagConstraints.EAST;
		panel.add(clear, con);

		fullPanel = panel;
	}

	private void showItem(){
		gui.showItem(this);
	}

	private class NewSearchListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			gui.newSearch();
		}
	}

	private class ReturnResultsListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			gui.backToResults();
		}
	}

	private class ShowItemListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			showItem();
		}
	}

	private class ImageContainer extends JPanel{
		String NO_IMAGE = "client/gui/images/no_image.gif";
		ImageIcon image;

		public ImageContainer(Image i){
			this.setMinimumSize(new Dimension(100, 100));
			this.setPreferredSize(new Dimension(100, 100));

			if(i instanceof Image){
				image = new ImageIcon(i);
			}else
				image = new ImageIcon(NO_IMAGE);
		}

		public void paintComponent(Graphics g){
			image.paintIcon(this, g, 0, 0);
		}
	}

	private class MapPanel extends JPanel{

		private ImageIcon mapImage;

		public MapPanel(){
			mapImage = map.getMap();

			this.setMinimumSize(new Dimension(mapImage.getIconWidth(), mapImage.getIconHeight()));
			this.setPreferredSize(new Dimension(mapImage.getIconWidth(), mapImage.getIconHeight()));
		}

		public void paintComponent(Graphics g){
			mapImage.paintIcon(this, g, 0, 0);

			Point p = map.getCoordinates(item.getLocation());
			if(p == null) return;
			int x = (int)p.getX();
			int y = (int)p.getY();
			drawStar(g, x, y);
		}

		public void drawStar(Graphics g, int x, int y){
			g.setColor(Color.yellow);
			g.fillOval(x - 5, y - 5, 10, 10);
		}
	}
}
