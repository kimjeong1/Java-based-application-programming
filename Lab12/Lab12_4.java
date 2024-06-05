import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Lab12_4 extends JFrame {
    public Lab12_4() {
        super("dlwlrlam.jpg");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MyPanel());
        setSize(500, 550);
        setVisible(true);
        getContentPane().setFocusable(true);
        getContentPane().requestFocus();
    }

    class MyPanel extends JPanel {
        private ImageIcon img = new ImageIcon("images/dlwlrma.jpg");
        private int x = 10, y = 10, width, height;

        public MyPanel() {
            width = img.getIconWidth();
            height = img.getIconHeight();

            MyMouseListener ml = new MyMouseListener();
            addMouseListener(ml);
            addMouseMotionListener(ml);
            setFocusable(true);
            requestFocusInWindow();
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == '+') {
                        width = (int) (width * 1.1);
                        height = (int) (height * 1.1);
                        repaint();
                    } else if (e.getKeyChar() == '-') {
                        width = (int) (width * 0.9);
                        height = (int) (height * 0.9);
                        repaint();
                    }
                }
            });
        }

        class MyMouseListener extends MouseAdapter {
            private java.awt.Point startP = null;
            private boolean isMove = false;

            public void mousePressed(MouseEvent e) {
                startP = e.getPoint();
                if ((startP.x >= x && startP.x <= x + width) &&
                        (startP.y >= y && startP.y <= y + height)) {
                    isMove = true;
                }
            }

            public void mouseReleased(MouseEvent e) {
                isMove = false;
            }

            public void mouseDragged(MouseEvent e) {
                if (!isMove) return;
                java.awt.Point endP = e.getPoint();
                x = x + endP.x - startP.x;
                y = y + endP.y - startP.y;
                startP = endP;
                repaint();
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img.getImage(), x, y, width, height, this);
        }
    }

    public static void main(String[] args) {
        new Lab12_4();
    }
}
