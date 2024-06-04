import java.awt.*;
import javax.print.attribute.standard.RequestingUserName;
import javax.swing.*;
import java.util.Random;
public class Lab10_1 extends JFrame{
    public Lab10_1(){
        super("Ten Color Buttons Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int numBtns = 10;
        Container c = getContentPane();
        c.setLayout(new GridLayout(1, numBtns));

        for(int i = 0; i<numBtns; i++){
            Random rnd = new Random();
            int r = (int)(Math.random() * 256);
            int g = rnd.nextInt(256);
            int b = rnd.nextInt(256);
            JButton btn = new JButton(Integer.toString(i));
            btn.setBackground(new Color(r,g,b));
            c.add(btn);
        }

        setSize(600, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Lab10_1();
    }
}
