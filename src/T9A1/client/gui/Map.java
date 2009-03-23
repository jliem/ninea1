package T9A1.client.gui;

import java.awt.Point;

import javax.swing.ImageIcon;

import T9A1.common.Location;

public class Map {
	public static final ImageIcon STAR = new ImageIcon("client/gui/images/star.png");
	private ImageIcon map;

	public Map(){
		map = new ImageIcon("client/gui/images/map.gif");
	}

	public Point getCoordinates(Location l){
		int a = l.getAisle();
		int b = l.getBin();

		if(aisles.length < a) return null;

		Aisle aisle = aisles[a - 1];

		if(aisle.getBins() < b) return null;

		int x = aisle.getStartX() + (Math.abs(aisle.getStartX() - aisle.getEndX()) / aisle.getBins()) * b;
		int y = aisle.getStartY() + (Math.abs(aisle.getStartY() - aisle.getEndY()) / aisle.getBins()) * b;

		return new Point(x, y);
	}

	public ImageIcon getMap(){
		return map;
	}

	public int getNumAisles(){
		return aisles.length;
	}

	public int getNumBins(int a){
		return aisles[a - 1].getBins();
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

	private Aisle[] aisles =
		{
			//right hand side of store
			new Aisle(642, 475, 764, 475),
			new Aisle(642, 468, 764, 468),
			new Aisle(642, 431, 764, 431),
			new Aisle(642, 422, 764, 422),
			new Aisle(642, 388, 764, 388),
			new Aisle(642, 381, 764, 381),
			new Aisle(642, 349, 764, 349),
			new Aisle(642, 341, 764, 341),
			new Aisle(642, 306, 764, 306),
			new Aisle(642, 299, 764, 299),
			new Aisle(642, 262, 764, 262),
			new Aisle(642, 255, 764, 255),
			new Aisle(642, 220, 764, 220),
			new Aisle(642, 213, 764, 213),
			new Aisle(642, 179, 697, 179, 5),
			new Aisle(697, 138, 764, 138),
			new Aisle(642, 131, 764, 131),
			new Aisle(642, 94, 764, 94),
			new Aisle(642, 87, 764, 87),
			new Aisle(642, 49, 764, 49),
			new Aisle(642, 42, 764, 42),

			//main block of aisles
			new Aisle(484, 51, 484, 172),
			new Aisle(484, 200, 484, 218),

			new Aisle(475, 51, 475, 172),
			new Aisle(475, 200, 475, 218),

			new Aisle(441, 51, 441, 172),
			new Aisle(441, 200, 441, 218),

			new Aisle(434, 51, 434, 172),
			new Aisle(434, 200, 434, 218),

			new Aisle(402, 51, 402, 172),
			new Aisle(402, 200, 402, 218),

			new Aisle(395, 51, 395, 172),
			new Aisle(395, 200, 395, 218),

			new Aisle(360, 51, 360, 172),
			new Aisle(360, 200, 360, 218),

			new Aisle(353, 51, 353, 172),
			new Aisle(353, 200, 353, 218),

			new Aisle(320, 51, 320, 172),
			new Aisle(320, 200, 320, 218),

			new Aisle(313, 51, 313, 172),
			new Aisle(313, 200, 313, 218),

			new Aisle(278, 51, 278, 172),
			new Aisle(278, 200, 278, 218),

			new Aisle(271, 51, 271, 172),
			new Aisle(271, 200, 271, 218),

			new Aisle(233, 51, 233, 172),
			new Aisle(233, 200, 233, 218),

			new Aisle(226, 51, 226, 172),
			new Aisle(226, 200, 226, 218),

			new Aisle(191, 51, 191, 172),
			new Aisle(191, 200, 191, 218),

			new Aisle(184, 51, 184, 172),
			new Aisle(184, 200, 184, 218),

			new Aisle(152, 51, 152, 172),
			new Aisle(152, 200, 152, 218),

			new Aisle(145, 51, 145, 172),
			new Aisle(145, 200, 145, 218),

			new Aisle(109, 51, 109, 172),
			new Aisle(109, 200, 109, 218),

			new Aisle(102, 51, 102, 172),
			new Aisle(102, 200, 102, 218),

			new Aisle(65, 51, 65, 172),
			new Aisle(65, 200, 65, 218),

			new Aisle(58, 51, 58, 172),
			new Aisle(58, 200, 58, 218),

			//small block on bottom left
			new Aisle(50, 367, 170, 367),
			new Aisle(50, 374, 170, 374),
			new Aisle(50, 411, 170, 411),
			new Aisle(50, 418, 170, 418),
			new Aisle(50, 455, 170, 455),
			new Aisle(50, 462, 170, 462)
		};

}
