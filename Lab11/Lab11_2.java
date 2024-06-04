import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Lab11_2 extends JFrame{
    JLabel la = new JLabel("Love Java");

    public Lab11_2(){
        super("Wheel your mouse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int n = e.getWheelRotation();
                if(n > 0) zoomOut();
                else zoomIn();
            }
        });
        c.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                if(e.getKeyChar() == '-') zoomOut();
                else if(e.getKeyChar() == '+') zoomIn();
            }
        });

        la.setFont(new Font("Arial", Font.PLAIN, 15));
        c.add(la);

        setSize(500, 150);
        setVisible(true);

        c.setFocusable(true);
        c.requestFocus();
    }

    private void zoomIn(){
        Font f = la.getFont();
        int size = f.getSize();
        la.setFont(new Font("Times New Roman", Font.BOLD|Font.ITALIC, size+5));
    }

    private void zoomOut(){
        Font f = la.getFont();
        int size = f.getSize();
        if(size <= 5)
            return;
        la.setFont(new Font("Arial", Font.PLAIN, size-5));
    }

    static public void main(String [] args){
        new Lab11_2();
    }
}
