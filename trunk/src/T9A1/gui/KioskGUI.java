package T9A1.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KioskGUI {
	Object inventoryManager;
	JPanel gui;
	CardLayout layoutManager;

	public KioskGUI(Object im){
		inventoryManager = im;

		JFrame frame = new JFrame("Kiosk");

		layoutManager = new CardLayout();
		gui = new JPanel(layoutManager);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
