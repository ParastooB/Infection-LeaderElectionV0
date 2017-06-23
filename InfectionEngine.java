import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class InfectionEngine implements Runnable {


	private Agents parent;
	private Agent iAgent;

	public InfectionEngine(Agents parent, Agent iAgent) {
	    this.parent = parent;
	    this.iAgent = iAgent;
	}

	@Override
	public void run() {

		int width = getParent().getWidth();
		int height = getParent().getHeight();

		while (getParent().isVisible() && !parent.isElectionComplete()) {
/*
				// Some small delay...
				// When they finish depends on how much they sleep
				try {
				    Thread.sleep(500);
				} catch (InterruptedException ex) {
				}*/

			// Repaint the agents pen...
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
				System.out.println("Thread "+iAgent.getID() +" finished at round " + parent.roundsCount() + ", threads remaining: " + Thread.activeCount());
				parent.electionIsComplete();
//				killThreads();
//				break;
			}
			else {
			// not every time they connect they can infect
				
				Agent b = getParent().getAgents().get(parent.random(parent.AGENT_COUNT-1));
				System.out.println("Agent " + iAgent.getID() + " attemping to connect to agent "+ b.getID());
			// DANGER : failed is global
				if (!b.isEngaged() && !iAgent.isEngaged() && (b.getID() != iAgent.getID()) ){
					b.engage(iAgent);
					iAgent.engage(b);
				  	if (b.isInfected() && !iAgent.isInfected()){
						iAgent.infect();
						b.Infected(iAgent);
						System.out.println("	Agent " + iAgent.getID() + " got infected by agent "+ b.getID());
						parent.infection();
				  	}
					else if (iAgent.isInfected() && !b.isInfected()){
						b.infect();
						iAgent.Infected(b);
						System.out.println("	Agent " + iAgent.getID() + " infected agent "+ b.getID());
						parent.infection();
					}
					else {
						System.out.println("	This interaction didn't change state of the agents");
						parent.failed(1);
					}
/*				  	iAgent.updateLeader(b.getID());
					b.updateLeader(iAgent.getID());*/
					b.disengage();
					iAgent.disengage();
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
				    Thread.sleep(10);
				} catch (InterruptedException ex) {
				}
			}

		}

	}

	public Agents getParent() {
	    return parent;
	}
	
	public synchronized void killThreads(){
		if (! Thread.currentThread().getThreadGroup().isDestroyed())
			Thread.currentThread().getThreadGroup().destroy();
	}
}
