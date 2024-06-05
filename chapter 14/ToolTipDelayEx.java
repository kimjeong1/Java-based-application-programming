import javax.swing.*;
import java.awt.*;
public class ToolTipDelayEx extends JFrame {
    public ToolTipDelayEx() {
        setTitle("툴팁 지연 시간 제어 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        JLabel cherryLabel = new JLabel(new
                ImageIcon("images/cherry.jpg"));
        cherryLabel.setToolTipText("체리 이미지 어때요");
        JLabel appleLabel = new JLabel(new ImageIcon("images/apple.jpg"));
        appleLabel.setToolTipText("사과 이미지 어때요");
        c.add(cherryLabel);
        c.add(appleLabel);
        ToolTipManager m = ToolTipManager.sharedInstance();
        m.setInitialDelay(0);
        m.setDismissDelay(10000);
        setSize(400,200);
        setVisible(true);
    }
    public static void main(String [] args) {
        new ToolTipDelayEx();
    }
}
