import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Lab12_1 extends JFrame{
    public Lab12_1(){
        setTitle("Money changer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MyPanel());
        setSize(300,300);
        setVisible(true);
    }

    class MyPanel extends JPanel{
        private int [] unit = {10000, 1000, 500, 100, 50, 10, 1};
        private String [] text = {"만원","천원","500원","100원","50원","10원","1원"};
        private JTextField [] tf = new JTextField[7];
        private JCheckBox [] cb = new JCheckBox[6];

        public MyPanel(){
            setBackground(Color.WHITE);
            setLayout(null);

            JLabel la = new JLabel("금액");
            la.setSize(30, 20); la.setLocation(60, 20);
            JTextField source = new JTextField(30);
            source.setSize(100, 20); source.setLocation(100, 20);
            JButton calcBtn = new JButton("계산");
            calcBtn.setSize(70, 20); calcBtn.setLocation(210, 20);
            add(la); add(source); add(calcBtn);

            for(int i = 0; i<text.length; i++){
                JLabel la2 = new JLabel(text[i]);
                la2.setHorizontalAlignment(JLabel.RIGHT);
                la2.setSize(50,20); la2.setLocation(50,50+i*20);
                tf[i] = new JTextField(30);
                tf[i].setHorizontalAlignment(JTextField.CENTER);
                tf[i].setSize(70,20);
                tf[i].setLocation(120, 50+i*20);
                tf[i].setEditable(false);
                add(la2); add(tf[i]);
            }

            for(int i = 0; i<cb.length; i++){
                cb[i]= new JCheckBox("", true);
                cb[i].setOpaque(false);
                cb[i].setSize(30,20); cb[i].setLocation(200,50+i*20);
                add(cb[i]);
            }

            calcBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String str = source.getText();
                    if(str.length() == 0) return;

                    int money = Integer.parseInt(str);
                    int res;

                    for(int i = 0; i<unit.length; i++){
                        if(i == unit.length -1){
                            tf[i].setText(Integer.toString(money));
                            break;
                        }
                        if(!cb[i].isSelected()){
                            tf[i].setText("0");
                            continue;
                        }
                        res = money / unit[i];
                        tf[i].setText(Integer.toString(res));
                        if(res>0)
                            money = money % unit[i];
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        new Lab12_1();
    }
}
