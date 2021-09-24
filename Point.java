// Point class

import java.awt.Color;
import java.awt.Graphics;
public class Point {
	protected static int maxX = 400, maxY = 400, minX = 0, minY = 0; //default values
	protected double x,y;
	public static Color background;
	public Color color;
	public Point() {
		this(Util.getRandom(minX, maxX), Util.getRandom(minX, maxX)); // random point
	}
	
	public Point(double x, double y) {
		setX(x);
		setY(y);
	}
	
	
	public double getX() {return x;}
	
	public double getY() {return y;}
	
	public void setX(double x) {
		this.x = Math.min(maxX, Math.max(minX, x));
	}
	
	public void setY(double y) {
		this.y = Math.min(maxY, Math.max(minY, y));
	}	
	
	
	static public void setMaxX(int maxX)  {
		if (maxX < minX) maxX = minX;
		Point.maxX = maxX;	
	}
	
	static public void setMaxY(int maxY) {
		if (maxY < minY) maxY = minY;
		Point.maxY = maxY;
	}
	
	static public void setMinX(int minX)  {
		if (minX > maxX) minX = maxX;
		Point.minX = minX;
	}
	
	static public void setMinY(int minY) {
		if (minY > maxY) minY = maxY;
		Point.minY = minY;
	}
	
	public static int getMaxX() {return maxX;}
		
	public static int getMaxY() {return maxY;}
	
	public static int getMinX() {return minX;}
	
	public static int getMinY() {return minY;}
	
	public int roundX() {return Util.round(x);}
	
	public int roundY() {return Util.round(y);}
	
	public String toString() {return "(" + Util.round(x,2) + "," + Util.round(y ,2)+  ")";}
	
	public void draw(Graphics g) {
		Graphics temp = g;
		g.fillOval(roundX()-1,roundY()-1,2,2);
	}
	
	public void move(Vect a) {
		setX(x + a.vx);
		setY(y + a.vy);
	}

	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass())
				return false;
		Point temp = (Point) o;
		return temp.x == x && temp.y == y;
	}
	
	
	public double getDistance(Point p) {
		double dx = p.x - x, dy = p.y - y;
		return Math.sqrt(dx*dx + dy*dy);
	}
	


}