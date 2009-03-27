package T9A1.client.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.xml.sax.XMLReader;

import T9A1.client.data.InventoryManager;
import T9A1.common.Item;
import T9A1.common.Location;

/**
 * The main GUI class for the kiosk.
 * @author Catie
 */
public class KioskGUI {
	/** The InventoryManager that performs searches. */
	private InventoryManager inventoryManager;
	/** The map of the store. */
	private Map map;

	/** The panel that displays the GUI */
	private JPanel gui;
	/** The layout manager for the GUI */
	private CardLayout layoutManager;

	/** The panel used to search for items. */
	private SearchPanel searchPanel;

	/**
	 * Creates and displays a new KioskGUI.
	 * @param im the InventoryManager associated with the kiosk
	 */
	public KioskGUI(InventoryManager im){
		inventoryManager = im;
		map = new Map(im.getStoreNumber());

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
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	/**
	 * Returns the width of the GUI.
	 * @return the width of the GUI
	 */
	public int getWidth(){
		return gui.getWidth();
	}

	/**
	 * Returns the Map associated with the current GUI.
	 * @return a Map object that represents the store map
	 */
	public Map getMap(){
		return map;
	}

	/**
	 * Runs a search on a given term.
	 * @param s the term to be searched for
	 */
	public void search(String s){
		ResultsPanel resultsPanel = null;
		Item[] results = inventoryManager.doSearch(s);
		resultsPanel = new ResultsPanel(this, s, results);

		gui.add(resultsPanel, "RESULTS");
		layoutManager.show(gui, "RESULTS");
	}

	/**
	 * Displays a chosen item.
	 * @param ip the ItemPanel for the item to be displayed
	 */
	public void showItem(ItemPanel ip){
		gui.add(ip.getFullPanel(), "ITEM");
		layoutManager.show(gui, "ITEM");
	}

	/**
	 * Clears out all results and brings up the search page.
	 */
	public void newSearch(){
		gui.removeAll();
		gui.add(searchPanel, "SEARCH");
	}

	/**
	 * Displays the current results page.
	 */
	public void backToResults(){
		layoutManager.show(gui, "RESULTS");
	}
}
