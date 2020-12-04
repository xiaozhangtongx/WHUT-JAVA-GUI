package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author zhang
 * @version 1.0
 * 2020/12/2 10:16
 * @Todo: SDKJ$
 */
public class MainGUI extends JFrame{
    public static void main(String[] args) {
        JFrame frame = new JFrame("登录");
        frame.setContentPane(new LoginFrame(frame).contentpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocation(500,200);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    public static void showMessage(Component component, String msg, String title) {
        JOptionPane.showMessageDialog(component, msg, title, JOptionPane.YES_NO_OPTION);
    }
    public static int showConfirmMessage(Component component, String msg, String title) {
        return JOptionPane.showConfirmDialog(component, msg, title, JOptionPane.OK_CANCEL_OPTION);
    }

}
