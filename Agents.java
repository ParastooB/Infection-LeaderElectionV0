import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Agents extends JPanel {

	public static final int AGENT_COUNT = 4;
	public static final int FrameSize = 800;
	public Color myGreen = new Color(0,192,0);
	private List<Agent> agentsOnNode;
	private List<Integer> agentIDs;
	private int infectedCount = 0;
	public long startTime = System.currentTimeMillis();
	private int rounds = 0;
	private int failed = 0;
	private int repeated = 0;
	private boolean locked;
	private boolean connection = false;
	private boolean electionCompleted = false;
	private int tempID;

	public Agents() {
	    agentsOnNode = new ArrayList<Agent>(AGENT_COUNT);

		int infected = random(AGENT_COUNT-1);
		int m = Math.min(FrameSize/2, FrameSize/2);
		int r = 4 * m / 5;
		agentIDs = new ArrayList<Integer>(AGENT_COUNT);

	    for (int index = 0; index < AGENT_COUNT ; index++) {

				// set the colour 
/*	    		tempID = random(1000);
	    		while(agentIDs.contains(tempID))
	    			tempID = random(1000);
				Agent agentNew = new Agent(new Color(5,80,120),tempID);*/

// only for test, to be deleted later:
				Agent agentNew = new Agent(myGreen,index+1);
//------------------------------------
//				if (index == infected){
				if (index == 0){ //forcing agent 1 to be infected
					agentNew.infect();
					agentNew.setAgentCount(AGENT_COUNT);
					System.out.println("Agent " + index + " is initially infected");
					this.infection();
				}
				else {
					agentNew.setColor(myGreen); 
					agentNew.setAgentCount(AGENT_COUNT);
				}

			// set the starting position...
			double t = 2 * Math.PI * index / AGENT_COUNT;
			int x = (int) Math.round(FrameSize/2 + r * Math.cos(t));
			int y = (int) Math.round(FrameSize/2 + r * Math.sin(t));

			Dimension size = agentNew.getSize();

			if (x + size.width > FrameSize) {
				x = FrameSize - size.width;
			}
			if (y + size.height > FrameSize) {
				y = FrameSize - size.height;
			}

			agentNew.setLocation(new Point(x, y));
			
			int s = 19 * m / 20;
			int xi = (int) Math.round(FrameSize/2 + s * Math.cos(t) - 1);
			int yi = (int) Math.round(FrameSize/2 + s * Math.sin(t) - 1);
			
			agentNew.setInfoLocation(new Point(xi,yi));
			agentsOnNode.add(agentNew);
			agentIDs.add(tempID);
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
	    for (Agent agent : agentsOnNode) {
		agent.paint(g2d);
	        for (Agent iA : agent.interactions()) {
	        	if (agent.SuccesfulInteractions().contains(iA)){
	        		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{1}, 0);
	        		g2d.setStroke(dashed);
	        		g2d.setPaint(new Color(0,192,0));
	        		g2d.drawLine(agent.getLocation().x, agent.getLocation().y, iA.getLocation().x, iA.getLocation().y);
	        	}
	        	else {
	        		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
	        		g2d.setStroke(dashed);
	        		g2d.setPaint(new Color(192,192,192));
	        		g2d.drawLine(agent.getLocation().x, agent.getLocation().y, iA.getLocation().x, iA.getLocation().y);
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
	
	public List<Agent> getAgents() {
	    return agentsOnNode;
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
