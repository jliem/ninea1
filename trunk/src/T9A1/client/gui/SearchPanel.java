package T9A1.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * The panel used to enter a search term.
 * @author Catie
 */
public class SearchPanel extends JPanel{
	/** An array that represents a QWERTY keyboard. */
	private final String[][] qwertyArray =
			{{"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
			{"A", "S", "D", "F", "G", "H", "J", "K", "L", "BKSP"},
			{"Z", "X", "C", "V", "B", "N", "M", "GO"},
			{"SPACE"}};

	/** The JTextField used to input the search term. */
	private JTextField searchBox;
	/** The JButton pressed to initiate a search. */
	private JButton search;
	private JRadioButton itemSearch;
	private JRadioButton projectSearch;

	/** The main GUI component. */
	private KioskGUI gui;
	/** The listener used to initiate a search. */
	private SearchListener listener;

	/**
	 * Creates a new search panel.
	 * @param g the main GUI component
	 */
	public SearchPanel(KioskGUI g) {
		super(new BorderLayout());

		gui = g;

		initialize();
	}

	public void initialize(){
		GridBagConstraints c = new GridBagConstraints();

		//Creates and adds a panel to display the new search button.
		JPanel button = new JPanel(new GridBagLayout());
		button.setBackground(GUIConstants.ORANGE);
		JButton newSearch = new JButton("<html><center>View<br />Current Sales</center></html>");
		newSearch.setFont(GUIConstants.MEDIUM_FONT);
		newSearch.addActionListener(new SaleSearchListener());
		c.fill = GridBagConstraints.NONE;
		c.gridy = 0;
		c.gridx = 1;
		c.weightx = 0;
		c.ipadx = 10;
		c.ipady = 15;
		c.insets = new Insets(10, 0, 0, 10);
		c.anchor = GridBagConstraints.EAST;
		button.add(newSearch, c);
		c.gridx = 0;
		c.weightx = 1;
		button.add(Box.createGlue(), c);
		add(button, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridBagLayout());
		Dimension size = new Dimension(500, 500);
		panel.setMinimumSize(size);
		panel.setPreferredSize(size);
		panel.setBackground(GUIConstants.ORANGE);

		JLabel label = new JLabel("<html><center>Enter an item or project to<br />" +
				"search for (i.e. faucet, hammer, repair a sink):</center></html>");
		label.setFont(GUIConstants.LARGE_FONT);
		c.weightx = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(label, c);

		listener = new SearchListener();

		searchBox = new JTextField(25);
		searchBox.setFont(GUIConstants.LARGE_FONT);
		searchBox.addActionListener(listener);
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(searchBox, c);

		search = new JButton("GO");
		search.setFont(GUIConstants.MEDIUM_FONT);
		search.addActionListener(listener);
		searchBox.addActionListener(listener);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 50;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		panel.add(search, c);

		JPanel choices = new JPanel();
		choices.setBackground(GUIConstants.ORANGE);
		ButtonGroup choiceGroup = new ButtonGroup();

		JLabel choose = new JLabel("Search for:");
		choose.setFont(GUIConstants.LARGE_FONT);
		choices.add(choose);

		choices.add(Box.createHorizontalStrut(20));

		itemSearch = new JRadioButton("Items");
		itemSearch.setFont(GUIConstants.LARGE_FONT);
		itemSearch.setBackground(GUIConstants.ORANGE);
		itemSearch.setSelected(true);
		choiceGroup.add(itemSearch);
		choices.add(itemSearch);

		choices.add(Box.createHorizontalStrut(20));

		projectSearch = new JRadioButton("Projects");
		projectSearch.setFont(GUIConstants.LARGE_FONT);
		projectSearch.setBackground(GUIConstants.ORANGE);
		//projectSearch.setEnabled(false);
		choiceGroup.add(projectSearch);
		choices.add(projectSearch);

		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.VERTICAL;
		c.ipadx = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		panel.add(choices, c);

		add(panel, BorderLayout.CENTER);

		OnScreenKeyboard keyboard = new OnScreenKeyboard();
		add(keyboard, BorderLayout.SOUTH);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	private class SaleSearchListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			gui.saleSearch();
		}
	}

	/**
	 * The listener used to initiate a search.
	 * @author Catie
	 */
	private class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String s = searchBox.getText().toUpperCase();
			if(s.equals(""))
				return;
			searchBox.setText("");
			if(itemSearch.isSelected())
				gui.itemSearch(s);
			else if(projectSearch.isSelected())
				gui.projectSearch(s);
		}

	}

	/**
	 * Panel that displays and control the onscreen keyboard.
	 * @author Catie
	 */
	private class OnScreenKeyboard extends JPanel implements ActionListener{
		public OnScreenKeyboard(){
			GridBagLayout layout = new GridBagLayout();
			this.setLayout(layout);
			this.setBackground(GUIConstants.ORANGE);
			GridBagConstraints constraints = new GridBagConstraints();

			FontMetrics m = this.getFontMetrics(GUIConstants.MEDIUM_FONT);
			int keyWidth = 60,
				keyHeight = 30;

			Insets inset = new Insets(5, 5, 5, 5);
			Insets none = new Insets(0, 0, 0, 0);
			for(int i = 0; i < qwertyArray.length; i++){
				JPanel row = new JPanel(new GridBagLayout());
				row.setBackground(GUIConstants.ORANGE);

				for(int j = 0; j < qwertyArray[i].length; j++){
					JButton b = new JButton(qwertyArray[i][j]);
					if(qwertyArray[i][j].equalsIgnoreCase("ENTER"))
						b.addActionListener(listener);
					else b.addActionListener(this);

					constraints.fill = GridBagConstraints.HORIZONTAL;

					constraints.gridy = 0;
					constraints.gridx = j;
					constraints.gridwidth = 1;
					constraints.ipadx = keyWidth -
						m.charsWidth(qwertyArray[i][j].toCharArray(), 0, qwertyArray[i][j].length());
					constraints.ipady = keyHeight;
					constraints.insets = inset;

					if(qwertyArray[i][j].equals("GO")){
						constraints.ipadx = keyWidth * 2 -
							m.charsWidth(qwertyArray[i][j].toCharArray(), 0, qwertyArray[i][j].length());
					}
					else if(qwertyArray[i][j].equals("SPACE")){
						constraints.ipadx = keyWidth * 8;
					}


					b.setFont(GUIConstants.MEDIUM_FONT);
					row.add(b, constraints);
				}

				constraints.gridwidth = 1;
				constraints.insets = none;
				constraints.ipadx = 0;
				constraints.ipady = 0;
				constraints.fill = GridBagConstraints.NONE;
				constraints.gridx = 0;
				constraints.gridy = i;
				constraints.anchor = GridBagConstraints.CENTER;
				add(row, constraints);
			}
		}

		public void actionPerformed(ActionEvent arg0) {
			String s = arg0.getActionCommand();
			if(s.equals("BACK")){
				String term = searchBox.getText();
				if(term.length() > 0)
					searchBox.setText(searchBox.getText().substring(0, searchBox.getText().length() - 1));
				else return;
			}
			else if(s.equalsIgnoreCase("SPACE"))
				searchBox.setText(searchBox.getText() + " ");
			else
				searchBox.setText(searchBox.getText() + s);

		}
	}
}
