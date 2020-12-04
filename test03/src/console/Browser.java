package console;

import java.io.IOException;
import java.sql.SQLException;

public class Browser extends AbstractUser {
    private String passwords;

    Browser(String name, String password, String role) {
        super(name, password, role);
        this.passwords = password;
    }

    /**
     * TODO 输出档案浏览员菜单
     *
     * @return void
     * @author zhang
     **/
    public void showMenu() {
        String input = null;
        String tip_system1 = "档案浏览员菜单";
        String tip_menu1 = "请选择菜单:";
        String tip_exit1 = "退出，谢谢你的使用";
        String infos = "\n****欢迎进入" + tip_system1 + "****\n" + "   1.下载档案\n" +
                "   2.档案列表\n" + "   3.修改密码\n" + "   4.退出\n" + "***************************";
        int k = 0;
        do {
            System.out.println(infos);
            System.out.println(tip_menu1);
            input = Main.in.nextLine().trim();
            if (!(input).matches("1|2|3|4"))
                System.err.println("输入格式错误");
            else {
                int i = Integer.parseInt(input);
                switch (i) {
                    case 1:
                        dowloada();
                        break;
                    case 2:
                        showlist();
                        break;
                    case 3:
                        chang();
                        break;
                    case 4:
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
     * TODO 下载文件
     *
     * @return void
     * @author zhang
     **/
    private void dowloada() {
        System.out.println("现在所能下载的文件有：");
        showlist();
        System.out.println("请输入你要下载文件的编号:");
        try {
            String ID = Main.in.nextLine();
            if (downloadFile(ID)) {
                System.out.println("下载成功！");
            } else
                System.out.println("下载失败！");
        } catch (IOException e) {
            System.out.println("Error:文件访问错误\n" + e.getMessage());
        }

    }

    /**
     * TODO 输出档案列表
     *
     * @return void
     * @author zhang
     **/
    private void showlist() {
        //System.out.println("档案列表如下");
        try {
            showFileList();
        } catch (SQLException e) {
            System.out.println("Error:文件访问错误\n" + e.getMessage());
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
            if (password.equals(Browser.this.passwords))
                System.out.println("你输入的新密码与原密码相同，更改密码失败！");
            else {

                changeSelfInfo(password);
            }
        } catch (SQLException e) {
            System.out.println("Error:数据库访问错误\n" + e.getMessage());
        }
    }
}


