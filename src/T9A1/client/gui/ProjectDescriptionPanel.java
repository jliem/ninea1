package T9A1.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import T9A1.common.Project;

public class ProjectDescriptionPanel extends JPanel {
	private final double PERCENTAGE = 0.7;

	/** The location of the product images. */
	private final String IMAGE_PATH = "client/gui/images/project/";
	/** The name of the default product image. */
	private final String NO_IMAGE = "no_image";
	/** The filetype of the product images. */
	private final String FILETYPE = ".jpg";

	private KioskGUI gui;
	private Map map;
	private ProjectPanel projectPanel;
	private Project project;

	private JList materialsList;
	private StepPanel stepPanel;

	public ProjectDescriptionPanel(KioskGUI gui, ProjectPanel projectPanel){
		this.gui = gui;
		this.map = gui.getMap();
		this.projectPanel = projectPanel;
		this.project = projectPanel.getProject();

		this.setBackground(GUIConstants.ORANGE);
		this.setLayout(new BorderLayout());

		addProjectPanel();
		addStepPanel();
		addListPanel();
		addButtonPanel();
	}

	public void addProjectPanel(){
		JPanel top = new JPanel(new GridBagLayout());
		top.setBackground(GUIConstants.ORANGE);
		GridBagConstraints con = new GridBagConstraints();
		Insets insets = new Insets(40, 20, 10, 0);
		con.insets = insets;
		top.add(projectPanel, con);
		con.gridx = 1;
		con.weightx = 1;
		top.add(Box.createGlue(), con);
		add(top, BorderLayout.NORTH);
	}

	public void addStepPanel(){
		stepPanel = new StepPanel();

		JScrollPane scroll = new JScrollPane(stepPanel);
		scroll.setMinimumSize(new Dimension((int)(gui.getWidth() * PERCENTAGE), gui.getHeight() - 375));
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(GUIConstants.ORANGE);
		panel.add(scroll);

		add(panel, BorderLayout.CENTER);
	}

	public void addListPanel(){
		String[] t = project.getTools();
		String[] m = project.getMaterials();
		String[] l = new String[t.length + m.length + 2];

		l[0] = "Tools";
		for(int i = 0; i < t.length; i++)
			l[i + 1] = "     " + t[i];

		l[t.length + 1] = "Materials";
		for(int i = 0; i < m.length; i++)
			l[i + t.length + 2] = "     " + m[i];

		materialsList = new JList(l);
		materialsList.setFont(GUIConstants.MEDIUM_FONT);
		materialsList.setBackground(GUIConstants.LIGHT_ORANGE);
		materialsList.setSelectionBackground(GUIConstants.DARK_ORANGE);

		JPanel panel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);
		c.ipadx  = 50;
		c.ipady = 20;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		JScrollPane scroll = new JScrollPane(materialsList);
		scroll.setMinimumSize(new Dimension(
				(int)(gui.getWidth() * (1 - PERCENTAGE)), gui.getHeight() - 100));
		panel.add(scroll, c);

		JButton button = new JButton("Search");
		button.setFont(GUIConstants.MEDIUM_FONT);
		button.addActionListener(new SearchMaterialsListener());
		c.gridy = 1;
		panel.add(button, c);

		panel.setBackground(GUIConstants.ORANGE);
		this.add(panel, BorderLayout.EAST);
	}

	public void addButtonPanel(){
		GridBagConstraints con = new GridBagConstraints();
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		Insets insets = new Insets(10, 10, 10, 10);
		buttonPanel.setBackground(GUIConstants.ORANGE);

		JButton back = new JButton("<html><center>Back to<br />Results</center></html>");
		back.addActionListener(new ReturnResultsListener());
		back.setFont(GUIConstants.MEDIUM_FONT);
		con.weightx = 0;
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 1;
		con.gridy = 0;
		con.ipadx = 20;
		con.ipady = 40;
		con.insets = insets;
		con.anchor = GridBagConstraints.EAST;
		buttonPanel.add(back, con);

		JButton clear = new JButton("<html><center>New<br />Search</center></html>");
		clear.addActionListener(new NewSearchListener());
		clear.setFont(GUIConstants.MEDIUM_FONT);
		con.gridx = 2;
		con.gridy = 0;
		buttonPanel.add(clear, con);

		con.gridx = 0;
		con.weightx = 1;
		buttonPanel.add(Box.createGlue(), con);

		add(buttonPanel, BorderLayout.SOUTH);
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

	private class SearchMaterialsListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			gui.itemSearch(materialsList.getSelectedValue().toString().trim());
		}
	}

	/**
	 * Listener for the Return to Results button.
	 * @author Catie
	 */
	private class ReturnResultsListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			projectPanel.setMouseover(true);
			projectPanel.validate();
			gui.backToResults();
		}
	}
	private class StepPanel extends JPanel{
		public StepPanel(){
			super(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			Project p = projectPanel.getProject();
			String[] steps = p.getInstructions();

			this.setBackground(GUIConstants.LIGHT_ORANGE);

			Insets insets = new Insets(20, 20, 20, 20);
			JLabel step, stepText;
			for(int i = 0; i < steps.length; i++){
				c.anchor = GridBagConstraints.CENTER;
				c.gridy = i * 3;
				c.gridx = 0;
				c.gridheight = 3;
				c.insets = insets;
				this.add(new ImageContainer(i + 1), c);

				step = new JLabel("Step " + (i + 1) + ":");
				step.setFont(GUIConstants.MEDIUM_FONT);
				c.anchor = GridBagConstraints.WEST;
				c.gridy = i * 3;
				c.gridx = 1;
				c.gridheight = 1;
				this.add(step, c);

				stepText = new JLabel("<html><p>" + steps[i] + "</p></html>");
				stepText.setFont(GUIConstants.SMALL_FONT);
				c.anchor = GridBagConstraints.WEST;
				c.gridy = i * 3 + 1;
				c.gridx = 1;
				c.gridheight = 1;
				this.add(stepText, c);

				c.anchor = GridBagConstraints.WEST;
				c.gridy = i * 3;
				c.gridx = 2;
				c.gridheight = 3;
				this.add(Box.createHorizontalGlue(), c);
			}
		}
	}

	/**
	 * Displays the project's image.
	 * @author Catie
	 */
	private class ImageContainer extends JPanel{
		ImageIcon image;

		public ImageContainer(int stepNumber){
			image = new ImageIcon(IMAGE_PATH + project.getId() + "_" + stepNumber + FILETYPE);

			if(image.getIconHeight() < 0)
				image = null;

			this.setMinimumSize(new Dimension(150, 185));
			this.setPreferredSize(new Dimension(150, 185));

			if(image != null){
				this.setMinimumSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
				this.setPreferredSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
			}
		}

		public void paintComponent(Graphics g){
			if(image != null)
				image.paintIcon(this, g, 0, 0);
		}
	}

}
