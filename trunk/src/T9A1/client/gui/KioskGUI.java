package T9A1.client.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.xml.sax.XMLReader;

import T9A1.client.data.InventoryManager;
import T9A1.common.Item;
import T9A1.common.Location;
import T9A1.common.Searchable;

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
	/** The current item results panel. */
	private ResultsPanel resultsPanel;

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
		gui.add(searchPanel, GUIConstants.SEARCH);

		layoutManager.show(gui, GUIConstants.SEARCH);

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
		Item[] results = inventoryManager.doSearch(s);
		resultsPanel = new ResultsPanel(this, s, GUIConstants.ITEM, results);

		gui.add(resultsPanel, GUIConstants.RESULTS);
		layoutManager.show(gui, GUIConstants.RESULTS);
	}

	/**
	 * Runs a search on a given term.
	 * @param s the term to be searched for
	 */
	public void projectSearch(String s){
		Searchable[] results = inventoryManager.doSearch(s);		resultsPanel = new ResultsPanel(this, s, GUIConstants.PROJECT, results);
		gui.add(resultsPanel, GUIConstants.RESULTS);
		layoutManager.show(gui, GUIConstants.RESULTS);
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

	public void showProject(ProjectPanel pp){
		ProjectPanel pp2 = pp.copy();
		pp2.setMouseover(false);
		gui.add(new ProjectDescriptionPanel(this, pp2), GUIConstants.PROJECT_PAGE);
		layoutManager.show(gui, GUIConstants.PROJECT_PAGE);
	}

	/**
	 * Clears out all results and brings up the search page.
	 */
	public void newSearch(){
		gui.removeAll();
		gui.add(searchPanel, GUIConstants.SEARCH);
	}

	/**
	 * Displays the current results page.
	 */
	public void backToResults(){
		resultsPanel.validate();
		layoutManager.show(gui, GUIConstants.RESULTS);
	}
}
