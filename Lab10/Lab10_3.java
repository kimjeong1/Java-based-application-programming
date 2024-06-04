import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Lab10_3 extends JFrame{
    private Container c = getContentPane();

    public Lab10_3(){
        super("Click \"C\"");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.setLayout(null);

        JLabel label = new JLabel("C");
        c.add(label);

        label.setLocation(100,100);
        label.setSize(20,20);
        label.addMouseListener(new MyMouseListener());

        setSize(300,300);
        setVisible(true);
    }

    private class MyMouseListener implements MouseListener{
        public void mousePressed(MouseEvent e){
            JLabel la = (JLabel)e.getSource();
            int xBound = Lab10_3.this.getContentPane().getWidth()- la.getWidth();
            int yBound = c.getHeight() - la.getHeight();
            int x = (int)(Math.random() * xBound);
            int y = (int)(Math.random() * yBound);
            la.setLocation(x, y);
        }
        public void mouseReleased(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }

    static public void main(String [] args){
        new Lab10_3();
    }
}

