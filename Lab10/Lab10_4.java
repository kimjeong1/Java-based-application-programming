import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Lab10_4 extends JFrame{
    public Lab10_4() {
        super("Click \"C\"");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);

        JLabel label = new JLabel("C");
        c.add(label);

        label.setLocation(100,100);
        label.setSize(20,20);
        label.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                JLabel la = (JLabel)e.getSource();
                int xBound = c.getWidth() - la.getWidth();
                int yBound = c.getHeight() - la.getHeight();
                int x = (int)(Math.random() * xBound);
                int y = (int)(Math.random() * yBound);
                la.setLocation(x, y);
            }
        });

        setSize(300,300);
        setVisible(true);
    }

    static public void main(String[] args){
        new Lab10_4();
    }
}
