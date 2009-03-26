package T9A1.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import T9A1.common.Item;

public class ResultsPanel extends JPanel {
	private KioskGUI gui;
	private String searchTerm;
	private Item[] results;
	private Font font;

	public ResultsPanel(KioskGUI g, String s, Item[] items){
		super(new BorderLayout());

		font = new Font("Arial", Font.PLAIN, 30);
		GridBagConstraints c = new GridBagConstraints();

		gui = g;
		searchTerm = s;
		results = items;

		JPanel list = new JPanel(new GridBagLayout());
		list.setBorder(new EmptyBorder(2, 2, 2, 2));
		list.setBackground(GUIColors.LIGHT_ORANGE);
		if(results == null || results.length == 0){
			JLabel empty = new JLabel("Your search returned no results.");
			empty.setFont(font);
			list.add(empty);
		}else{
			ItemPanel ip;
			for(int i = 0; i < results.length; i++){
				ip = new ItemPanel(gui, results[i]);
				c.ipady = 50;
				c.gridy = i;
				list.add(ip.getCompactPanel(), c);
			}
		}

		add(new JScrollPane(list), BorderLayout.CENTER);

		JPanel displayResults = new JPanel(new GridBagLayout());
		displayResults.setBackground(GUIColors.ORANGE);
		JLabel search = new JLabel("You searched for " + searchTerm.toLowerCase());

		JLabel number;
		if(results == null || results.length == 0){
			number = new JLabel("Your search returned no results.");
		}else{
			number = new JLabel("Your search returned " + items.length + " results.");
		}
		search.setFont(font);
		number.setFont(font);
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridy = 0;
		displayResults.add(search, c);
		c.gridy = 1;
		displayResults.add(number, c);
		displayResults.setMinimumSize(new Dimension(0, gui.getWidth()));
		add(displayResults, BorderLayout.NORTH);

		JPanel button = new JPanel(new GridBagLayout());
		button.setBackground(GUIColors.ORANGE);
		JButton newSearch = new JButton("New Search");
		newSearch.setFont(font);
		newSearch.addActionListener(new NewSearchListener());
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		button.add(newSearch, c);
		add(button, BorderLayout.SOUTH);
	}

	private class NewSearchListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			gui.newSearch();
		}
	}
}