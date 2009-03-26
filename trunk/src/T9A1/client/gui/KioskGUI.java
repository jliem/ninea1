package T9A1.client.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.xml.sax.XMLReader;

import T9A1.client.data.InventoryManager;
import T9A1.common.Item;
import T9A1.common.Location;

public class KioskGUI {
	private InventoryManager inventoryManager;
	private Map map;

	private JPanel gui;
	private CardLayout layoutManager;

	private SearchPanel searchPanel;

	public KioskGUI(InventoryManager im){
		inventoryManager = im;
		map = new Map(1);

		JFrame frame = new JFrame("Kiosk");
		Dimension size = new Dimension(1000, 700);
		frame.setMinimumSize(size);
		frame.setLocation(150, 150);

		layoutManager = new CardLayout();
		gui = new JPanel(layoutManager);

		searchPanel = new SearchPanel(this);
		gui.add(searchPanel, "SEARCH");

		frame.add(gui);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public int getWidth(){
		return gui.getWidth();
	}

	public Map getMap(){
		return map;
	}

	public void search(String s){
		ResultsPanel resultsPanel = null;
		Item[] results = inventoryManager.doSearch(s);
		resultsPanel = new ResultsPanel(this, s, results);

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
}
