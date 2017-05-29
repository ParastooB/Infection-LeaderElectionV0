import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class SimpleBalls {

	// Agent count
	public static final int FrameSize = 1024;

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
        		//new Thread(new BounceEngine(balls,new Ball(new Color(5,80,120),2))).start();
                for (Ball ball : balls.getBalls()) {
                    new Thread(new BounceEngine(balls,ball)).start();
                }

            }
        });
    }
}
