package frame;

import console.AbstractUser;
import console.DataProcessing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * @author zhang
 * @version 1.0
 * 2020/12/2 10:57
 * @Todo: $
 */
public class SelfPassFrame extends JFrame {
    public JPanel panel1;
    private JPasswordField newpasswordText;
    private JPasswordField ConfirmpasswordText;
    private JPasswordField oldpasswordText;
    private JButton 取消Button;
    private JButton 确定Button;
    private String oldpassword;
    private String newpassword;
    private String confirmpassword;

    public SelfPassFrame(AbstractUser user,JFrame jFrame) {

        确定Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInput(user)) {
                    MainGUI.showConfirmMessage(SelfPassFrame.this, "你确定要修改密码吗？", "提示!");
                    try {
                        if (user.changeSelfInfo(newpassword)) {
                            MainGUI.showMessage(SelfPassFrame.this, "密码修改成功！", "提示!");
                            jFrame.dispose();
                            JFrame frame = new JFrame("登录");
                            frame.setContentPane(new LoginFrame(frame).contentpanel);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.pack();
                            frame.setLocation(500,200);
                            frame.setResizable(false);
                            frame.setVisible(true);
                        } else {
                            MainGUI.showMessage(SelfPassFrame.this, "密码修改失败！", "提示!");
                            jFrame.dispose();
                        }
                    } catch (SQLException ex) {
//                        ex.printStackTrace();
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        取消Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
//                SelfPassFrame.this.dispose();
              jFrame.dispose();
//              SelfPassFrame.this.setVisible(false);
//              System.exit(0);
            }
        });
    }


//    public static void main(String[] args) {
//        JFrame frame = new JFrame("修改密码");
//        frame.setContentPane(new SelfPassFrame().panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setBounds(500,300,400,200);
//        frame.setResizable(false);
//        frame.setVisible(true);
//    }

    private boolean checkInput(AbstractUser user) {
        oldpassword = String.valueOf(oldpasswordText.getPassword()).trim();
        newpassword = String.valueOf(newpasswordText.getPassword()).trim();
        confirmpassword = String.valueOf(ConfirmpasswordText.getPassword()).trim();
        if (!user.getPassword().equals(oldpassword)) {
            MainGUI.showMessage(this, "原密码输入错误!", "提示！");
            newpasswordText.requestFocus();//获得焦点
            return false;
        }
        if (oldpassword.equals("") || oldpassword == null || newpassword.equals("") ||
                newpassword == null || confirmpassword.equals("") || confirmpassword == null) {
            MainGUI.showMessage(this, "输入的密码不能为空!", "提示！");
            newpasswordText.requestFocus();//获得焦点
            return false;
        }
        if (newpassword.equals(oldpassword)) {
            MainGUI.showMessage(SelfPassFrame.this, "新密码不能与原密码相同!", "提示！");
            oldpasswordText.requestFocus();
            return false;
        }
        if (!confirmpassword.equals(newpassword)) {
            MainGUI.showMessage(SelfPassFrame.this, "输入密码错误，请你再次确认你的密码!", "提示！");
            ConfirmpasswordText.requestFocus();
            return false;
        }
        return true;
    }

}
