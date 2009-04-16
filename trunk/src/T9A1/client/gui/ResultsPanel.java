package T9A1.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import T9A1.common.Item;
import T9A1.common.Searchable;

/**
 * A panel that displays a list of search results.
 * @author Catie
 */
public class ResultsPanel extends JPanel {
	/** The main GUI component */
	private KioskGUI gui;
	/** The search term that the results were generated from. */
	private String searchTerm;
	private String type;
	/** An ListPanel containing search results. */
	private Searchable[] results;

	/**
	 * Creates a new results panel for a given search term and results set.
	 * @param g the main GUI component
	 * @param s the search term used to generate the results
	 * @param items the results generated by the search
	 */
	public ResultsPanel(KioskGUI g, String s, String t, Searchable[] results){
		super(new BorderLayout());
		this.results = results;

		GridBagConstraints c = new GridBagConstraints();

		gui = g;
		searchTerm = s;
		type = t;

		add(new JScrollPane(new ListPanel(gui, results)), BorderLayout.CENTER);

		//Creates and adds a panel to go at the top of the page that displays data on the results.
		JPanel displayResults = new JPanel(new GridBagLayout());
		displayResults.setBackground(GUIConstants.ORANGE);
		JLabel search = new JLabel("You searched for \"" + searchTerm + "\"");
		JLabel number;
		if(results == null || results.length == 0){
			number = new JLabel("Your search returned no results.");
		}else{
			number = new JLabel("Your search returned " + results.length + " results.");
		}
		search.setFont(GUIConstants.MEDIUM_FONT);
		number.setFont(GUIConstants.MEDIUM_FONT);
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 0;
		displayResults.add(search, c);
		c.gridy = 1;
		displayResults.add(number, c);
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 2;
		c.weightx = 1;
		displayResults.add(Box.createGlue(), c);
		add(displayResults, BorderLayout.NORTH);

		//Creates and adds a panel to display the new search button.
		JPanel button = new JPanel(new GridBagLayout());
		button.setBackground(GUIConstants.ORANGE);
		JButton newSearch = new JButton("New Search");
		newSearch.setFont(GUIConstants.MEDIUM_FONT);
		newSearch.addActionListener(new NewSearchListener());
		c.fill = GridBagConstraints.NONE;
		c.gridy = 0;
		c.gridx = 1;
		c.weightx = 0;
		c.ipadx = 10;
		c.ipady = 10;
		c.anchor = GridBagConstraints.EAST;
		button.add(newSearch, c);
		c.gridx = 0;
		c.weightx = 1;
		button.add(Box.createGlue(), c);
		add(button, BorderLayout.SOUTH);
	}

	public String toString(){
		return "";//for(ItemPanel i : (ItemPanel)list.getComponents())
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
}
