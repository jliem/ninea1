package T9A1.client.gui;

import java.awt.Point;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import T9A1.common.Location;

/**
 * A representation of the store map. Uses the store number to
 * read in the correct map image and aisle information from files.
 *
 * @author Catie
 */
public class Map {
	/** The location of the map images. */
	public static String MAP_PATH;
	/** The filetype of the map image. */
	public static final String FILETYPE = ".gif";

	/** The map image. */
	private ImageIcon map;
	/** The store's number. */
	private int storeNumber;
	/** An ArrayList of Aisles in the store. */
	private ArrayList<Aisle> aisles;

	/**
	 * Creates a new Map object for the given store number, including parsing the XML file.
	 * @param storeNumber the number for the store represented by the Map
	 */
	public Map(int storeNumber){
		aisles = new ArrayList();

		this.storeNumber = storeNumber;

		map = new ImageIcon(MAP_PATH + storeNumber  + FILETYPE);

		Parser p = new Parser();
		p.fillOutAisles();
	}

	/**
	 * Returns the coordinates on the map that represent the provided Location.
	 * @param l the Location to be plotted
	 * @return the Location's coordinate on the map
	 */
	public Point getCoordinates(Location l){
		int a = l.getAisle();
		int b = l.getBin();

		if(aisles.size() < a) return null;

		Aisle aisle = aisles.get(a - 1);

		if(aisle.getBins() < b) return null;

		int x = aisle.getStartX() + (Math.abs(aisle.getStartX() - aisle.getEndX()) / aisle.getBins()) * b;
		int y = aisle.getStartY() + (Math.abs(aisle.getStartY() - aisle.getEndY()) / aisle.getBins()) * b;

		return new Point(x, y);
	}

	/**
	 * Returns the map image.
	 * @return the map image
	 */
	public ImageIcon getMap(){
		return map;
	}

	/**
	 * Returns the number of aisles in the store.
	 * @return the number of aisles in the store
	 */
	public int getNumAisles(){
		return aisles.size();
	}

	/**
	 * Returns the number of bins in a give aisle.
	 * @param a the aisle to check
	 * @return the number of bins in aisle a
	 */
	public int getNumBins(int a){
		return aisles.get(a - 1).getBins();
	}

	/**
	 * Parses the XML file that represents the aisles in the store using DOM.
	 * @author Catie
	 */
	private class Parser{
		Document dom;

		/**
		 * Initiates document parsing.
		 */
		public void fillOutAisles(){
			parseXmlFile();
			parseDocument();
		}

		/**
		 * Loads in the XML file based on store number.
		 */
		private void parseXmlFile(){
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				dom = db.parse(MAP_PATH + storeNumber + ".xml");
			}catch(ParserConfigurationException pce) {
				pce.printStackTrace();
			}catch(SAXException se) {
				se.printStackTrace();
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}

		/**
		 * Parses the loaded XML file line by line.
		 */
		private void parseDocument(){
			Element docEle = dom.getDocumentElement();

			NodeList list = docEle.getElementsByTagName("aisle");
			if(list != null && list.getLength() > 0) {
				for(int i = 0 ; i < list.getLength();i++) {
					Element e = (Element)list.item(i);
					Aisle a = getAisle(e);
					aisles.add(a);
				}
			}
		}

		/**
		 * Builds an Aisle object out of each XML line.
		 * @param e An Element representing one line of XML
		 * @return A complete Aisle object
		 */
		private Aisle getAisle(Element e){
			int startX = getValue(e, "start_x");
			int startY = getValue(e, "start_y");
			int endX = getValue(e, "end_x");
			int endY = getValue(e, "end_y");
			int bins = getValue(e, "bins");

			if(bins < 0)
				return new Aisle(startX, startY, endX, endY);
			else
				return new Aisle(startX, startY, endX, endY, bins);
		}

		/**
		 * Gets the value of a given tag from the provided element.
		 * @param e One line of XML
		 * @param tag The string representation of the requested tag
		 * @return the integer value of the tag
		 */
		private int getValue(Element e, String tag) {
			String s = e.getAttribute(tag);
			int i;
			try{
				i = Integer.parseInt(s);
			}catch(Exception ex){
				i = -1;
			}
			return i;
		}
	}

	/**
	 * Represents an aisle in the store.
	 * @author Catie
	 */
	private class Aisle{
		static final int STANDARD_BINS = 10;

		private int startX, startY, endX, endY, bins;

		public Aisle(int startX, int startY, int endX, int endY) {
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
			this.bins = STANDARD_BINS;
		}

		public Aisle(int startX, int startY, int endX, int endY, int bins) {
			super();
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
			this.bins = bins;
		}

		public int getStartX() {
			return startX;
		}

		public void setStartX(int startX) {
			this.startX = startX;
		}

		public int getStartY() {
			return startY;
		}

		public void setStartY(int startY) {
			this.startY = startY;
		}

		public int getEndX() {
			return endX;
		}

		public void setEndX(int endX) {
			this.endX = endX;
		}

		public int getEndY() {
			return endY;
		}

		public void setEndY(int endY) {
			this.endY = endY;
		}

		public int getBins() {
			return bins;
		}

		public void setBins(int bins) {
			this.bins = bins;
		}
	}
}
