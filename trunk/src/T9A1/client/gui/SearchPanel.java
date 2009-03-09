package T9A1.client.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class SearchPanel extends JPanel{
	private final String[][] qwertyArray =
			{{"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
			{"A", "S", "D", "F", "G", "H", "J", "K", "L", "BACK"},
			{"Z", "X", "C", "V", "B", "N", "M", "ENTER"},
			{"SPACE"}};

	private JTextField searchBox;
	private JButton search;

	private KioskGUI gui;

	public SearchPanel(KioskGUI g) {
		super(new BorderLayout());

		gui = g;

		JLabel label = new JLabel("Enter a search term below:");
		add(label, BorderLayout.NORTH);

		searchBox = new JTextField(30);
		add(searchBox, BorderLayout.CENTER);

		search = new JButton("GO");
		search.addActionListener(new SearchListener());
		add(search, BorderLayout.EAST);

		OnScreenKeyboard keyboard = new OnScreenKeyboard();
		add(keyboard, BorderLayout.SOUTH);
	}

	public void search(){
		search.doClick();
	}

	private class SearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = searchBox.getText().toUpperCase();
			searchBox.setText("");
			gui.search(s);
		}

	}

	private class OnScreenKeyboard extends JPanel implements ActionListener{
		public OnScreenKeyboard(){
			SpringLayout layout = new SpringLayout();
			this.setLayout(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();

			JButton[][] buttonArray = new JButton[qwertyArray.length][];
			buttonArray[0] = new JButton[qwertyArray[0].length];
			buttonArray[1] = new JButton[qwertyArray[1].length];
			buttonArray[2] = new JButton[qwertyArray[2].length];
			buttonArray[3] = new JButton[qwertyArray[3].length];

			Insets inset = new Insets(2, 2, 2, 2);
			for(int i = 0; i < buttonArray.length; i++){
				for(int j = 0; j < buttonArray[i].length; j++){
					buttonArray[i][j] = new JButton(qwertyArray[i][j]);
					buttonArray[i][j].addActionListener(this);

					constraints.fill = GridBagConstraints.HORIZONTAL;

					constraints.gridx = j * 2 + i % 2;
					constraints.gridy = i;
					constraints.gridwidth = 2;
					constraints.ipadx = 10;
					constraints.ipady = 5;
					constraints.insets = inset;

					if(qwertyArray[i][j].equals("ENTER")){
						constraints.gridwidth = 4;
					}
					else if(qwertyArray[i][j].equals("SPACE")){
						constraints.gridwidth = 16;
					}
					add(buttonArray[i][j], constraints);
				}
			}

			for(int i = 0; i < buttonArray[0].length; i++){

			}
		}

		@Override
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
			else if(s.equalsIgnoreCase("ENTER"))
				search();
			else
				searchBox.setText(searchBox.getText() + s);

		}
	}
}
