import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Lab11_3 extends JFrame{
    private JLabel[] gameLabels = new JLabel[10];
    private int nextPressed = 0;

    public Lab11_3(){
        super("Click 0 to 9");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);

        setSize(300, 300);
        setVisible(true);

        for(int i = 0; i<gameLabels.length; i++){
            gameLabels[i] = new JLabel(Integer.toString(i));
            gameLabels[i].setForeground(Color.MAGENTA);
            gameLabels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JLabel la = (JLabel) e.getSource();
                    if(Integer.parseInt(la.getText()) == nextPressed){
                        nextPressed++;
                        if(nextPressed == 10){
                            nextPressed = 0;
                            configure();
                        }
                        else la.setVisible(false);
                    }
                }
            });
            c.add(gameLabels[i]);
        }
        configure();
    }

    private void configure(){
        Container c = getContentPane();
        for(int i = 0; i<gameLabels.length; i++){
            gameLabels[i].setSize(15,15);
            int xBound = c.getWidth() - gameLabels[i].getWidth();
            int yBound = c.getHeight() - gameLabels[i].getHeight();
            int x = (int)(Math.random()*xBound);
            int y = (int)(Math.random()*yBound);
            gameLabels[i].setLocation(x,y);
            gameLabels[i].setVisible(true);
        }
    }

    static public void main(String [] args){
        new Lab11_3();
    }
}
