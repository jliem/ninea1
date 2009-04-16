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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import T9A1.common.Project;

public class ProjectDescriptionPanel extends JPanel {

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

	public ProjectDescriptionPanel(KioskGUI gui, ProjectPanel projectPanel){
		this.gui = gui;
		this.map = gui.getMap();
		this.projectPanel = projectPanel;
		this.project = projectPanel.getProject();

		this.setBackground(GUIConstants.ORANGE);
		this.setLayout(new BorderLayout());

		addProjectPanel();
		add(new JScrollPane(new StepPanel()), BorderLayout.CENTER);
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

	public void addButtonPanel(){
		GridBagConstraints con = new GridBagConstraints();
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		Insets insets = new Insets(10, 10, 10, 10);
		buttonPanel.setBackground(GUIConstants.ORANGE);

		JButton back = new JButton("Back to Results");
		back.addActionListener(new ReturnResultsListener());
		back.setFont(GUIConstants.MEDIUM_FONT);
		con.weightx = 0;
		con.gridheight = 1;
		con.gridwidth = 1;
		con.gridx = 1;
		con.gridy = 0;
		con.insets = insets;
		con.anchor = GridBagConstraints.EAST;
		buttonPanel.add(back, con);

		JButton clear = new JButton("New Search");
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
				c.gridy = i * 2;
				c.gridx = 0;
				c.gridheight = 2;
				c.insets = insets;
				this.add(new ImageContainer(i + 1), c);

				step = new JLabel("Step " + (i + 1) + ":");
				step.setFont(GUIConstants.MEDIUM_FONT);
				c.anchor = GridBagConstraints.WEST;
				c.gridy = i * 2;
				c.gridx = 1;
				c.gridheight = 1;
				this.add(step, c);

				stepText = new JLabel(steps[i]);
				stepText.setFont(GUIConstants.SMALL_FONT);
				c.anchor = GridBagConstraints.WEST;
				c.gridy = i * 2 + 1;
				c.gridx = 1;
				c.gridheight = 1;
				this.add(stepText, c);
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
