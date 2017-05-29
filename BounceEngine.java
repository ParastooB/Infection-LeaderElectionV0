import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class BounceEngine implements Runnable {


	private Balls parent;
	private Ball singleBall;
	private int iCount = 0;

	public BounceEngine(Balls parent, Ball singleBall) {
	    this.parent = parent;
	    this.singleBall = singleBall;
	}

	@Override
	public void run() {

		int width = getParent().getWidth();
		int height = getParent().getHeight();

		while (getParent().isVisible()) {

			// Repaint the balls pen...
			SwingUtilities.invokeLater(new Runnable() {
			    @Override
			    public void run() {
				getParent().repaint();
			    }
			});

			// DANGER : infectedCount is global
			iCount = 0;
			for (Ball ball : getParent().getBalls()) {
				if (ball.isInfected())
					iCount += 1;
			}
			if (iCount == parent.AGENT_COUNT){
			//if (parent.rounds == 4*parent.AGENT_COUNT){
				long endTime   = System.currentTimeMillis();
				long totalTime = endTime - parent.startTime;
				System.out.println("Time done: "+totalTime);
				// System.out.println(failed + " interactions failed");
				break;
			}
			else {
			// not every time they connect they can infect
				Ball b = getParent().getBalls().get(parent.random(parent.AGENT_COUNT-1));
				System.out.println("Agent " + singleBall.getID() + " attemping to connect to agent "+ b.getID());
			// DANGER : failed is global
				if (!b.isEngaged() && (b.getID() != singleBall.getID()) ){
					b.engage();
				  	if (b.isInfected() && !singleBall.isInfected()){
						singleBall.infect();
						System.out.println("	Agent " + singleBall.getID() + " got infected by agent "+ b.getID());
				  	}
					else if (singleBall.isInfected() && !b.isInfected()){
						b.infect();
						System.out.println("	Agent " + singleBall.getID() + " infected agent "+ b.getID());
					}
					else 
						System.out.println("	This interaction didn't change state of the agents");
					b.disengage();
				}
				else
					System.out.println("	Connection failed!");
			}
			
			// DANGER: rounds is GLOBAL
			// LOCK
			if (! parent.locked ){	
				parent.locked = true;	
				parent.rounds += 1;
				parent.locked = false;
			}
			System.out.println("	This is round: "+ parent.rounds );
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
