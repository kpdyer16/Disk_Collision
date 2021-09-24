// Vect class

import java.awt.Graphics;
// a Vect represents a vector in RxR (the plane).
public class Vect {
	public double vx, vy;
	
	public Vect(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}
	public Vect() {
		vx = vy = 0;
	}
	public Vect(Point p, Point q) {
		this.vx = q.getX()-p.getX();
		this.vy = q.getY()-p.getY();
	}
	public Vect setAngle(double angle) {
		 setLengthAndAngle(this.getLength(), angle);
		 return this;
	}
	public Vect setLength(double length) {
		setLengthAndAngle(length, this.getAngle());
		return this;
	}
	public Vect multiplyBy(double x) {
		this.vx *= x;
		this.vy *= x;
		return this;
	}
	public Vect getProduct(double x) {
		Vect temp = (Vect) this.clone();
		return temp.multiplyBy(x);
	}
	public Vect setLengthAndAngle(double length, double angle) {
		this.vx = Math.cos(angle)*length;
		this.vy = Math.sin(angle)*length;
		return this;
	}
	public Vect reflect(double angle) {
		setAngle(2*angle - this.getAngle());
		return this;
	}
	public double getLength() {
		return Math.sqrt(vx*vx + vy*vy);
	}
	public double getAngle() {
		return Math.atan2(vy,vx); 
	}
	public Vect negateX() {
		this.vx *= -1;
		return this;
	}
	public Vect negateY() {
		this.vy *= -1;
		return this;
	}
	public Vect negate() {
		negateX(); 
		negateY();
		return this;
	}
	
	// return a new Vect equal to this
	public Vect getNegation() {
		Vect temp = (Vect) this.clone();
		temp.negate(); 
		return temp;
	}
	public Vect rotateClockwise(double angle) {
		setAngle(this.getAngle() + angle);
		return this;
	}
	public double getAngleBetween(Vect other) {
		double temp = Util.absoluteDifference(this.getAngle(), other.getAngle());
		return temp > Math.PI ? 2*Math.PI - temp : temp;
	}
	public Vect normalize() {
		multiplyBy(1/getLength());
		return this;
	}
	public Vect getProjection(Vect u) {
		Vect temp = new Vect(u.vx, u.vy);
		return temp.normalize().multiplyBy( Math.cos(getAngleBetween(temp))*getLength());
	}
	public Vect getProjection(double angle) {
		Vect temp = new Vect();
		return temp.setLengthAndAngle(1, angle).multiplyBy( Math.cos(getAngleBetween(temp))*getLength());
	}
	public Vect add(Vect other) {
		vx += other.vx; vy += other.vy;
		return this;
	}
	public Vect subtract(Vect other) {
		vx -= other.vx; vy -= other.vy;
		return this;
	}
	public Vect getSum(Vect other) {
		Vect temp = (Vect) this.clone();
		return temp.add(other);
	}
	public Vect getDifference(Vect other) {
		Vect temp = (Vect) this.clone();
		return temp.subtract(other);
	}
	public boolean formsAcuteAngle(Vect other) {
		return getAngleBetween(other) < Math.PI/2;
	}
	private Point getTip(double x, double y) {
		return new Point(x + vx, y + vy);
	}
	public void simpleDraw(Graphics g, int x, int y) {
		g.drawLine(x, y, Util.round(x+vx),Util.round(y+vy));
		g.fillOval(x-2,y-2,4,4);  // make a circle at base
	}
	public Object clone() {
		return new Vect(vx,vy);
	}
	public void draw(Graphics g, int x, int y) {
		simpleDraw(g,x,y);
		Vect v1 = new Vect(), v2 = new Vect();
		v1.setLength(8);
		v1.setAngle(getAngle() + 4*Math.PI/5);
		v2.setLength(8);
		v2.setAngle(getAngle() - 4*Math.PI/5);
		Point p0 = getTip(x, y);
		Point p1 = v1.getTip(p0.getX(), p0.getY());
		Point p2 = v2.getTip(p0.getX(), p0.getY());	
		Util.fillTriangle(g, p1, p2, p0);	
	}
	public String toString() {
		return "[" + Util.round(vx,2) + "," + Util.round(vy,2) + "]";
	}
}
