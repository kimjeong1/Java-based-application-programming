import java.awt.*;
import javax.swing.*;

public class Lab10_2 extends JFrame{
    public Lab10_2(){
        super("Calculator Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        JPanel n = new JPanel();
        n.setLayout(new FlowLayout());
        n.setBackground(Color.LIGHT_GRAY);
        n.add(new JLabel("Enter formula"));
        n.add(new JTextField(15));

        c.add(n, BorderLayout.NORTH);
        c.add(new CenterPanel(), BorderLayout.CENTER);
        c.add(new SouthPanel(), BorderLayout.SOUTH);

        setSize(350,400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Lab10_2();
    }
}
class CenterPanel extends JPanel{
    public CenterPanel(){
        setLayout(new GridLayout(4,4,10,10));
        for(int i = 0; i<10; i++)
            add(new JButton(Integer.toString(i)));
        add(new JButton("CE"));
        add(new JButton("Run"));
        JButton[] btns = {new JButton("+"), new JButton("-"),
                new JButton("x"), new JButton("/")};
        for(int i =0; i<4; i++){
            btns[i].setBackground(Color.CYAN);
            add(btns[i]);
        }
    }
}

class SouthPanel extends JPanel{
    public SouthPanel(){
        setLayout(new FlowLayout());
        setBackground(Color.YELLOW);
        add(new JLabel("Result"));
        add(new JTextField(15));
    }
}
