package console;

import java.io.*;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * TODO 抽象用户类，为各用户子类提供模板
 *
 * @author zhang
 * @version 1.0
 * 2020/11/25 10:08
 **/
public abstract class AbstractUser {
    static final double EXCEPTION_PROBABILITY = 0.9;
    private String name;
    private String password;
    private String role;
    String uploadpath = "C:\\Users\\zhang\\Desktop" +
            "\\java多线程实验\\textdata\\uploadfile\\";

    String downloadpath = "C:\\Users\\zhang\\Desktop" +
            "\\java多线程实验\\textdata\\downloadfile\\";

    AbstractUser(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    /**
     * TODO 修改用户自身信息
     *
     * @param password 口令
     * @return boolean 修改是否成功
     * @throws SQLException
     * @author zhang
     * @version 1.0
     * 2020/11/25 10:08
     **/

    public boolean changeSelfInfo(String password) throws SQLException {
        if (DataProcessing.updateUser(name, password, role)) {
            this.password = password;
            System.out.println("修改成功");
            return true;
        } else {
            return false;
        }
    }

    /**
     * TODO 下载档案文件
     *
     * @param id 文件id
     * @return boolean 下载是否成功
     * @throws IOException
     * @author zhang
     * @version 1.0
     * 2020/11/25 10:08
     **/
    public boolean downloadFile(String id) throws IOException {
        try {
            Doc doc = DataProcessing.searchDoc(id);
            if (doc == null) {
                return false;
            }
            File file = new File(downloadpath + doc.getFilename());
            if (file.exists()) {
                //System.out.println(file.getName());
                System.out.println("Error:该文件已下载！");
                return false;
            }
            //System.out.println(file.getName());
            //缓冲流
            byte[] bytes = new byte[1024]; //自己定义的一个缓冲区
            BufferedInputStream infile = new BufferedInputStream(new FileInputStream(uploadpath + doc.getFilename()));
            BufferedOutputStream outfile = new BufferedOutputStream(new FileOutputStream(downloadpath + doc.getFilename()));
            int length;
            while ((length = infile.read(bytes)) != -1) {
                //将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此缓冲的输出流。
                //public void write(byte[] b,int off,int len);
                outfile.write(bytes, 0, length);
            }
            infile.close();
            outfile.close();
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println(e.getMessage());
        }
        double ranValue = Math.random();
        if (ranValue > EXCEPTION_PROBABILITY) {
            throw new IOException("Error in accessing file");
        }
        System.out.println("下载文件... ...");
        return true;

    }

    /**
     * TODO 展示文件列表
     *
     * @return void
     * @throws SQLException
     * @author zhang
     * @version 1.0
     * 2020/11/25 10:08
     **/
    public void showFileList() throws SQLException {
        double ranValue = Math.random();
        if (ranValue > EXCEPTION_PROBABILITY) {
            throw new SQLException("Error in accessing file DB");
        }
        System.out.println("     *******************档案列表如下******************");
        Enumeration<Doc> e = DataProcessing.listDoc();
        Doc doc;
        while (e.hasMoreElements()) {
            doc = e.nextElement();
            System.out.println("编号：" + doc.getId() + "\t创建者:" + doc.getCreator() + "\t文件描述:"
                    + doc.getDescription() + "\t\t文件名：" + doc.getFilename() + "\t\t上传时间：" + doc.getTimestamp());
        }
        System.out.println("     ************************************************");
    }


    /**
     * TODO 展示菜单，需子类加以覆盖
     *
     * @return void
     * @throws
     * @author zhang
     * @version 1.0
     * 2020/11/25 10:08
     **/
    public abstract void showMenu();

    /**
     * TODO 退出系统
     *
     * @param
     * @return void
     * @throws
     */
    public void exitSystem() {
        System.out.println("系统退出, 谢谢使用 ! ");
        System.exit(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
