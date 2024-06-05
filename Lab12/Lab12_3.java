import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Lab12_3 extends JFrame {
    public Lab12_3() {
        super("Draw Polygon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(new MyPanel());
        setSize(300, 300);
        setVisible(true);
    }

    class MyPanel extends JPanel {
        private Vector<Integer> x = new Vector<>();
        private Vector<Integer> y = new Vector<>();

        public MyPanel() {
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    x.add(e.getX());
                    y.add(e.getY());
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.MAGENTA);

            int[] xs = new int[x.size()];
            int[] ys = new int[y.size()];

            for (int i = 0; i < x.size(); i++) {
                xs[i] = x.get(i);
                ys[i] = y.get(i);
            }

            if (xs.length > 1) {
                g.drawPolygon(xs, ys, xs.length);
            } else if (xs.length == 1) {
                g.drawOval(xs[0], ys[0], 1, 1); // single point
            }
        }
    }

    public static void main(String[] args) {
        new Lab12_3();
    }
}
