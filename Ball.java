import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
public class Ball{

	private Color color;
	private Point location;
	private Dimension size;
	private boolean infected;
	private boolean busy = false;
	private int myID;
	private int LeaderID;

	public Ball(Color color, int i) {

	    setColor(color);
	    size = new Dimension(10, 10);
	    this.myID = i;
	    this.LeaderID = myID;

	}

	public void infect() {
	    this.infected = this.infected || true;
	    this.color = new Color(200,0,0);
	}

	public void engage() {
	    this.busy = true;
	}

	public void disengage() {
	    this.busy = false;
	}

	public boolean isEngaged() {
	    return this.busy;
	}

	public Dimension getSize() {
	    return size;
	}

	public void setColor(Color color) {
	    this.color = color;
	}

	public void setLocation(Point location) {
	    this.location = location;
	}

	public Color getColor() {
	    return color;
	}

	public Point getLocation() {
	    return location;
	}

	public int getID() {
	    return myID;
	}

	public boolean isInfected() {
	    return this.infected;
	}

	public void updateLeader(int NewLeader) {
	    this.LeaderID = Math.max(NewLeader, this.LeaderID);
	}

	protected void paint(Graphics2D g2d) {

	    Point p = getLocation();
	    if (p != null) {
		g2d.setColor(getColor());
		Dimension size = getSize();
		g2d.fillOval(p.x, p.y, size.width, size.height);
	    }

	}
}
