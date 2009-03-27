package T9A1.client.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
			{"A", "S", "D", "F", "G", "H", "J", "K", "L", "BACK"},
			{"Z", "X", "C", "V", "B", "N", "M", "ENTER"},
			{"SPACE"}};

	/** The JTextField used to input the search term. */
	private JTextField searchBox;
	/** The JButton pressed to initiate a search. */
	private JButton search;
	/** The Font used throughout the panel. */
	private Font font;

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

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(GUIColors.ORANGE);
		GridBagConstraints c = new GridBagConstraints();

		font = new Font("Arial", Font.PLAIN, 40);
		JLabel label = new JLabel("Enter a search term below:");
		label.setFont(font);
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(label, c);

		listener = new SearchListener();

		searchBox = new JTextField(20);
		searchBox.setFont(font);
		searchBox.addActionListener(listener);
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(searchBox, c);

		search = new JButton("GO");
		search.setFont(font);
		search.addActionListener(listener);
		searchBox.addActionListener(listener);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 50;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		panel.add(search, c);

		add(panel, BorderLayout.CENTER);

		OnScreenKeyboard keyboard = new OnScreenKeyboard();
		add(keyboard, BorderLayout.SOUTH);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
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
			gui.search(s);
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
			this.setBackground(GUIColors.ORANGE);
			GridBagConstraints constraints = new GridBagConstraints();

			JButton[][] buttonArray = new JButton[qwertyArray.length][];
			buttonArray[0] = new JButton[qwertyArray[0].length];
			buttonArray[1] = new JButton[qwertyArray[1].length];
			buttonArray[2] = new JButton[qwertyArray[2].length];
			buttonArray[3] = new JButton[qwertyArray[3].length];

			Insets inset = new Insets(5, 5, 5, 5);
			Font keyboardFont = new Font("Arial", Font.PLAIN, 26);
			for(int i = 0; i < buttonArray.length; i++){
				for(int j = 0; j < buttonArray[i].length; j++){
					buttonArray[i][j] = new JButton(qwertyArray[i][j]);
					if(qwertyArray[i][j].equalsIgnoreCase("ENTER"))
						buttonArray[i][j].addActionListener(listener);
					else buttonArray[i][j].addActionListener(this);

					constraints.fill = GridBagConstraints.HORIZONTAL;

					constraints.gridy = i;
					constraints.gridwidth = 2;
					constraints.ipadx = 40;
					constraints.ipady = 40;
					constraints.insets = inset;

					if(i < 2)
						constraints.gridx = j * 2;
					else if(i == 2)
						constraints.gridx = j * 2 + 1;
					else
						constraints.gridx = 2;

					if(qwertyArray[i][j].equals("BACK")){
						constraints.ipadx = 0;
					}
					else if(qwertyArray[i][j].equals("ENTER")){
						constraints.gridwidth = 4;
					}
					else if(qwertyArray[i][j].equals("SPACE")){
						constraints.gridwidth = 16;
					}
					buttonArray[i][j].setFont(keyboardFont);
					add(buttonArray[i][j], constraints);
				}

				constraints.gridwidth = 1;
				constraints.gridx = 0;
				constraints.gridy = 2;
				add(Box.createHorizontalGlue(), constraints);
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
