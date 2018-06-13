import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.PrintWriter;
import java.io.*;
public class Agent{

	private Color color;
	private Point location;
	private Point Infolocation;
	private Dimension size;
	private boolean infected = false;
	private boolean busy = false;
	private int myID;
	private int LeaderID;
	private int agent_count;
	private List<Agent> interactedWith; 
	private List<Agent> infectedThem;
	PrintWriter writer = null; 
	String fileName = null;

	public Agent(Color color, int i) {

	    setColor(color);
	    size = new Dimension(25, 25);
	    this.myID = i;
	    this.LeaderID = myID;
	    fileName = "./agents/a_" + this.myID + ".txt";
        try{
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("SecurityException: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("UnsupportedEncodingException: " + e.getMessage());
	    }
        try{
			if (color.equals(new Color(0,192,0)))
            	writer.println("green");
			if (color.equals(new Color(200,0,0)))
            	writer.println("red");
        } catch (NullPointerException  e) {
            System.err.println("NullPointerException: " + e.getMessage());
	    }
	    writer.close();

	}

	public void setAgentCount(int count){
		this.agent_count = count;
		this.interactedWith = new ArrayList<Agent>(agent_count);
		this.infectedThem = new ArrayList<Agent>(agent_count);
	}
	public void infect() {
	    this.infected = this.infected || true;
	    this.color = new Color(200,0,0);
	    try{
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("SecurityException: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("UnsupportedEncodingException: " + e.getMessage());
	    }
	    try{
			if (color.equals(new Color(0,192,0)))
            	writer.println("green");
			if (color.equals(new Color(200,0,0)))
            	writer.println("red");
        } catch (NullPointerException  e) {
            System.err.println("NullPointerException: " + e.getMessage());
	    }
	    writer.close();
	}

	public void engage(Agent withID) {
		this.interactedWith.add(withID);
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
	
	public void setInfoLocation(Point location) {
	    this.Infolocation = location;
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
	
	public void Infected(Agent a) {
	    this.infectedThem.add(a);
	}
	
	public ArrayList<Agent> interactions(){
		ArrayList<Agent> a = new ArrayList<Agent>(agent_count);
		a = (ArrayList<Agent>) this.interactedWith;
		return a;
	}
	
	public ArrayList<Agent> SuccesfulInteractions(){
		ArrayList<Agent> a = new ArrayList<Agent>(agent_count);
		a = (ArrayList<Agent>) this.infectedThem;
		return a;
	}

	protected void paint(Graphics2D g2d) {

	    Point p = getLocation();
	    if (p != null) {
		g2d.setColor(getColor());
		Dimension size = getSize();
		g2d.fillOval(p.x-size.width/2, p.y-size.height/2, size.width, size.height);
		g2d.setColor(Color.BLACK);
		Point q = this.Infolocation;
		//g2d.setTransform(AffineTransform.getRotateInstance(Math.toRadians(45), q.x, p.y));
		//g2d.drawString("" + this.myID ,q.x,  q.y);
		//g2d.drawString("" + this.LeaderID ,q.x-10,  q.y-10);
	    }

	}
}
