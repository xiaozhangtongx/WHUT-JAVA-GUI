package console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

public class Administrator extends AbstractUser {
    private String passwords;

    public Administrator(String name, String password, String role) {
        super(name, password, role);
        this.passwords = password;
    }

    //String name, password, role;


    /**
     * TODO 输出系统管理员菜单
     *
     * @return void
     * @author zhang
     **/
    public void showMenu() {
        String tip_system1 = "系统管理员菜单";
        String tip_menu1 = "请选择菜单:";
        String tip_exit1 = "退出，谢谢你的使用";
        String infos = "\n****欢迎进入" + tip_system1 + "****\n" + "   1.新增用户\n" + "   2.删除用户\n" +
                "   3.修改用户\n" + "   4.用户列表\n" + "   5.下载档案\n" + "   6.档案列表\n"
                + "   7.修改个人密码\n" + "   8.退出\n" + "***************************";
        String input = null;
        int k = 0;
        do {
            System.out.println(infos);
            System.out.println(tip_menu1);
            input = Main.in.nextLine().trim();
            if (!(input).matches("1|2|3|4|5|6|7|8"))
                System.err.println("输入格式错误");
            else {
                int i = Integer.parseInt(input);
                switch (i) {
                    case 1:
                        adduser();
                        break;
                    case 2:
                        deluser();
                        break;
                    case 3:
                        changuserinfo();
                        break;
                    case 4:
                        System.out.println("***************用户列表如下**************");
                        showlist();
                        System.out.println("****************************************");
                        break;
                    case 5:
                        download();
                        break;
                    case 6:
                        showlist2();
                        break;
                    case 7:
                        chang();
                        break;
                    case 8:
                        System.out.println(tip_exit1);
                        k = 1;
                        break;

                    default:
                        System.out.println("输入错误，请按照规定输入！！！\n");
                }
            }

        } while (k == 0);

    }

    /**
     * TODO 新增用户
     *
     * @return void
     * @author zhang
     **/
    private void adduser() //新增用户
    {
        try {
            System.out.println("请输入新用户的姓名：");
            String name = Main.in.nextLine();
            System.out.println("请输入新用户的密码：");
            String password = Main.in.nextLine();
            //System.out.println("请输入新用户的类型(输入administrator、operator或browser)：");
            String tip_menu1 = "请选择用户类型:";
            String infos = "***************************\n" + "   1.administrator\n" + "   2.operator\n" +
                    "   3.browser\n" + "***************************";
            String input = null;
            System.out.println(infos);
            System.out.println(tip_menu1);
            String role = null;
            input = Main.in.nextLine().trim();
            if (!(input).matches("1|2|3|4"))
                System.err.println("输入格式错误");
            else {
                int i = Integer.parseInt(input);
                switch (i) {
                    case 1:
                        role = "administrator";
                        break;
                    case 2:
                        role = "operator";
                        break;
                    case 3:
                        role = "browser";
                        break;

                    default:
                }
            }
            if (DataProcessing.insertUser(name, password, role)) {
                System.out.println("新用户添加成功！");
            } else
                System.out.println("新用户添加失败！");

        } catch (SQLException e) {
            System.out.println("Error:数据库访问错误\n" + e.getMessage());
        }
    }

    /**
     * TODO 删除用户
     *
     * @return void
     * @author zhang
     **/
    private void deluser()//删除用户
    {
        System.out.println("请输入待删除用户的姓名：");
        try {
            String name = Main.in.nextLine();

            if (DataProcessing.deleteUser(name))
                System.out.println("该用户删除成功！");
            else
                System.out.println("该用户删除失败！");
        } catch (SQLException e) {
            System.out.println("Error:数据库访问错误\n" + e.getMessage());
        }
    }

    /**
     * TODO 修改用户角色
     *
     * @return void
     * @author zhang
     **/
    private void changuserinfo()//修改用户
    {

        try {
            System.out.println("***************用户列表如下**************");
            showlist();
            System.out.println("****************************************");
            System.out.println("请输入要修改用户的姓名：");
            String name = Main.in.nextLine();
            System.out.println("请输入要修改用户的密码：");
            String password = Main.in.nextLine();
            String tip_menu1 = "请选择用户类型:";
            String tip_exit1 = "退出，谢谢你的使用";
            String infos = "***************************\n" + "   1.administrator\n" + "   2.operator\n" +
                    "   3.browser\n" + "***************************";
            String input = null;
            System.out.println(infos);
            String role = null;
            System.out.println(tip_menu1);
            input = Main.in.nextLine().trim();
            if (!(input).matches("1|2|3|4"))
                System.err.println("输入格式错误");
            else {
                int i = Integer.parseInt(input);
                switch (i) {
                    case 1:
                        role = "administrator";
                        break;
                    case 2:
                        role = "operator";
                        break;
                    case 3:
                        role = "browser";
                        break;
                    default:
                }
                if (DataProcessing.updateUser(name, password, role))
                    System.out.println("用户修改成功！");
                else
                    System.out.println("用户修改失败！");

            }
        } catch (SQLException e) {
            System.out.println("Error:数据库访问错误\n" + e.getMessage());
        }
    }

    /**
     * TODO 输出用户列表
     *
     * @return void
     * @author zhang
     **/
    private void showlist()//用户列表
    {
//        User user= (Administrator) DataProcessing.getAllUser();
//       while(user!=null)
//       {
//           System.out.println("姓名："+user.getName()+"密码："+user.getPassword()+"角色："+user.getRole());
//       }   自己写的有bug

//csdn上查到了一个解决办法  通过 Enumeration 的对象（比如对象名为：e）调用此类中的 nextElement() 获得 User 的对象
        try {
            Enumeration<AbstractUser> e = null;

            e = DataProcessing.listUser();

            AbstractUser user;
            while (e.hasMoreElements()) {
                user = e.nextElement();
                System.out.println("姓名：" + user.getName() + "\t密码：" + user.getPassword() + "\t角色：" + user.getRole());
            }
        } catch (SQLException ex) {
            System.out.println("Error:数据库访问错误\n" + ex.getMessage());
        }

    }

    /**
     * TODO 下载文件
     *
     * @return void
     * @author zhang
     **/
    private void download()//下载文件
    {
        System.out.println("现在所能下载的文件有：");
        showlist2();
        System.out.println("请输入你要下载文件的编号:");
        try {
            String ID = Main.in.nextLine();
            if (downloadFile(ID)) {
                System.out.println("下载成功！");
            } else
                System.out.println("下载失败！");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error:数据库访问错误\n" + e.getMessage());
        }
    }

    /**
     * TODO 输出档案列表
     *
     * @return void
     * @author zhang
     **/
    private void showlist2()//档案列表
    {
        //System.out.println("档案列表如下");
        try {
            showFileList();
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Error:数据库访问错误\n" + e.getMessage());
        }
    }

    /**
     * TODO 修改用户密码
     *
     * @return void
     * @author zhang
     **/
    private void chang() {
        System.out.println("请输入你的新密码:");
        try {
            String password = Main.in.nextLine();
            if (password.equals(Administrator.this.passwords))
                System.out.println("你输入的新密码与原密码相同，更改密码失败！");
            else {
                changeSelfInfo(password);
            }
        } catch (SQLException e) {
            System.out.println("Error:数据库访问错误\n" + e.getMessage());
        }
    }
}
