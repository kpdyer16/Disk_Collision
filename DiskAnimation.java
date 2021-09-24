import java.applet.Applet;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;

public class DiskAnimation extends Applet  implements MouseListener {
	boolean go = true;
	final int MAXSHAPES = 50;
	int numshapes = Util.getRandom(10,20),numSmall = Util.getRandom(3,10);
	Disk[] shapes = new Disk[MAXSHAPES];
	private static final long serialVersionUID = 1L;
	static final double MAXSPEED = 2.2, MINSPEED = -2.2;
	
	public void init() {	
		this.addMouseListener(this);
		Point.setMaxX(this.getWidth());
		Point.setMaxY(this.getHeight());  // minX and minY will default to 0
		System.out.println("Width = " + this.getWidth());
		for (int i = 0; i < numSmall; i++) shapes[i] =  
			new Disk(
				100*i,
				100,
				Util.getRandom(4, 40), 
				new Vect(Util.getRandom(MINSPEED,MAXSPEED), Util.getRandom(MINSPEED,MAXSPEED)),
				Color.WHITE);

		this.setBackground(new Color(35, 0, 80));
		for (int i = numSmall; i < numshapes; i++) {
			shapes[i] = new Disk(shapes);
		}
		Disk.background = this.getBackground();
	}
	public void update(Graphics g) {
		final int SLEEPTIME = 32;
		try {Thread.sleep(SLEEPTIME);}
		catch (InterruptedException e) {}
		paint(g);
		
	}
	public void paint(Graphics g) {
		Point.setMaxX ( this.getWidth());
		Point.setMaxY ( this.getHeight());
		for (int i = 0; i < MAXSHAPES; i++) if (shapes[i] != null) shapes[i].draw(g);
		for (int i = 0; i < numSmall; i++) {
			shapes[i].erase(g);
			shapes[i].move(shapes);
			shapes[i].draw(g);
		}
		if (go) repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!go && numshapes < MAXSHAPES) {
			shapes[numshapes++] = new Disk(e.getX(), e.getY(), Util.getRandom(28, 40), Util.getDarkColor(), Util.getColor(255, 255, 255));
			shapes[numshapes-1].anchored = true;
		}
		go = !go;
		if (go) repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}	
}