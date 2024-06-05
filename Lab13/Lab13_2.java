import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Lab13_2 extends JFrame {
    private JLabel resultLabel = new JLabel("Result");

    public Lab13_2() {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane(); // getContentPane 메소드 호출 추가
        c.setLayout(new FlowLayout());

        JButton btn = new JButton("calculate");
        btn.addActionListener(new MyActionListener());
        c.add(btn);

        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.GREEN);
        c.add(resultLabel);

        setSize(250, 200);
        setVisible(true);
    }

    class MyActionListener implements ActionListener {
        private CalcDialog dialog;

        public MyActionListener() {
            dialog = new CalcDialog(Lab13_2.this); // 생성자에서 인스턴스 생성 위치 수정
        }

        public void actionPerformed(ActionEvent e) {
            dialog.setVisible(true);
            if (dialog.isValid())
                resultLabel.setText(Integer.toString(dialog.getResult()));
        }

        class CalcDialog extends JDialog {
            private int sum = 0;
            private boolean bValid = false;
            private JTextField a = new JTextField(10);
            private JTextField b = new JTextField(10);
            private JButton addBtn = new JButton("   Add   "); // 버튼 라벨 수정

            public CalcDialog(JFrame f) {
                super(f, "Calculation Dialog", true);
                setLayout(new FlowLayout());

                add(new JLabel("Input two integers."));
                add(a);
                add(b);
                add(addBtn); // 버튼 추가

                addBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int x = Integer.parseInt(a.getText());
                            int y = Integer.parseInt(b.getText());
                            sum = x + y;
                            bValid = true;
                        } catch (NumberFormatException e2) {
                            JOptionPane.showMessageDialog(CalcDialog.this,
                                    "Input only Numbers.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        CalcDialog.this.setVisible(false);
                    }
                });
                setSize(200, 200); // setSize 위치 변경
            }

            public boolean isValid() {
                return bValid;
            }

            public int getResult() {
                return sum;
            }
        }
    }

    public static void main(String[] arg) {
        new Lab13_2();
    }
}
