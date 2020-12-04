package frame;

import console.AbstractUser;
import console.DataProcessing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JComboBox;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author zhang
 * @version 1.0
 * 2020/12/2 21:51
 * @Todo:
 */
public class UserFrame extends Component {
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
    private JTable table;
    private  String username;
    private String password;
    private  String role;

    public static void main(String[] args) {
        JFrame frame = new JFrame("UserFrame");
        frame.setContentPane(new UserFrame().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
        frame.setBounds(500, 300, 400, 250);
        frame.setVisible(true);
    }


    public UserFrame() {
        tabbedPane.setTitleAt(0, "新增用户");
        tabbedPane.setTitleAt(1, "修改用户");
        tabbedPane.setTitleAt(2, "删除用户");
        showUserInfoToTable();
        AddUser();
        ChangeUser();

    }

    public void showUserInfoToTable() {
        try {
            String[] colName = {"用户名", "密 码", "角 色"};
            String[][] tableValue = new String[6][3];
            Enumeration<AbstractUser> e;
            e = DataProcessing.listUser();
            AbstractUser user;
            int a = 0;//设置行数
            while (e.hasMoreElements()) {
                user = e.nextElement();
                tableValue[a][0] = user.getName();
                tableValue[a][1] = user.getPassword();
                tableValue[a][2] = user.getRole();
                a++;
            }
            DefaultTableModel tableMode1 = new DefaultTableModel(tableValue, colName);
            table.setModel((TableModel) tableMode1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void AddUser() {
        Role.setEnabled(true);
        Role.setModel(new DefaultComboBoxModel(new String[]{"","Administrator", "Browser", "Operator"}));
//        Role.setBounds(197, 156, 181, 24);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AachenInput()) {
                    MainGUI.showConfirmMessage(UserFrame.this, "你确定要增加吗？", "提示!");
                    try {
                        if (DataProcessing.insertUser(username,password,role)) {
                            MainGUI.showMessage(UserFrame.this, "用户增加成功！", "提示!");
                        }
                        else
                        {
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
            
            }
        });
    }


    private void ChangeUser() {
        String[] strings = new String[100];
        Enumeration<AbstractUser> e = null;
        try {
            e = DataProcessing.listUser();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        AbstractUser user = null;
        int a = 0;//设置行数
        while (e.hasMoreElements()) {
            user = e.nextElement();
            strings[a] = user.getName();
            a++;
            System.out.println(strings[a]);
        }
//        String[][] tableValue = new String[6][3];
//        Enumeration<AbstractUser> e = null;
//        try {
//            e = DataProcessing.listUser();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        AbstractUser user;
//        int a = 0;//设置行数
//        while (e.hasMoreElements()) {
//            user = e.nextElement();
//            tableValue[a][0] = user.getName();
//            tableValue[a][1] = user.getPassword();
//            tableValue[a][2] = user.getRole();
//            a++;
//            System.out.println(tableValue[a][0]);
//            System.out.println(tableValue[a][1]);
//            System.out.println(tableValue[a][2]);
//        }
//        String[] strings = new String[100];
//        int i=0;
//        while (i<=10)
//        {
//            strings[i]=tableValue[i][0];
//        }
//
        comboBox1.setEnabled(true);
        comboBox1.setModel(new DefaultComboBoxModel(strings));
        comboBox2.setEnabled(true);
        comboBox2.setModel(new DefaultComboBoxModel(new String[]{"Administrator", "Browser", "Operator"}));
        chanInput();
    }
    private boolean AachenInput() {
        username = this.UserName.getText();
        password = new String(this.passwordField.getPassword());
        role = (String) this.Role.getSelectedItem();
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
    private  boolean chanInput()
    {
        username=(String) this.comboBox1.getSelectedItem();
        password=new String(this.passwordField.getPassword());
        role=(String) this.comboBox2.getSelectedItem();
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
        return  true;
    }



//    private void SetPane(int value) {
//        if (value == 0) {
//            tabbedPane.setSelectedComponent(addUser);
//        } else if (value == 1) {
//            tabbedPane.setSelectedComponent(ChangeUser);
//        } else if (value == 2) {
//            tabbedPane.setSelectedComponent(DelUser);
//        }
//    }
}
