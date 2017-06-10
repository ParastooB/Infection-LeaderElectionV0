import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Balls extends JPanel {

	public static final int AGENT_COUNT = 100;
	public static final int FrameSize = 1024;
	public Color myGreen = new Color(0,192,0);
	private List<Ball> ballsUp;
	private int infectedCount = 0;
	public long startTime = System.currentTimeMillis();
	private int rounds = 0;
	private int failed = 0;
	private int repeated = 0;
	private boolean locked;
	private boolean connection = false;
	private boolean electionCompleted = false;

	public Balls() {
	    ballsUp = new ArrayList<Ball>(AGENT_COUNT);

			int infected = random(AGENT_COUNT-1);
			int m = Math.min(FrameSize/2, FrameSize/2);
			int r = 4 * m / 5;
			int r2 = Math.abs(m - r) / 2;

	    for (int index = 0; index < AGENT_COUNT ; index++) {

				// set the colour 
				Ball ballNew = new Ball(new Color(5,80,120),index);
				if (index == infected){
					ballNew.infect();
					ballNew.setAgentCount(AGENT_COUNT);
					System.out.println("Agent " + index + " is initially infected");
					this.infection();
				}
				else {
					ballNew.setColor(myGreen); 
					ballNew.setAgentCount(AGENT_COUNT);
				}

			// set the starting position...
				double t = 2 * Math.PI * index / AGENT_COUNT;
				int x = (int) Math.round(FrameSize/2 + r * Math.cos(t));
				int y = (int) Math.round(FrameSize/2 + r * Math.sin(t));

			Dimension size = ballNew.getSize();

			if (x + size.width > FrameSize) {
				x = FrameSize - size.width;
			}
			if (y + size.height > FrameSize) {
				y = FrameSize - size.height;
			}

			ballNew.setLocation(new Point(x, y));
			ballsUp.add(ballNew);
	    }
	}

	@Override
	protected synchronized void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g.create();
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.drawString("Rounds: "+String.valueOf(this.rounds), 100, 100);
	    g2d.drawString("Failed: "+String.valueOf(this.failed+this.repeated), 100, 120);
	    g2d.drawString("Infected: "+String.valueOf(this.infectionCount()), 100, 140);
	    for (Ball ball : ballsUp) {
		ball.paint(g2d);
	        for (Ball iBall : ball.interactions()) {
	        	if (ball.SuccesfulInteractions().contains(iBall)){
	        		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{1}, 0);
	        		g2d.setStroke(dashed);
	        		g2d.setPaint(new Color(0,192,0));
	        		g2d.drawLine(ball.getLocation().x, ball.getLocation().y, iBall.getLocation().x, iBall.getLocation().y);
	        	}
	        	else {
	        		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
	        		g2d.setStroke(dashed);
	        		g2d.setPaint(new Color(192,192,192));
	        		g2d.drawLine(ball.getLocation().x, ball.getLocation().y, iBall.getLocation().x, iBall.getLocation().y);
	        	}
	    	}
	    }
	    g2d.dispose();
	}

	public synchronized void infection(){
		this.infectedCount ++;
	}
	
	public synchronized void rounds(){
		this.rounds ++;
	}
	
	public synchronized void failed(int errorCode){
		if (errorCode == 1)
			this.repeated ++;
		else if (errorCode == 2)
			this.failed ++;
	}
	
	public int infectionCount(){
		int a = this.infectedCount;
		return a;
	}
	
	public int roundsCount(){
		int a = this.rounds;
		return a;
	}
	
	public List<Ball> getBalls() {
	    return ballsUp;
	}
	
	public boolean isElectionComplete() {
	    return this.electionCompleted;
	}
	
	public void electionIsComplete() {
	    this.electionCompleted = true;
	}

	public static int random(int maxRange) {
        return (int) Math.round((Math.random() * maxRange));
    }
}
