// Util class

import java.awt.*;

class Util {
	
   	private Util() {}

	public static int getRandom(int min, int max) {
		return (int) ((max - min + 1) * Math.random()) + min;
	}
	
	public static double getRandom(double min, double max) {
		return  ((max - min) * Math.random()) + min;
	}
	
	public static Color getColor(int maxR, int maxG, int maxB)  {
		return new Color (Util.getRandom(0,maxR), Util.getRandom(0,maxG), Util.getRandom(0,maxB));
	}

	public static Color getDarkColor() {
		return getColor(180,50,180);	
	}
	public static int absoluteDifference(int m, int n) {
		return Math.abs(m-n);
	}
	public static double absoluteDifference(double m, double n) {
		return Math.abs(m-n);
	}
	public static void fillTriangle(Graphics g, Point p1, Point p2, Point p3) {
		g.fillPolygon(
		new int []  {p1.roundX(), p2.roundX(), p3.roundX()}, 
		new int []  {p1.roundY(), p2.roundY(), p3.roundY()}, 
		3);
	}
	public static double getRadians(double degrees) {return degrees*Math.PI/180;}
	
	public static double getDegrees(double radians) {return radians/Math.PI*180;}
	
	public static int round(double d) {
		return (int) (Math.round(d));
	}
	
	public static double round(double d, int digits) {
		double p = 1;
		for (int i = 0; i < digits; i++) p*= 10;
		return Math.round(d*p)/ p;
	}
}