package T9A1.client.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import T9A1.common.Item;

public class ResultsPanel extends JPanel {
	private KioskGUI gui;
	private String searchTerm;
	private Item[] results;

	public ResultsPanel(KioskGUI g, String s, Item[] items){
		super(new BorderLayout());

		gui = g;
		searchTerm = s;
		results = items;

		add(new JLabel("You searched for : " + searchTerm), BorderLayout.NORTH);

		JPanel list = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if(results == null || results.length == 0){
			list.add(new JLabel("Your search returned no results."));
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

		JButton newSearch = new JButton("New Search");
		newSearch.addActionListener(new NewSearchListener());
		add(newSearch, BorderLayout.SOUTH);
	}

	private class NewSearchListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			gui.newSearch();
		}
	}
}
