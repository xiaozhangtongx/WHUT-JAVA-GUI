package console;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Operator extends AbstractUser {
    private String names, passwords;

    Operator(String name, String password, String role) {
        super(name, password, role);
        names = name;
        this.passwords = password;
    }


    @Override
    /**
     * TODO 展示菜单
     * @return void
     * @throws
     * @author zhang
     * @version 1.0
     * 2020/11/25 10:08
     **/
    public void showMenu() {
        String tip_system1 = "档案录入员菜单";
        String tip_menu1 = "请选择菜单:";
        String tip_exit1 = "退出，谢谢你的使用";
        String infos = "\n****欢迎进入" + tip_system1 + "****\n" + "   1.上传档案\n" + "   2.下载档案\n" +
                "   3.档案列表\n" + "   4.修改密码\n" + "   5.退出\n" + "***************************";
        int k = 0;
        String input = null;
        do {
            System.out.println(infos);
            System.out.println(tip_menu1);
            input = Main.in.nextLine().trim();
            if (!(input).matches("1|2|3|4|5"))
                System.err.println("输入格式错误");
            else {
                int i = Integer.parseInt(input);
                switch (i) {
                    case 1:
                        upload();
                        break;
                    case 2:
                        download();
                        break;
                    case 3:
                        showlist();
                        break;
                    case 4:
                        chang();
                        break;
                    case 5:
                        System.out.println(tip_exit1);
                        k = 1;
                        break;
                    default:
                        System.out.println("输入错误，请按照规定输入！！！");
                }
            }

        } while (k == 0);

    }

    /**
     * TODO 上传档案
     *
     * @return void
     * @author zhang
     **/
    private void upload()//上传档案
    {
        System.out.println("请输入源文件名:(注意要输入文件的后缀)");
        String name = Main.in.nextLine();
        System.out.println("请输入源文件地址:");
        String address = Main.in.nextLine();
        String number;
        int k = 0;
        do {
            System.out.println("请输入档案号:");
            number = Main.in.nextLine();
            try {
                Doc doc = DataProcessing.searchDoc(number);
                //doc.getCreator();
                if (doc != null) {
                    System.out.println("该档案号已被使用，请你更换个试试！！！");
                } else
                    k = 1;
            } catch (SQLException e) {
                //e.printStackTrace();
                System.out.println(e.getMessage());
            }
        } while (k == 0);
//一开始认为编号可以不用输入，直接自动加一，但是在字符强制转换那里出错了
//        try {
//            Enumeration<Doc> e = DataProcessing.getAllDocs();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        Doc doc = null;
//        int number=(int)doc.getID();
//        number=number+1;
        System.out.println("请输入档案描述:");
        String s = Main.in.nextLine();
        String uploadpath = "C:\\Users\\zhang\\Desktop" +
                "\\java多线程实验\\textdata\\uploadfile\\";  //目标地址
        File file = new File(address + "\\" + name);
        File file1 = new File(uploadpath + name);
        if (file1.exists()) {
            System.out.println("Error:该文件已上传,无需重复上传！");

        } else {
            //System.out.println(file1.getName());
            if (file.exists()) {
                try {
                    BufferedInputStream infile = new BufferedInputStream(new FileInputStream(address + "\\" + name));
                    BufferedOutputStream outfile = new BufferedOutputStream(new FileOutputStream(uploadpath + name));
//                  FileInputStream infile = new FileInputStream(address + "\\" + name);
//                  FileOutputStream outfile = new FileOutputStream(uploadpath + name);
                    byte data[] = new byte[(int) file.length()];
                    int length;//创建长度来求文件的长度
                    while ((length = infile.read(data)) != -1) {
                        outfile.write(data, 0, length);
                    }
                    infile.close();
                    outfile.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                try {
                    DataProcessing.insertDoc(number, Operator.this.names, timestamp, s, name);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("文件上传成功！");
            } else
                System.out.println("Error:该文件不存在!");
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
        showlist();
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
    private void showlist()//档案列表
    {
        //System.out.println("档案列表如下");
        try {
            showFileList();
        } catch (SQLException e) {
            e.printStackTrace();
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
            if (password.equals(Operator.this.passwords))
                System.out.println("你输入的新密码与原密码相同，更改密码失败！");
            else {
                changeSelfInfo(password);
            }
        } catch (SQLException e) {
            System.out.println("Error:数据库访问错误\n" + e.getMessage());
        }
    }
}


