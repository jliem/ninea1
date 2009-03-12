package T9A1.client.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import T9A1.client.data.InventoryManager;
import T9A1.common.Item;
import T9A1.common.Location;

public class KioskGUI {
	private InventoryManager inventoryManager;
	private JPanel gui;
	private CardLayout layoutManager;

	private SearchPanel searchPanel;

	private EventHandler eventHandler;

	public KioskGUI(InventoryManager im){
		inventoryManager = im;

		JFrame frame = new JFrame("Kiosk");

		layoutManager = new CardLayout();
		gui = new JPanel(layoutManager);

		eventHandler = new EventHandler();

		searchPanel = new SearchPanel(this);
		gui.add(searchPanel, "SEARCH");

		//layoutManager.show(gui, "SEARCH");

		frame.add(gui);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public void search(String s){
		//Item[] results = inventoryManager.doSearch(s);
		Item item = new Item(s, 2.5,
				"It's a bunch of " + s, true, new Location(13, 4));
		Item[] results = {item, item, item};
		ResultsPanel resultsPanel = new ResultsPanel(this, s, results);
		gui.add(resultsPanel, "RESULTS");
		layoutManager.show(gui, "RESULTS");
	}

	public void showItem(ItemPanel ip){
		gui.add(ip.getFullPanel(), "ITEM");
		layoutManager.show(gui, "ITEM");
	}

	public void newSearch(){
		gui.removeAll();
		gui.add(searchPanel, "SEARCH");
	}

	public void backToResults(){
		layoutManager.show(gui, "RESULTS");
	}

	private class EventHandler implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
