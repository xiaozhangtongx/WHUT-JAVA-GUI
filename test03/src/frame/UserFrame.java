package frame;

import console.AbstractUser;
import console.DataProcessing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JComboBox;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author zhang
 * @version 1.0
 * 2020/12/2 21:51
 * @Todo:用户各项功能实现
 */
public class UserFrame extends JFrame {
    public JPanel panel1;
    private JTabbedPane tabbedPane;
    private JPanel addUser;
    private JTextField UserName;
    private JPasswordField passwordField;
    private JComboBox Role;
    private JButton button1;
    private JButton 取消Button1;
    private JPanel ChangeUser;
    private JPanel DelUser;
    private JComboBox comboBox1;
    private JPasswordField passwordField1;
    private JComboBox comboBox2;
    private JButton button3;
    private JButton 取消Button;
    private JButton confirm;
    private JButton quite;
    private JTable table1;
    private String username;
    private String password;
    private String role;

    public static void main(String[] args) {
        JFrame frame = new JFrame("UserFrame");
        AbstractUser user = null;
        frame.setContentPane(new UserFrame(frame, user, 0).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
        frame.setBounds(500, 300, 400, 250);
        frame.setVisible(true);
    }


    public UserFrame(JFrame jFrame, AbstractUser user, int a) {
        tabbedPane.setTitleAt(0, "新增用户");
        tabbedPane.setTitleAt(1, "修改用户");
        tabbedPane.setTitleAt(2, "删除用户");
        showUserInfoToTable();
        AddUser(jFrame);
        ChangeUser(jFrame, user);
        SetPane(a);
        Deluser(jFrame, user);
    }

    //删除用户
    private void Deluser(JFrame jFrame, AbstractUser user) {
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (delInput(user)) {
                    MainGUI.showConfirmMessage(UserFrame.this,
                            "你确定要删除该用户吗？", "提示!");
                    try {
                        if (DataProcessing.deleteUser(username)) {
//                           System.out.println("该用户删除成功！");
                            MainGUI.showConfirmMessage(UserFrame.this,
                                    "该用户删除成功！", "提示!");
                            showUserInfoToTable();
//                           System.out.println(username);
//                           System.out.println(user.getName());
                            jFrame.dispose();
                        } else {
//                             System.out.println("该用户删除失败！");
                            MainGUI.showConfirmMessage(UserFrame.this,
                                    "该用户删除失败！", "提示!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        quite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
    }

    //构造表格
    public void showUserInfoToTable() {
        String[] colName = {"用户名", "密 码", "角 色"}; //表头
        String[][] tableValue = new String[6][3];
        Enumeration<AbstractUser> e;
        try {
            e = DataProcessing.listUser();
            int a = 0;//设置行数
            while (e.hasMoreElements()) {
                AbstractUser user = e.nextElement();
                tableValue[a][0] = user.getName();
                tableValue[a][1] = user.getPassword();
                tableValue[a][2] = user.getRole();
                a++;
            }
            //把表格的内容居中
            DefaultTableCellRenderer r = new DefaultTableCellRenderer();
            r.setHorizontalAlignment(JLabel.CENTER);
            table1.setDefaultRenderer(Object.class, r);
            //表格
            DefaultTableModel tableMode1 = new DefaultTableModel(tableValue, colName);
            table1.setModel(tableMode1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //新增用户
    private void AddUser(JFrame jFrame) {
        Role.setEnabled(true);
        Role.setModel(new DefaultComboBoxModel(new String[]{"", "administrator", "browser", "operator"}));
//        Role.setBounds(197, 156, 181, 24);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AachenInput()) {
                    MainGUI.showConfirmMessage(UserFrame.this, "你确定要增加吗？", "提示!");
                    try {
                        if (DataProcessing.insertUser(username, password, role)) {
                            showUserInfoToTable();
                            MainGUI.showMessage(UserFrame.this, "用户增加成功！", "提示!");
                            jFrame.dispose();
                        } else {
                            MainGUI.showMessage(UserFrame.this, "用户增加失败！", "提示!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        取消Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
    }

    //修改用户
    private void ChangeUser(JFrame jFrame, AbstractUser user) {
        try {
            String[] strings = new String[100];
//            AbstractUser user;
            Enumeration<AbstractUser> e;
            e = DataProcessing.listUser();
            int a = 0;//设置行数
            while (e.hasMoreElements()) {
                user = e.nextElement();
                strings[a] = user.getName();
                a++;
//            System.out.println(user.getName());
//            System.out.println(strings[a]);
            }
            comboBox1.setEnabled(true);
            comboBox1.setModel(new DefaultComboBoxModel(strings));
            comboBox2.setEnabled(true);
            comboBox2.setModel(new DefaultComboBoxModel(new String[]{"administrator", "browser", "operator"}));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        AbstractUser finalUser = user;
        //变化菜单里面的内容
        comboBox1.addItemListener(new ItemListener() {   //监听器
            @Override
            public void itemStateChanged(ItemEvent e) {
                Dchange(finalUser);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chanInput()) {

                    MainGUI.showConfirmMessage(UserFrame.this, "你确定要保存该用户吗？", "提示!");
                    try {
                        if (DataProcessing.updateUser(username, password, role)) {
                            showUserInfoToTable();
                            MainGUI.showMessage(UserFrame.this, "用户修改成功！", "提示!");
                            jFrame.dispose();
                        } else {
                            MainGUI.showMessage(UserFrame.this, "用户修改失败！", "提示!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        取消Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
    }

    //实现联动变化
    private void Dchange(AbstractUser user) {
        username = String.valueOf(this.comboBox1.getSelectedItem());
        try {
            Enumeration<AbstractUser> e;
            e = DataProcessing.listUser();
            int a = 0;//设置行数
            while (e.hasMoreElements()) {
                user = e.nextElement();
                if (user.getName().equals(username)) {
                    passwordField1.setText(user.getPassword());
//                    comboBox2.addItem(user.getRole());
                    comboBox2.setSelectedItem(user.getRole());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //新增用户输入判断
    private boolean AachenInput() {
        username = this.UserName.getText().trim();
        password = String.valueOf(this.passwordField.getPassword()).trim();
        role = String.valueOf(this.Role.getSelectedItem());
        if (username.equals("") || username == null) {
            MainGUI.showMessage(UserFrame.this, "输入的用户名不能为空!", "提示！");
            UserName.requestFocus();//获得焦点
            return false;
        }
        if (password.equals("") || password == null) {
            MainGUI.showMessage(UserFrame.this, "输入的密码不能为空!", "提示！");
            passwordField.requestFocus();//获得焦点
            return false;
        }
        if (role.equals("") || role == null) {
            MainGUI.showMessage(UserFrame.this, "请选择身份!", "提示！");
            Role.requestFocus();//获得焦点
            return false;
        }
        return true;
    }

    //修改用户输入判断
    private boolean chanInput() {
        username = String.valueOf(this.comboBox1.getSelectedItem());
        password = String.valueOf(this.passwordField1.getPassword());
        role = String.valueOf(this.comboBox2.getSelectedItem());
        if (username.equals("") || username == null) {
            MainGUI.showMessage(UserFrame.this, "输入的用户名不能为空!", "提示！");
            UserName.requestFocus();//获得焦点
            return false;
        }
        if (password.equals("") || password == null) {
            MainGUI.showMessage(UserFrame.this, "输入的密码不能为空!", "提示！");
            passwordField.requestFocus();//获得焦点
            return false;
        }
        if (role.equals("") || role == null) {
            MainGUI.showMessage(UserFrame.this, "请选择身份!", "提示！");
            Role.requestFocus();//获得焦点
            return false;
        }
        return true;
    }

    //删除用户输入判断
    private boolean delInput(AbstractUser user) {
        int choose = table1.getSelectedRow();
        if (choose == -1) { // 未选择用户的情况
            MainGUI.showMessage(UserFrame.this, "未选择用户！", "提示！");
            return false;
        } else {
            // 获取所选行的用户名
            username = String.valueOf(table1.getValueAt(choose, 0));
            // 若选择空行
            if (username.equals("") || username == null) {
                MainGUI.showMessage(UserFrame.this, "该用户不存在！", "提示！");
                return false;
            }
            // 选择自身用户的情况
            if (username.equals(user.getName())) {
                MainGUI.showMessage(UserFrame.this, "不能删除自身用户！", "提示！");
                return false;
            }
            return true;
        }
    }

    //选择标签
    private void SetPane(int value) {
        if (value == 0) {
            tabbedPane.setSelectedComponent(addUser);
        } else if (value == 1) {
            tabbedPane.setSelectedComponent(ChangeUser);
        } else if (value == 2) {
            tabbedPane.setSelectedComponent(DelUser);
        }
    }
}
