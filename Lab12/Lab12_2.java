import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
public class Lab12_2 extends JFrame{
    private JTextArea ta = new JTextArea(5,10);
    private JSlider slider = new JSlider(0,200,0);

    public Lab12_2(){
        super("TextArea with JSlider");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new JScrollPane(ta), BorderLayout.NORTH);
        c.add(slider, BorderLayout.CENTER);

        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider s = (JSlider) e.getSource();
                if(ta.getText().length() <= s.getValue())
                    s.setValue(ta.getText().length());
                else{
                    try{
                        ta.setText(ta.getText(0, s.getValue()));
                    } catch (BadLocationException e1) {}

                }
            }
        });

        ta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                JTextArea t = (JTextArea) e.getSource();
                int size = t.getText().length();
                try{
                    if(size>= 200)
                        t.setText(t.getText(0, 200));
                } catch (BadLocationException ex){}
                slider.setValue(size);
            }
        });

        setSize(300, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Lab12_2();
    }
}
