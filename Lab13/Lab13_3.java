import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Lab13_3 extends JFrame {
    private MyPanel p = new MyPanel();

    public Lab13_3() {
        setTitle("Menu & event");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(p);
        createMenu();
        setSize(250, 220);
        setVisible(true);
    }

    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                case "Load":
                    p.load();
                    break;
                case "Hide":
                    p.hide();
                    break;
                case "ReShow":
                    p.reShow();
                    break;
                case "Exit":
                    int result = JOptionPane.showConfirmDialog(getContentPane(),
                            "Exit?", "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION)
                        System.exit(0);
                    break;
            }
        }
    }

    class MyPanel extends JPanel {
        private Image img = null;
        private Image tmp = null;

        public void load() {
            img = new ImageIcon("images/dlwlrma.jpg").getImage();
            setPreferredSize(new Dimension(img.getWidth(this), img.getHeight(this)));
            pack();
            repaint();
        }

        public void hide() {
            tmp = img;
            img = null;
            repaint();
        }

        public void reShow() {
            img = tmp;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img == null) return;
            g.drawImage(img, 0, 0, this);
        }
    }

    private void createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem[] menuItem = new JMenuItem[4];
        String[] itemTitle = { "Load", "Hide", "ReShow", "Exit" };
        MenuActionListener listener = new MenuActionListener();

        for (int i = 0; i < menuItem.length; i++) {
            menuItem[i] = new JMenuItem(itemTitle[i]);
            menuItem[i].addActionListener(listener);
            menu.add(menuItem[i]);
        }

        mb.add(menu);
        setJMenuBar(mb);
    }

    public static void main(String[] args) {
        new Lab13_3();
    }
}
