package T9A1.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import T9A1.common.Item;

public class ResultsPanel extends JPanel {
	private KioskGUI gui;
	private String searchTerm;
	private Item[] results;

	public ResultsPanel(KioskGUI g, String s, Item[] i){
		gui = g;
		searchTerm = s;
		results = i;

		add(new JLabel("You searched for : " + searchTerm));

		if(results == null)
			add(new JLabel("Your search returned no results."));

		JButton newSearch = new JButton("New Search");
		newSearch.addActionListener(new NewSearchListener());
		add(newSearch);
	}

	private class NewSearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			gui.newSearch();
		}

	}
}
