import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class BounceEngine implements Runnable {

	public int infectedCount = 0;
	public long startTime = System.currentTimeMillis();
	public int rounds = 0;
	public int failed = 0;

	private Balls parent;

	public BounceEngine(Balls parent) {
	    this.parent = parent;
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
			infectedCount = 0;
			for (Ball ball : getParent().getBalls()) {
				if (ball.isInfected())
					infectedCount += 1;
			}
			// if (infectedCount == parent.AGENT_COUNT){
			if (rounds == 4*parent.AGENT_COUNT){
				long endTime   = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				System.out.println("Time it took to infect every one: "+totalTime);
				// System.out.println(failed + " interactions failed");
				break;
			}
			else {
			// not every time they connect they can infect
				Ball b = getParent().getBalls().get(parent.random(parent.AGENT_COUNT-1));
			// DANGER : fail is global
				if (!b.isEngaged()){
					b.engage();
				  	if (b.isInfected())
						failed += 1;
					b.infect();
					b.disengage();
				}
			}
			
			// DANGER: rounds is GLOBAL		
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
