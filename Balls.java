import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Balls extends JPanel {

	public static final int AGENT_COUNT = 5;
	public static final int FrameSize = 512;
	public Color myGreen = new Color(0,200,0);
	private List<Ball> ballsUp;
	public int infectedCount = 0;
	public long startTime = System.currentTimeMillis();
	public int rounds = 0;
	public int failed = 0;
	public boolean locked;
	public boolean connection = false;

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
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g.create();
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    for (Ball ball : ballsUp) {
		ball.paint(g2d);
	        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
	        g2d.setStroke(dashed);
	        g2d.setPaint(Color.blue);
	        for (Ball iBall : ball.interactions())
	        	g2d.drawLine(ball.getLocation().x, ball.getLocation().y, iBall.getLocation().x, iBall.getLocation().y);
	    }
	    g2d.dispose();
	}

	public List<Ball> getBalls() {
	    return ballsUp;
	}

	public static int random(int maxRange) {
        return (int) Math.round((Math.random() * maxRange));
    }
}
