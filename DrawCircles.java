import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class DrawCircles extends JPanel {
    private static void create() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// number of agaents 
        f.add(new CircleTest(20));
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                create();
            }
        });
    }
}
