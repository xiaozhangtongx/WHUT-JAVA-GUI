package frame;

import console.AbstractUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zhang
 * @version 1.0
 * 2020/12/2 9:39
 * @Todo: kjhdk$
 */
public class MainFrame extends JFrame {
    private AbstractUser loginUser;  //登录用户
    JMenu userMenu;
    JMenuItem addUserMenuItem;
    JMenuItem delUserMenuItem;

    JMenu docMenu;
    JMenuItem updataDocMenuItem;
    JMenuItem uploadMenuItem;
    JMenuItem downloadItem;

    JMenu selfMenu;
    JMenuItem updatpassMenuItem;
    JMenuItem exitMenuItem;

    public MainFrame(AbstractUser loginUser,JFrame jFrame) throws HeadlessException {
        super("菜单");
        this.loginUser = loginUser;//保存用户信息
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        userMenu = new JMenu("用户管理");
        menuBar.add(userMenu);
        addUserMenuItem = new JMenuItem("新增用户");
        userMenu.add(addUserMenuItem);
        addUserMenuItem.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
                addUser();
            }
        });
        delUserMenuItem = new JMenuItem("修改用户");
        userMenu.add(delUserMenuItem);
        updataDocMenuItem = new JMenuItem("删除用户");
        userMenu.add(updataDocMenuItem);

        docMenu = new JMenu("档案管理");
        menuBar.add(docMenu);
        uploadMenuItem = new JMenuItem("上传档案");
        docMenu.add(uploadMenuItem);
        downloadItem = new JMenuItem("下载档案");
        docMenu.add(downloadItem);
        selfMenu = new JMenu("个人信息管理");
        updatpassMenuItem = new JMenuItem("修改个人密码");
        updatpassMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.dispose();
                change(loginUser);
            }
        });  //用了提示修改
        selfMenu.add(updatpassMenuItem);
        exitMenuItem = new JMenuItem("退出");
//        exitMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.dispose();
//            }
//        });
        exitMenuItem.addActionListener(e -> MainFrame.this.dispose());
        selfMenu.add(exitMenuItem);
        menuBar.add(selfMenu);
        init();//初始化菜单项
    }



//    public static void main(String[] args) {
////        MainFrame frame = new MainFrame();
////        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.setBounds(200, 200, 500, 400);
//////        frame.pack();
////        frame.setVisible(true);
//    }

    private void init() {
        String role = loginUser.getRole();
        if (role.equalsIgnoreCase("Browser")) {
            userMenu.setEnabled(false);
            uploadMenuItem.setEnabled(false);
            setTitle("档案浏览员菜单");
        } else if (role.equalsIgnoreCase("Operator")) {
            userMenu.setEnabled(false);
            setTitle("档案录入员菜单");
        } else {
            uploadMenuItem.setEnabled(false);
            setTitle("系统管理员菜单");
        }

    }
    private  void change(AbstractUser user)
    {
        JFrame frame = new JFrame("修改密码");
        frame.setContentPane(new SelfPassFrame(user,frame).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500,300,400,200);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    private  void  addUser()
    {
        JFrame frame = new JFrame("UserFrame");
        frame.setContentPane(new UserFrame().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      frame.pack();
        frame.setBounds(500, 300, 400, 250);
        frame.setVisible(true);

    }

}
