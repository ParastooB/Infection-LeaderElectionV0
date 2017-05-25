import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class SimpleBalls {

	// Agent count
	public static final int AGENT_COUNT = 200;
	public static final int FrameSize = 1024;
	Color myRed = new Color(200,0,0);
	Color myGreen = new Color(0,200,0);
	public int infectedCount = 0;
	long startTime = System.currentTimeMillis();
	public int rounds = 0;
	public int failed = 0;

    public static void main(String[] args) {
        new SimpleBalls();
    }

    public SimpleBalls() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Spot");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                Balls balls = new Balls();
                frame.add(balls);
                frame.setSize(FrameSize, FrameSize);
                frame.setVisible(true);

                new Thread(new BounceEngine(balls)).start();

            }
        });
    }

    public static int random(int maxRange) {
        return (int) Math.round((Math.random() * maxRange));
    }

    public class Balls extends JPanel {

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
					ballNew.setColor(myRed); 
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
    }

    public class BounceEngine implements Runnable {

        private Balls parent;

        public BounceEngine(Balls parent) {
            this.parent = parent;
        }

        @Override
        public void run() {

            int width = getParent().getWidth();
            int height = getParent().getHeight();

			//parent(random(AGENT_COUNT))


            while (getParent().isVisible()) {

                // Repaint the balls pen...
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        getParent().repaint();
                    }
                });
				infectedCount = 0;
				for (Ball ball : getParent().getBalls()) {
					if (ball.isInfected())
						infectedCount += 1;
				}
				if (infectedCount == AGENT_COUNT){
					long endTime   = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					System.out.println("Time it took to infect every one: "+totalTime);
					System.out.println(failed + " interactions failed");
					break;
				}
				else {
// not every time they connect they can infect
					Ball b = getParent().getBalls().get(random(AGENT_COUNT-1));
                    b.setColor(myRed); 
					if (b.isInfected())
						failed += 1;
					b.infect();
				}
				rounds += 1;
				System.out.println("This is round: "+ rounds + ", "+ infectedCount + " agents are infected now");
                // Some small delay...
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }

            }

        }

        public Balls getParent() {
            return parent;
        }
    }

    public class Ball {

        private Color color;
        private Point location;
        private Dimension size;
		private boolean infected;

        public Ball(Color color) {

            setColor(color);
            size = new Dimension(10, 10);

        }

        public void infect() {
            this.infected = this.infected || true;
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

        public boolean isInfected() {
            return this.infected;
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
}
