package T9A1.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicBorders;

import T9A1.common.Item;
import T9A1.common.Location;
import T9A1.common.Project;

/**
 * A class for getting different item displays. The compact panel can be
 * used in a list of items, while the full panel displays the item's information
 * along with the item's location plotted on a store map.
 *
 * @author Catie
 */
public class ProjectPanel extends JPanel implements SearchResult{

	/** The location of the product images. */
	public static String IMAGE_PATH;
	/** The name of the default product image. */
	private final String NO_IMAGE = "no_image";
	/** The filetype of the product images. */
	private final String FILETYPE = ".jpg";

	/** The item that the panels represent. */
	private Project project;
	/** The main GUI component. */
	private KioskGUI gui;

	/** Determines whether or not the panel should react on mouseover */
	private boolean mouseover;

	/** Displays the tools necessary to complete the project. */
	private JLabel tools;
	/** displays the materials necessary to complete the project. */
	private JLabel materials;

	/**
	 * Creates a new ItemPanel and initializes the compact panel.
	 * @param g The main GUI component.
	 * @param i The Item that this panel represents.
	 */
	public ProjectPanel(KioskGUI gui, Project project){
		this.project = project;
		this.gui = gui;
		mouseover = true;

		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		Insets insets = new Insets(3, 10, 3, 10);

		addMouseListener(new MouseOverListener());
		setBackground(GUIConstants.LIGHT_ORANGE);
		setPreferredSize(new Dimension(gui.getWidth() - 10, 195));

		ImageContainer i = new ImageContainer();
		con.gridheight = 5;
		con.gridwidth = 1;
		con.gridx = 0;
		con.gridy = 0;
		con.insets = new Insets(5, 70, 5, 40);
		add(i, con);

		JLabel name = new JLabel(project.getTitle());
		name.setFont(GUIConstants.LARGE_FONT);
		con.gridheight = 1;
		con.gridwidth = 3;
		con.gridx = 1;
		con.gridy = 0;
		con.insets = insets;
		con.anchor = GridBagConstraints.WEST;
		add(name, con);

		JLabel times = new JLabel(project.getHours() +
				(project.getHours() == 1 ? " Hour, " : " Hours, ") +
				project.getMinutes() + " Minutes");
		times.setFont(GUIConstants.SMALL_FONT);
		con.gridheight = 1;
		con.gridwidth = 3;
		con.gridx = 1;
		con.gridy = 1;
		con.insets = insets;
		con.anchor = GridBagConstraints.WEST;
		add(times, con);

		String s = "Tools: ";
		String[] toolList = project.getTools();
		if(toolList.length == 0)
			s += "none";
		else
			for(int j = 0; j < toolList.length; j++)
				s += toolList[j] + (j != toolList.length - 1 ? ", " : "");

		tools = new JLabel(s);
		tools.setFont(GUIConstants.SMALL_FONT);
		con.gridheight = 1;
		con.gridwidth = 3;
		con.gridx = 1;
		con.gridy = 2;
		con.insets = insets;
		con.anchor = GridBagConstraints.WEST;
		add(tools, con);

		s = "Materials: ";
		String[] matList = project.getMaterials();
		if(matList.length == 0)
			s += "none";
		else
			for(int j = 0; j < matList.length; j++)
				s += matList[j] + (j != matList.length - 1 ? ", " : "");

		materials = new JLabel(s);
		materials.setFont(GUIConstants.SMALL_FONT);
		con.gridheight = 1;
		con.gridwidth = 3;
		con.gridx = 1;
		con.gridy = 3;
		con.insets = insets;
		con.anchor = GridBagConstraints.WEST;
		add(materials, con);

		if(!mouseover){
			tools.setVisible(false);
			materials.setVisible(false);
		}

		con.fill = GridBagConstraints.HORIZONTAL;
		con.gridwidth = 1;
		con.gridheight = 4;
		con.weightx = 1;
		con.gridx = 2;
		con.gridy = 0;
		con.insets = insets;
		add(Box.createGlue(), con);
	}

	/**
	 * Instructs the main GUI component to display the image.
	 */
	private void showItem(){
		gui.showProject(this);
	}

	/**
	 * Provides the Project object that this panel represents.
	 * @return the Project this panel represents
	 */
	public Project getProject(){
		return project;
	}

	/**
	 * Determines whether or not the panel will change colors on mouseover.
	 * @param b a boolean representing the mouseover setting
	 */
	public void setMouseover(boolean b){
		mouseover = b;
		if(b){
			setBackground(GUIConstants.LIGHT_ORANGE);
			tools.setVisible(true);
			materials.setVisible(true);
		}else{
			setBackground(GUIConstants.ORANGE);
			tools.setVisible(false);
			materials.setVisible(false);
		}
		this.invalidate();
	}

	/**
	 * Copies the ProjectPanel.
	 * @return a copy of the ProjectPanel
	 */
	public ProjectPanel copy(){
		return new ProjectPanel(gui, project);
	}

	/**
	 * @override from SearchResult
	 * Returns the panel to be displayed on a list.
	 */
	public JPanel getListPanel() {
		return this;
	}

	/**
	 * MouseListener for the user to chose an item.
	 * @author Catie
	 */
	private class MouseOverListener implements MouseListener{

		/**
		 * Shows full projects page when panel is clicked.
		 */
		public void mouseClicked(MouseEvent e) {
			if(mouseover)
				showItem();
		}

		/**
		 * Highlights panel on mouseover.
		 */
		public void mouseEntered(MouseEvent e) {
			if(mouseover)
				setBackground(GUIConstants.DARK_ORANGE);
		}

		/**
		 * Returns panel to non-highlighted state on mouse exit.
		 */
		public void mouseExited(MouseEvent e) {
			if(mouseover)
				setBackground(GUIConstants.LIGHT_ORANGE);
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}

	/**
	 * Displays the project's image.
	 * @author Catie
	 */
	private class ImageContainer extends JPanel{
		ImageIcon image;

		public ImageContainer(){
			image = new ImageIcon(IMAGE_PATH + project.getId() + "_1" +  FILETYPE);

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
