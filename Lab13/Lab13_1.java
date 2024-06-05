import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Lab13_1 extends JFrame {
    public Lab13_1() {
        super("Only numbers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();

        JToolBar tBar = new JToolBar();
        tBar.add(new JLabel("Student ID: "));
        JTextField tf = new JTextField();
        tf.addKeyListener(new MyKeyListener());
        tBar.add(tf);
        c.add(tBar, BorderLayout.SOUTH);
        
        setSize(400, 300);
        setVisible(true);
    }

    class MyKeyListener extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                String k = Character.toString(e.getKeyChar());
                k = k.concat(" is not a number. \nInput only numbers.");
                JOptionPane.showMessageDialog(getContentPane(), k, 
                        "Error", JOptionPane.ERROR_MESSAGE);
                e.consume(); // 추가: 잘못된 입력을 소비하여 텍스트 필드에 추가되지 않도록 함
            }
        }
    }

    public static void main(String[] args) {
        new Lab13_1();
    }
}
