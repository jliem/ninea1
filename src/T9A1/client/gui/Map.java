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

public class Map {
	public static final String MAP_PATH = "client/gui/maps/";
	public static final String FILETYPE = ".gif";

	private ImageIcon map;
	private int storeNumber;
	private ArrayList<Aisle> aisles;

	public Map(int storeNumber){
		aisles = new ArrayList();

		this.storeNumber = storeNumber;

		map = new ImageIcon(MAP_PATH + storeNumber  + FILETYPE);

		Parser p = new Parser();
		p.fillOutAisles();
	}

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

	public ImageIcon getMap(){
		return map;
	}

	public int getNumAisles(){
		return aisles.size();
	}

	public int getNumBins(int a){
		return aisles.get(a - 1).getBins();
	}

	private class Parser{
		Document dom;

		public void fillOutAisles(){
			parseXmlFile();
			parseDocument();
		}

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

		private int getValue(Element e, String tag) {
			String s = e.getAttribute(tag);
			System.out.println(s);
			int i;
			try{
				i = Integer.parseInt(s);
			}catch(Exception ex){
				i = -1;
			}
			return i;
		}
	}

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
