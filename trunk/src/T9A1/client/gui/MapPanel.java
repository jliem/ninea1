package T9A1.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

	/** The main GUI component */
	private KioskGUI gui;
	/** A digital representation of the store map */
	private Map map;
	/** The ItemPanel representing the Item being plotted */
	private ItemPanel itemPanel;

	/**
	 * Initializes the MapPanel object
	 * @param gui The main GUi component
	 * @param itemPanel The ItemPanel representing the Item being plotted
	 */
	public MapPanel(KioskGUI gui, ItemPanel itemPanel){
		this.gui = gui;
		this.map = gui.getMap();
		this.itemPanel = itemPanel;

		this.setBackground(GUIConstants.ORANGE);
		this.setLayout(new BorderLayout());

		addItemPanel();

		MapDisplayPanel mapPanel = new MapDisplayPanel();
		add(mapPanel, BorderLayout.CENTER);

		addButtonPanel();
	}

	/**
	 * Adds the ItemPanel itemPanel to the top of this panel to display pertinent information.
	 */
	public void addItemPanel(){
		JPanel top = new JPanel(new GridBagLayout());
		top.setBackground(GUIConstants.ORANGE);
		GridBagConstraints con = new GridBagConstraints();
		Insets insets = new Insets(40, 20, 10, 0);
		con.insets = insets;
		top.add(itemPanel, con);
		con.gridx = 1;
		con.weightx = 1;
		top.add(Box.createGlue(), con);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * Adds Back to Results and New Search buttons to the bottom of this panel.
	 */
	public void addButtonPanel(){
		GridBagConstraints con = new GridBagConstraints();
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		Insets insets = new Insets(10, 10, 10, 10);
		buttonPanel.setBackground(GUIConstants.ORANGE);

		JButton back = new JButton("<html><center>Back to<br />Results</center></html>");
		back.addActionListener(new ReturnResultsListener());
		back.setFont(GUIConstants.MEDIUM_FONT);
		con.weightx = 0;
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 1;
		con.gridy = 0;
		con.ipadx = 20;
		con.ipady = 20;
		con.insets = insets;
		con.anchor = GridBagConstraints.EAST;
		buttonPanel.add(back, con);

		JButton clear = new JButton("<html><center>New<br />Search</center></html>");
		clear.addActionListener(new NewSearchListener());
		clear.setFont(GUIConstants.MEDIUM_FONT);
		con.gridx = 2;
		con.gridy = 0;
		buttonPanel.add(clear, con);

		con.gridx = 0;
		con.weightx = 1;
		buttonPanel.add(Box.createGlue(), con);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Listener for the New Search button.
	 * @author Catie
	 */
	private class NewSearchListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			gui.newSearch();
		}
	}

	/**
	 * Listener for the Return to Results button.
	 * @author Catie
	 */
	private class ReturnResultsListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			itemPanel.setMouseover(true);
			itemPanel.validate();
			gui.backToResults();
		}
	}

	/**
	 * A panel that displays the map and plots the Item's location.
	 * @author Catie
	 */
	private class MapDisplayPanel extends JPanel{
		/** The imagine representing the store map */
		private ImageIcon mapImage;

		/**
		 * Initializes the MapDisplayPanel.
		 */
		public MapDisplayPanel(){
			mapImage = map.getMap();

			this.setMinimumSize(new Dimension(mapImage.getIconWidth(), mapImage.getIconHeight()));
			this.setPreferredSize(new Dimension(mapImage.getIconWidth(), mapImage.getIconHeight()));
		}

		/**
		 * Overrides JPanel's paintComponent method. Draws the map and plots the item location.
		 */
		public void paintComponent(Graphics g){
			int startx = (getWidth() / 2) - (mapImage.getIconWidth() / 2);
			int starty = (getHeight() / 2) - (mapImage.getIconHeight() / 2);

			mapImage.paintIcon(this, g, startx, starty);

			Point p = map.getCoordinates(itemPanel.getItem().getLocation());
			if(p == null) return;
			int x = startx + (int)p.getX();
			int y = starty + (int)p.getY();
			g.setColor(Color.yellow);
			g.fillOval(x - 5, y - 5, 10, 10);
		}
	}

}
