package T9A1.client.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import T9A1.common.Item;
import T9A1.common.Project;
import T9A1.common.Searchable;

public class ListPanel extends JPanel {

	private KioskGUI gui;
	private Searchable[] results;

	public ListPanel(KioskGUI gui, Searchable[] results){
		this.gui = gui;
		this.results = results;

		initialize();
	}

	private void initialize() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setBorder(new EmptyBorder(2, 2, 2, 2));
		this.setBackground(GUIConstants.LIGHT_ORANGE);
		if(results == null){
			JLabel empty = new JLabel("Connection problem. Please see store associate for assistance.");
			empty.setForeground(Color.red);
			empty.setFont(GUIConstants.MEDIUM_FONT);
			this.add(empty);
		}else if(results.length == 0){
			JLabel empty = new JLabel("Your search returned no results.");
			empty.setFont(GUIConstants.MEDIUM_FONT);
			this.add(empty);
		}else if(results[0] instanceof Item){
			addItems();
		}else if(results[0] instanceof Project){
			addProjects();
		}

	}

	private void addProjects() {
		GridBagConstraints c = new GridBagConstraints();
		ProjectPanel ip;
		int i;
		c.weighty = 0;
		for(i = 0; i < results.length; i++){
			ip = new ProjectPanel(gui, (Project)results[i]);
			c.ipady = 50;
			c.gridy = i;
			c.gridx = 0;
			c.weightx = 0;
			this.add(ip, c);
			c.gridx = 1;
			c.weightx = 1;
			this.add(Box.createGlue(), c);
		}
		c.gridy = ++i;
		c.gridx = 0;
		c.weighty = 1;
		c.weightx = 0;
		this.add(Box.createGlue(), c);
	}

	private void addItems() {
		GridBagConstraints c = new GridBagConstraints();
		ItemPanel ip;
		int i;
		c.weighty = 0;
		for(i = 0; i < results.length; i++){
			ip = new ItemPanel(gui, (Item)results[i]);
			c.ipady = 50;
			c.gridy = i;
			c.gridx = 0;
			c.weightx = 0;
			this.add(ip, c);
			c.gridx = 1;
			c.weightx = 1;
			this.add(Box.createGlue(), c);
		}
		c.gridy = ++i;
		c.gridx = 0;
		c.weighty = 1;
		c.weightx = 0;
		this.add(Box.createGlue(), c);
	}
}
