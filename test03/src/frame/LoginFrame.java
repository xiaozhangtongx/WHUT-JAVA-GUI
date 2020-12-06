package frame;

import console.AbstractUser;
import console.DataProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * @author zhang
 * @version 1.0
 * 2020/12/2 9:21
 * @Todo: 登录实现
 */

public class LoginFrame extends JFrame {
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("登录");
//        frame.setContentPane(new LoginFrame().contentpanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setLocation(100, 200);
//        frame.setResizable(false);    //使窗口不能放大
//        frame.setVisible(true);
//    }

    public JPanel contentpanel;
    private JTextField UserNametext;
    private JPasswordField psdUser;
    private JButton 确定Button;
    private JButton 取消Button;
    public String username;  //用户名字符串
    public String password;//密码字符串

    public LoginFrame(JFrame jFrame) {
        //登录按钮
        确定Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInput()) {
                    AbstractUser user = null;
                    try {
                        user = DataProcessing.searchUser(username, password);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    if (user == null) {
                        MainGUI.showMessage(LoginFrame.this, "用户名或密码输入错误!", "提示！");
                    } else {
                        jFrame.dispose();
//                       setVisible(false);
                        showMainFrame(user, jFrame);

                    }
                }
//                checkInput();
            }
        });
        //取消按钮
        取消Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public boolean checkInput() {
        username = UserNametext.getText().trim();
        password = String.valueOf(psdUser.getPassword()).trim();
        if (username.equals("") || username == null) {
            MainGUI.showMessage(this, "用户名不能为空!", "提示！");
            UserNametext.requestFocus();//获得焦点
            return false;
        }
        if (password.equals("") || password == null) {
            MainGUI.showMessage(this, "密码不能为空!", "提示！");
            psdUser.requestFocus();
            return false;
        }
        return true;
    }

    //显示主界面
    public static void showMainFrame(AbstractUser user, JFrame jFrame) {
        MainFrame frame = new MainFrame(user, jFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 200, 500, 400);
//        frame.pack();
        frame.setVisible(true);
    }

}
