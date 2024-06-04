import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Lab10_5 extends JFrame{
    public Lab10_5(){
        super("Click \"C\"");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);
        c.addMouseListener(new MyMouseAdapter());

        setSize(300,300);
        setVisible(true);
    }

    static public void main(String[] args){
        new Lab10_5();
    }

    class MyMouseAdapter extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            JLabel la = new JLabel();
            la.setSize(5,5);
            int x = e.getX();
            int y = e.getY();
            la.setLocation(x, y);
            int r = (int)(Math.random() * 256);
            int g = (int)(Math.random() * 256);
            int b = (int)(Math.random() * 256);

            la.setOpaque(true);
            la.setBackground(new Color(r,g,b));
            Container c = (Container)e.getSource();
            c.add(la);

            c.revalidate();
            c.repaint();
        }
    }
}
