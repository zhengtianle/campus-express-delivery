import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @auther ZhengTianle
 * @Date: 18-5-11
 */
public class DBManager {

    //数据库连接常量
    public static final String DRIVER ="com.mysql.jdbc.Driver";
    public static  String USER = "root";
    public static  String PASSWORD = "201600800543";
    public static final String URL ="jdbc:mysql://localhost:3306/lanlanApp";

    //
    private static DBManager per = null;
    private Connection connection = null;
    private Statement statement = null;

    //单态模式->懒汉模式
    private DBManager(){

    }

    public static DBManager createInstance(){
        if(per == null){
            per = new DBManager();
            per.initDB();
        }
        return per;
    }

    //加载驱动
    public void initDB(){
        try{
            Class.forName(DRIVER);
        }catch(Exception e){
            System.err.println("可恶！驱动没加载成功!");
            e.printStackTrace();
        }
    }

    //连接数据库，获取句柄+对象
    public void connectDB(){
        System.out.println("Connecting to database……");
        try{
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.createStatement();
        }catch (SQLException e){
            System.err.println("可恶！没连上!");
            e.printStackTrace();
        }
        System.out.println("Connect to database successful.");
    }

    // 关闭数据库 关闭对象，释放句柄
    public void closeDB() {
        System.out.println("Close connection to database..");
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("可恶！没关上!");
            e.printStackTrace();
        }
        System.out.println("Close connection successful.");
    }

    //查询
    public ResultSet executeQuery(String sql) {
        ResultSet resultSet = null;
        try{
            resultSet = statement.executeQuery(sql);
        }catch (SQLException e){
            System.err.println("可恶！没查到!");
            e.printStackTrace();
        }

        return resultSet;
    }

    //增加 删除 修改
    public int executeUpdate(String sql){
        int affectedRowNumber = 0;
        try{
            affectedRowNumber = statement.executeUpdate(sql);
        }catch(SQLException e){
            System.err.println("可恶！没改成功！");
            e.printStackTrace();
        }

        return affectedRowNumber;
    }


}
