package T9A1.client.gui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import T9A1.common.Item;
import T9A1.common.Location;

public class ItemPanel{

	private Item item;

	private KioskGUI gui;

	private JPanel compactPanel, fullPanel;

	public ItemPanel(KioskGUI g, Item i){
		item = i;
		gui = g;

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

		//ImageContainer i = new ImageContainer(item.getImage());
		JButton i = new JButton("IMAGE");
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
		if(item.isInStock())
			inStock = new JLabel("In Stock");
		else
			inStock = new JLabel("Out of Stock");
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
		panel.add(name, con);

		JLabel inStock;
		if(item.isInStock())
			inStock = new JLabel("In Stock");
		else
			inStock = new JLabel("Out of Stock");
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 1;
		con.gridy = 2;
		con.insets = insets;
		panel.add(inStock, con);

		JLabel location = new JLabel("Aisle " + item.getLocation().getAisle() + " - Bin " + item.getLocation().getBin());
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 1;
		con.gridy = 3;
		con.insets = insets;
		panel.add(location, con);

		JButton back = new JButton("Back to Results");
		back.addActionListener(new ReturnResultsListener());
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 0;
		con.gridy = 1;
		con.insets = insets;
		panel.add(back, con);

		JButton clear = new JButton("New Search:");
		clear.addActionListener(new NewSearchListener());
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 0;
		con.gridy = 2;
		con.insets = insets;
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
		String NO_IMAGE = "NoImage_small.jpg";
		ImageIcon image;

		public ImageContainer(Image i){
			if(i instanceof Image){
				image = new ImageIcon(i);
			}else
				image = new ImageIcon(NO_IMAGE);
		}

		public void paintComponent(Graphics g){
			image.paintIcon(this, g, 0, 0);
		}
	}
}
