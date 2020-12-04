package console;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * TODO Main方法显示主菜单
 *
 * @author zhang
 **/
public class Main {

    public static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        String tip_system = "档案系统";
        String tip_menu = "请选择菜单:";
        String tip_exit = "系统退出，谢谢你的使用";
        String infos = "\n****欢迎进入" + tip_system + "****\n" + "        1.登录\n" + "     " +
                "   2.退出\n" + "**********************";
        String name;
        String password;
        String input = null;
        do {
            System.out.println(infos);
            System.out.println(tip_menu);
            input = Main.in.nextLine().trim();
            if (!(input).matches("1|2"))
                System.err.println("输入格式错误");
            else {
                int i = Integer.parseInt(input);
                switch (i) {
                    case 1:
                        try {
                            System.out.println("请输入你的用户名:");
                            name = in.nextLine();
                            System.out.println("请输入你的密码:");
                            password = in.nextLine();
                            AbstractUser user = DataProcessing.searchUser(name, password);
                            if (user == null) {
                                System.out.println("用户名或密码输入错误\n");
                            } else
                                user.showMenu();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage() + "\nError:数据库错误!该用户不在数据库内\n");
                        }
                        break;
                    case 2:
                        System.out.println(tip_exit);
                        in.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("输入错误，请按照规定输入！！！\n");
                }
            }
        } while (true);
    }
}
