package T9A1.client.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import T9A1.client.data.InventoryManager;
import T9A1.common.Item;
import T9A1.common.Location;

public class KioskGUI {
	private InventoryManager inventoryManager;
	private Map map;

	private JPanel gui;
	private CardLayout layoutManager;

	private SearchPanel searchPanel;

	private EventHandler eventHandler;

	public KioskGUI(InventoryManager im){
		inventoryManager = im;
		map = new Map();

		JFrame frame = new JFrame("Kiosk");
		Dimension size = new Dimension(1000, 700);
		frame.setMinimumSize(size);
		frame.setLocation(150, 150);

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

	public Map getMap(){
		return map;
	}

	public void search(String s){
		ResultsPanel resultsPanel = null;
		Item[] results = inventoryManager.doSearch(s);
		//if(results != null && results.length > 0){
			resultsPanel = new ResultsPanel(this, s, results);
		/*}else{

			Item item = new Item(s, 2.5,
					"It's a bunch of " + s, true, new Location(60, 10));
			Item[] results2 = {item, item, item};
			resultsPanel = new ResultsPanel(this, s, results2);
		}*/

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
