import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class BounceEngine implements Runnable {


	private Balls parent;
	private Ball singleBall;

	public BounceEngine(Balls parent, Ball singleBall) {
	    this.parent = parent;
	    this.singleBall = singleBall;
	}

	@Override
	public void run() {

		int width = getParent().getWidth();
		int height = getParent().getHeight();

		while (getParent().isVisible() && !parent.isElectionComplete()) {

			// Repaint the balls pen...
			SwingUtilities.invokeLater(new Runnable() {
			    @Override
			    public void run() {
//				getParent().repaint();
			    	getParent().updateUI();
			    }
			});

			// DANGER : infectedCount is global but synchronized
			if (parent.infectionCount() == parent.AGENT_COUNT){
//				long endTime   = System.currentTimeMillis();
//				long totalTime = endTime - parent.startTime;
//				System.out.println("Time done: "+totalTime + " they finished at round " + parent.roundsCount());
				System.out.println("They finished at round " + parent.roundsCount());
				parent.electionIsComplete();
//				break;
			}
			else {
			// not every time they connect they can infect
				
				Ball b = getParent().getBalls().get(parent.random(parent.AGENT_COUNT-1));
				System.out.println("Agent " + singleBall.getID() + " attemping to connect to agent "+ b.getID());
			// DANGER : failed is global
				if (!b.isEngaged() && !singleBall.isEngaged() && (b.getID() != singleBall.getID()) ){
					b.engage(singleBall);
					singleBall.engage(b);
				  	if (b.isInfected() && !singleBall.isInfected()){
						singleBall.infect();
						b.Infected(singleBall);
						System.out.println("	Agent " + singleBall.getID() + " got infected by agent "+ b.getID());
						parent.infection();
				  	}
					else if (singleBall.isInfected() && !b.isInfected()){
						b.infect();
						singleBall.Infected(b);
						System.out.println("	Agent " + singleBall.getID() + " infected agent "+ b.getID());
						parent.infection();
					}
					else {
						System.out.println("	This interaction didn't change state of the agents");
						parent.failed(1);
					}
					b.disengage();
					singleBall.disengage();
				}
				else {
					System.out.println("	Connection failed!");
					parent.failed(2);
				}
				// LOCK
				parent.rounds();
				System.out.println("	This is round: "+ parent.roundsCount());
				
				// Some small delay...
				// When they finish depends on how much they sleep
				try {
				    Thread.sleep(80);
				} catch (InterruptedException ex) {
				}
			}

		}

	}

	public Balls getParent() {
	    return parent;
	}
}
