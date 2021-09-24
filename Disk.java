// Disk Class
import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;
// Worked on this with Siddharth

class Disk extends Point {
	protected int radius;
	public Vect velocity;
	public Color borderColor;
	public boolean anchored = false; // default value


	public Disk(int x, int y, int radius, Color color ) {
		// you write: call constructor with (int, int, int, Color, Color). The last two params are just color
		this(x, y, radius, color, color);
	}
	
	public Disk(int radius, Color color) {
		// you write: call the default Point constructor
		// you write: call init
		super();
		init(radius, color, color);
	}
	
	public Disk(int x, int y, int radius, Vect velocity, Color c) {
		// you write: call the four parameter constructor.
		// you write: set the velocity
		this(x,y,radius,c);
		this.velocity = velocity;
	}
	
	
	public Disk(int x, int y, int radius, Color color, Color borderColor) {
		// you write: call the Point 2-parameter constructor
		// you write: call init
		super(x,y);
		init(radius, color, borderColor);
	}
	
	private void init(int radius, Color color, Color borderColor) {
		// you write: initialize values, set velocity to zero.
		this.radius = radius;
		this.color = color;
		this.borderColor = borderColor;
	}
	
	void copyDisk(Disk d) {
		// you write: set all values of this to d’s values.
		this.setRadius(d.radius);
		this.setX(d.getX());
		this.setY(d.getY());
		this.velocity = d.velocity;
		this.anchored = d.anchored;
		this.color = d.color;
		this.borderColor = d.borderColor;
	}
	
	public Disk(Disk[] otherDisks) // create a random anchored disk, avoiding overlaps with other disks.
	{
		// Nothing to do here but understand how it works.
		boolean collision;
		Disk temp;
		anchored = true;
		int tries, maxTries = 10000, minRadius = 40;
		do {
			tries = 0;
			do { 
				temp = new Disk(
						Util.getRandom(minX, maxX), 
						Util.getRandom(minY,maxY), 
						Util.getRandom(minRadius, minRadius + (maxX - minX)/9),
						Util.getDarkColor()	,
						Color.LIGHT_GRAY);
				temp.anchored = true;
				
				collision = false;
				for (int j = 0; j < otherDisks.length; j++)  {
					Disk other = otherDisks[j];
					if (other != null && other.collision(temp)) 
						collision = true;
				}
				tries++;
			} while (collision && tries < maxTries);
			minRadius--;
			//if (minRadius < 40) System.out.println(minRadius);
		} while (collision && minRadius >= 1);
		
		copyDisk(temp);
		
	}
	public boolean collision(Disk d) {
		double r = getDistance(d);
		return r <= radius + d.radius  &&  d!= this; 
	}
	




	public void draw(Graphics g, Color c, Color border) {
		// everything in here is written for you but you should understand how it works.
		Color temp = g.getColor();  // save color
		g.setColor(c); // temporarily change color
		int d = Util.round(2*radius);
		g.fillOval(Util.round(x-radius), Util.round(y-radius), d,d);
		if (!this.color.equals(this.borderColor)) {
			g.setColor(border);
			g.drawOval(Util.round(x-radius), Util.round(y-radius), d,d);	
		}
		g.setColor(temp); // restore color
	}

	public void draw(Graphics g) {
		// you write: call the draw above with color and borderColor.
		draw(g, this.color, this.borderColor);
	}
	public void setRadius(int radius) {
		// you write: set the radius
		this.radius = radius;
	}
	
	public double getArea() {
		// you write: return the area
		return Math.PI * radius * radius;
	}

	public void erase(Graphics g) {
		draw(g,background, background);
	}
	public void move() {
		// you write: update  velocity if disk boundary crosses border and is going it that direction
		move(velocity); // calls Point move
		
		// if disk leaves x boundary
		if (((x + radius > maxX) && velocity.vx > 0) || ((x - radius < minX) && velocity.vx < 0)) {
			velocity.negateX();
		}
		// if disk leaves y boundary
		if (((y + radius > maxY) && velocity.vy > 0) || ((y - radius < minY) && velocity.vy < 0)) {
			velocity.negateY();
		}
	}
	public void move(Disk [] disks) {
		if (anchored) return;

		for (int i = 0; i < disks.length; i++) 
			if (disks[i] != null)
				handleCollision(disks[i]);
		
		double speed = this.velocity.getLength(); 
		if (speed > 6) this.velocity.setLength(speed * 0.99);  // some friction for super fast speeds
		move();
	}
	public void elasticCollision(Disk d) {
		// you write this  (about 15 to 20 lines of code)
		if (this.getDistance(d) >= Util.absoluteDifference(this.radius, d.radius)) {
			Vect temp = new Vect(d, this); // temp is a vector between the centers of each disk 
			if (!this.velocity.getDifference(d.velocity).formsAcuteAngle(temp)) {
				Vect p1 = this.velocity.getProjection(temp); // p1 is the projection of this velocity vector on temp
				Vect p2 = d.velocity.getProjection(temp); // p2 is the projection of d's velocity vector on temp
				Vect q1 = this.velocity.getProjection(temp.getAngle() - (Math.PI/2));
				Vect q2 = d.velocity.getProjection(temp.getAngle() - (Math.PI/2));
				double m1 = this.getArea();
				double m2 = d.getArea();
				Vect newP1 = ((p1.getProduct(m1-m2)).getSum(p2.getProduct(2*m2))).getProduct(1.0/(m1+m2));
				Vect newP2 = ((p2.getProduct(m2-m1)).getSum(p1.getProduct(2*m1))).getProduct(1.0/(m1+m2));
				this.velocity = newP1.getSum(q1);
				d.velocity = newP2.getSum(q2);
			}
		}
	}

	//p1.getProduct(m1 - m2).add(p2.getProduct(2*m2)).multiplyBy((double)1.0/(m1 + m2));
	//p2.getProduct(m2 - m1).add(p1.getProduct(2*m1)).multiplyBy((double)1.0/(m1 + m2));

	public void handleCollision(Disk d) {
		if (collision(d)) 
			if (!d.anchored) elasticCollision(d);
			else {
				// you write: bounce of anchored disk.
				Vect temp = new Vect(d, this);
				this.velocity.reflect(temp.rotateClockwise(Math.PI/2).getAngle());
			}
	}
	public String toString() {
		// you write: call toString of Point then append velocity in square brackets e.g. (44,55)  [0.2,3.22]
		// remember, there is already a Velocity.toString(). 
		return super.toString() + "  [" + velocity.vx + "," + velocity.vy + "]";
	}
	
}

