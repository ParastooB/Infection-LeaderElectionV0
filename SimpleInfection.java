/*Soucre : https://stackoverflow.com/questions/13022754/java-bouncing-ball*/

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class SimpleInfection {

	public static final int FrameSize = 900;

    public static void main(String[] args) {
        new SimpleInfection();
    }

    public SimpleInfection() {
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

                // JFrame frame = new JFrame("Spot");
                // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // frame.setLayout(new BorderLayout());
                Agents agents = new Agents();
                // frame.add(agents);
                // frame.setSize(FrameSize, FrameSize);
                // frame.setVisible(true);
                
                ThreadGroup tg = new ThreadGroup ("1");
                for (Agent agent : agents.getAgents()) {
                	InfectionEngine gb = new InfectionEngine(agents,agent);
                    new Thread(tg, gb, "Thread for " + agent.getID()).start();
                }
                //tg.list();

            }
        });
    }
}
