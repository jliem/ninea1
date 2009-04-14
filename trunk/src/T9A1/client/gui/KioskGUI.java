package T9A1.client.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.xml.sax.XMLReader;

import T9A1.client.data.RequestManager;
import T9A1.common.Item;
import T9A1.common.Location;

/**
 * The main GUI class for the kiosk.
 * @author Catie
 */
public class KioskGUI {
	/** The InventoryManager that performs searches. */
	private RequestManager inventoryManager;
	/** The map of the store. */
	private Map map;

	/** The panel that displays the GUI */
	private JPanel gui;
	/** The layout manager for the GUI */
	private CardLayout layoutManager;

	/** The panel used to search for items. */
	private ItemSearchPanel itemSearchPanel;
	/** The panel used to search for projects. */
	private ProjectSearchPanel projectSearchPanel;
	/** The current item results panel. */
	private ItemResultsPanel itemResultsPanel;
	/** The current project results panel. */
	private ProjectResultsPanel projectResultsPanel;

	/**
	 * Creates and displays a new KioskGUI.
	 * @param im the InventoryManager associated with the kiosk
	 */
	public KioskGUI(RequestManager im){
		inventoryManager = im;
		map = new Map(im.getStoreNumber());

		JFrame frame = new JFrame("Kiosk");
		Dimension size = new Dimension(1000, 700);
		frame.setMinimumSize(size);
		frame.setLocation(150, 150);

		layoutManager = new CardLayout();
		gui = new JPanel(layoutManager);

		itemSearchPanel = new ItemSearchPanel(this);
		gui.add(itemSearchPanel, GUIConstants.ITEM_SEARCH);
		projectSearchPanel = new ProjectSearchPanel(this);
		gui.add(projectSearchPanel, GUIConstants.PROJECT_SEARCH);

		layoutManager.show(gui, GUIConstants.ITEM_SEARCH);

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
	public void itemSearch(String s){
		Item[] results = inventoryManager.searchItems(s);
		itemResultsPanel = new ItemResultsPanel(this, s, results);

		gui.add(itemResultsPanel, GUIConstants.ITEM_RESULTS);
		layoutManager.show(gui, GUIConstants.ITEM_RESULTS);
	}

	/**
	 * Runs a search on a given term.
	 * @param s the term to be searched for
	 */
	public void projectSearch(String s){
		Item[] results = inventoryManager.searchItems(s);
		projectResultsPanel = new ProjectResultsPanel(this, s, results);

		gui.add(projectResultsPanel, GUIConstants.PROJECT_RESULTS);
		layoutManager.show(gui, GUIConstants.PROJECT_RESULTS);
	}

	/**
	 * Displays a chosen item.
	 * @param ip the ItemPanel for the item to be displayed
	 */
	public void showItem(ItemPanel ip){
		ItemPanel ip2 = ip.copy();
		ip2.setMouseover(false);
		gui.add(new MapPanel(this, ip2), GUIConstants.MAP_PAGE);
		layoutManager.show(gui, GUIConstants.MAP_PAGE);
	}

	public void showProject(){

	}

	public void showSearch(String s){
		layoutManager.show(gui, s);
	}

	/**
	 * Clears out all results and brings up the search page.
	 */
	public void newSearch(String s){
		gui.removeAll();
		gui.add(itemSearchPanel, GUIConstants.ITEM_SEARCH);
		gui.add(projectSearchPanel, GUIConstants.PROJECT_SEARCH);
		layoutManager.show(gui, s);
	}

	/**
	 * Displays the current results page.
	 */
	public void backToItemResults(){
		itemResultsPanel.validate();
		layoutManager.show(gui, GUIConstants.ITEM_RESULTS);
	}

	/**
	 * Displays the current results page.
	 */
	public void backToProjectResults(){
		projectResultsPanel.validate();
		layoutManager.show(gui, GUIConstants.PROJECT_RESULTS);
	}
}
