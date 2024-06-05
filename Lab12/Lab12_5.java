import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Lab12_5 extends JFrame {
    private int[] data = {0, 0, 0, 0};
    private int[] arcAngle = new int[4];
    private Color[] color = {Color.RED, Color.BLUE, Color.MAGENTA, Color.ORANGE};
    private String[] itemName = {"apple", "cherry", "strawberry", "prune"};
    private JTextField[] tf = new JTextField[4];
    private ChartPanel chartPanel = new ChartPanel();

    public Lab12_5() {
        setTitle("Pie chart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.add(new InputPanel(), BorderLayout.NORTH);
        c.add(chartPanel, BorderLayout.CENTER);
        setSize(500, 350);
        setVisible(true);
    }

    private void drawChart() {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            data[i] = Integer.parseInt(tf[i].getText());
            sum += data[i];
        }
        if (sum == 0) return;
        for (int i = 0; i < data.length; i++) {
            arcAngle[i] = (int) Math.round(data[i] / sum * 360);
            chartPanel.repaint();
        }
    }

    private class InputPanel extends JPanel {  // Corrected class name
        public InputPanel() {  // Corrected constructor name
            setBackground(Color.LIGHT_GRAY);
            for (int i = 0; i < tf.length; i++) {
                tf[i] = new JTextField("0", 5);
                tf[i].addActionListener(new MyActionListener());  // Corrected ActionListener class name
                add(new JLabel(itemName[i]));
                add(tf[i]);
            }
        }

        private class MyActionListener implements ActionListener {  // Corrected class name
            public void actionPerformed(ActionEvent e) {
                JTextField t = (JTextField) e.getSource();
                try {
                    Integer.parseInt(t.getText());
                } catch (NumberFormatException ex) {
                    t.setText("0");
                }
                drawChart();
            }
        }
    }

    private class ChartPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int startAngle = 0;
            for (int i = 0; i < data.length; i++) {
                g.setColor(color[i]);
                g.drawString(itemName[i] + ' ' +
                        Math.round(arcAngle[i] * 100 / 360.0) + "%", 50 + i * 100, 20);
            }
            for (int i = 0; i < data.length; i++) {
                g.setColor(color[i]);
                g.fillArc(150, 50, 200, 200, startAngle, arcAngle[i]);
                startAngle = startAngle + arcAngle[i];
            }
        }
    }

    public static void main(String[] args) {
        new Lab12_5();
    }
}
