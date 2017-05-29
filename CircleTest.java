/*Source: https://stackoverflow.com/questions/2508704/draw-a-circle-with-a-radius-and-points-around-the-edge*/
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class CircleTest extends JPanel {

    private static final int SIZE = 512;
    private int a = SIZE / 2;
    private int b = a;
    private int r = 4 * SIZE / 5;
    private int n;

    /** @param n  the desired number of circles. */
    public CircleTest(int n) {
        super(true);
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        this.n = n;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        a = getWidth() / 2;
        b = getHeight() / 2;
        int m = Math.min(a, b);
        r = 4 * m / 5;
        int r2 = Math.abs(m - r) / 2;
        g2d.drawOval(a - r, b - r, 2 * r, 2 * r);
        for (int i = 0; i < n; i++) {
            double t = 2 * Math.PI * i / n;
            int x = (int) Math.round(a + r * Math.cos(t));
            int y = (int) Math.round(b + r * Math.sin(t));
			if (i == 0)
				g2d.setColor(Color.red);
			else 
				g2d.setColor(Color.green);
            g2d.fillOval(x - r2, y - r2, 2 * r2, 2 * r2);
        }
    }
}
