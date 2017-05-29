import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Balls extends JPanel {

	public static final int AGENT_COUNT = 20;
	public static final int FrameSize = 1024;
	public Color myGreen = new Color(0,200,0);
	private List<Ball> ballsUp;

	public Balls() {
	    ballsUp = new ArrayList<Ball>(AGENT_COUNT);

			int infected = random(AGENT_COUNT);
			int m = Math.min(FrameSize/2, FrameSize/2);
			int r = 4 * m / 5;
			int r2 = Math.abs(m - r) / 2;

	    for (int index = 0; index < AGENT_COUNT ; index++) {

				// set the colour 
				Ball ballNew = new Ball(new Color(5,80,120));
				if (index == infected){
					ballNew.infect();
				}
				else
				ballNew.setColor(myGreen); 

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
