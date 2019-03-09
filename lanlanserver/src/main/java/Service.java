import com.google.gson.internal.bind.SqlDateTypeAdapter;
import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @auther ZhengTianle
 * @Date: 18-5-11
 */
public class Service {
    public String responseMsg = "无";
    private ResultSet resultSet;
    private ResultSet userResultSet;
    private ResultSet receiveResultSet;

    /**
     *
     * @param tel 手机号
     * @param passwords 密码
     * @return 是否登录成功
     */
    public CurrentUserInfo login(String tel, String passwords){

        //获取SQL查询语句
        String loginSql = "select * " +
                            "from Users " +
                            "where tel = '"+tel+
                            "' and passwords = '"+passwords+"'";
        String hasUsers = "select * " +
                            "from Users " +
                            "where tel = '"+tel+"'";

        CurrentUserInfo currentUserInfo = new CurrentUserInfo();

        //获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        //操作DB对象
        try{

            ResultSet resultSet = sql.executeQuery(loginSql);
            if(resultSet.next()){

                currentUserInfo.tel = resultSet.getString("tel");
                currentUserInfo.nickname = resultSet.getString("nickname");
                currentUserInfo.avatar = resultSet.getString("avatar");
                currentUserInfo.sex = resultSet.getString("sex");
                currentUserInfo.name = resultSet.getString("name");
                currentUserInfo.school = resultSet.getString("school");
                currentUserInfo.stu_id = resultSet.getString("stu_id");
                currentUserInfo.grade = resultSet.getString("grade");
                currentUserInfo.release_tasks = resultSet.getString("release_tasks");
                currentUserInfo.receive_tasks = resultSet.getString("receive_tasks");

                sql.closeDB();
                return currentUserInfo;
            }else{
                resultSet = sql.executeQuery(hasUsers);
                if(resultSet.next()){
                    responseMsg = "密码错误";
                }else{
                    responseMsg = "账号不存在";
                }

                sql.closeDB();
                return currentUserInfo;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        sql.closeDB();
        return currentUserInfo;
    }

    /**
     *
     * @param tel 手机号
     * @param passwords 密码
     * @param sex 性别
     * @param name 真实姓名
     * @param school 所在学校
     * @param stu_id 学号
     * @return
     */
    public boolean register(String tel,String passwords,String sex,
                            String name,String school,String stu_id){

        //获得SQL查询语句
        String registerSql = "insert into Users(tel,passwords,sex,name,school,stu_id) "+
                                "values('"+tel+"',"+
                                "'"+passwords+"',"+
                                "'"+sex+"',"+
                                "'"+name+"',"+
                                "'"+school+"',"+
                                "'"+stu_id+"')";
        String hasUsers = "select * " +
                            "from Users " +
                            "where tel = '"+tel+"'";

        //获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        try{

            ResultSet resultSet = sql.executeQuery(hasUsers);
            if(resultSet.next()){//账号已经存在
                responseMsg = "该手机号已被注册";
            }else{
                //注册新账号
                int affectRowNumber = sql.executeUpdate(registerSql);
                if(affectRowNumber != 0){
                    sql.closeDB();
                    return true;
                }else{
                    responseMsg = "数据库操作错误";
                    sql.closeDB();
                    return false;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        sql.closeDB();
        return false;

    }

    /**
     * 修改个人文字形式信息
     * @param nickname  昵称
     * @param sex       性别
     * @param name      姓名
     * @param school    学校
     * @param stu_id    学号(不修改，只用来定位查询)
     * @return
     */
    public boolean modifyUserInfo(String nickname,String sex,
                                  String name,String school,
                                  String stu_id,String avatar){

        String modifyInfoSql = "Update Users set nickname = '"+nickname+
                "',sex = '"+sex+
                "',name = '"+name+
                "',school = '"+school+
                "',avatar = '"+avatar+
                "' where stu_id = '"+stu_id+"'";

        //获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        int affectRowNumber = sql.executeUpdate(modifyInfoSql);
        if(affectRowNumber != 0){
            sql.closeDB();
            return true;
        }else{
            responseMsg = "数据库操作错误，修改失败";
            sql.closeDB();
            return false;
        }

    }

    public List<TaskInfo> allTaskInfo(){

        List<TaskInfo> list = new ArrayList<TaskInfo>();

        String receiveSql = "select * from Users,Receive_Express where Users.stu_id =Receive_Express.stu_id";
        String sendSql = "select * from Users,Send_Express where Users.stu_id =Send_Express.stu_id";


        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        try {
            /**
             * 代发
             */
            resultSet = sql.executeQuery(sendSql);
            while(resultSet.next()){
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.task_id = resultSet.getString("task_id");
                taskInfo.stu_id = resultSet.getString("stu_id");
                taskInfo.express_name = resultSet.getString("express_name");
                taskInfo.express_type = resultSet.getString("express_type");
                taskInfo.express_weight = resultSet.getString("express_weight");
                taskInfo.express_value = resultSet.getString("express_value");
                taskInfo.meeting_location = resultSet.getString("meeting_location");
                taskInfo.meeting_time = resultSet.getString("meeting_time");
                taskInfo.note = resultSet.getString("note");
                taskInfo.sender_name = resultSet.getString("sender_name");
                taskInfo.sender_tel = resultSet.getString("sender_tel");
                taskInfo.sender_location = resultSet.getString("sender_location");
                taskInfo.receiver_name = resultSet.getString("receiver_name");
                taskInfo.receiver_tel = resultSet.getString("receiver_tel");
                taskInfo.receiver_location = resultSet.getString("receiver_location");
                taskInfo.express_company = resultSet.getString("express_company");
                taskInfo.money = resultSet.getString("money");
                taskInfo.successful = resultSet.getString("successful");

                /**
                 * 发布者的信息
                 */
                taskInfo.tel = resultSet.getString("tel");
                taskInfo.nickname = resultSet.getString("nickname");
                taskInfo.sex = resultSet.getString("sex");
                taskInfo.name = resultSet.getString("name");
                taskInfo.school = resultSet.getString("school");
                taskInfo.grade = resultSet.getString("grade");

                taskInfo.type = "代发快递";

                /**
                 * 没有被接收的任务才添加到任务界面
                 */
                if(taskInfo.successful.equals("0"))
                    list.add(taskInfo);


            }


            /**
             * 代收
             */
            receiveResultSet = sql.executeQuery(receiveSql);
            while(receiveResultSet.next()){
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.task_id = receiveResultSet.getString("task_id");
                taskInfo.stu_id = receiveResultSet.getString("stu_id");
                taskInfo.express_name = receiveResultSet.getString("express_name");
                taskInfo.express_type = receiveResultSet.getString("express_type");
                taskInfo.express_weight = receiveResultSet.getString("express_weight");
                taskInfo.express_value = receiveResultSet.getString("express_value");
                taskInfo.meeting_location = receiveResultSet.getString("meeting_location");
                taskInfo.meeting_time = receiveResultSet.getString("meeting_time");
                taskInfo.note = receiveResultSet.getString("note");
                taskInfo.access_code = receiveResultSet.getString("access_code");
                taskInfo.sender_name = receiveResultSet.getString("sender_name");
                taskInfo.sender_tel = receiveResultSet.getString("sender_tel");
                taskInfo.sender_location = receiveResultSet.getString("sender_location");
                taskInfo.receiver_name = receiveResultSet.getString("receiver_name");
                taskInfo.receiver_tel = receiveResultSet.getString("receiver_tel");
                taskInfo.receiver_location = receiveResultSet.getString("receiver_location");
                taskInfo.express_company = receiveResultSet.getString("express_company");
                taskInfo.money = receiveResultSet.getString("money");
                taskInfo.successful = receiveResultSet.getString("successful");

                /**
                 * 发布者的信息
                 */
                taskInfo.nickname = receiveResultSet.getString("nickname");
                taskInfo.tel = receiveResultSet.getString("tel");
                taskInfo.sex = receiveResultSet.getString("sex");
                taskInfo.name = receiveResultSet.getString("name");
                taskInfo.school = receiveResultSet.getString("school");
                taskInfo.grade = receiveResultSet.getString("grade");

                taskInfo.type = "代收快递";

                /**
                 * 没有被接收的任务才添加到任务界面
                 */
                if(taskInfo.successful.equals("0"))
                    list.add(taskInfo);


            }



        }catch (SQLException e){
            e.printStackTrace();
        }

        sql.closeDB();
        return list;
    }

    /**
     * 增加快递任务
     * 通过type判断代发还是代收
     */
    public boolean addTask(TaskInfo taskInfo){

        //获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        if(taskInfo.type.equals("代发快递")){
            /**
             * 将信息插入代发快递表中
             */
            String addSendSql = "insert into Send_Express(stu_id,express_name,express_type,express_weight" +
                    ",express_value,meeting_location,meeting_time,note,sender_name,sender_tel,sender_location" +
                    ",receiver_name,receiver_tel,receiver_location,express_company,money) "+
                    "values('"
                    +taskInfo.stu_id+"',"+
                    "'"+taskInfo.express_name+"',"+
                    "'"+taskInfo.express_type+"',"+
                    "'"+taskInfo.express_weight+"',"+
                    "'"+taskInfo.express_value+"',"+
                    "'"+taskInfo.meeting_location+"',"+
                    "'"+taskInfo.meeting_time+"',"+
                    "'"+taskInfo.note+"',"+
                    "'"+taskInfo.sender_name+"',"+
                    "'"+taskInfo.sender_tel+"',"+
                    "'"+taskInfo.sender_location+"',"+
                    "'"+taskInfo.receiver_name+"',"+
                    "'"+taskInfo.receiver_tel+"',"+
                    "'"+taskInfo.receiver_location+"',"+
                    "'"+taskInfo.express_company+"',"+
                    "'"+taskInfo.money+"')";

            int affectRow = sql.executeUpdate(addSendSql);
            if(affectRow != 0){
                sql.closeDB();
                return true;
            }

        } else {
            /**
             * 将信息插入代收快递表中
             */
            String addReceiveSql = "insert into Receive_Express(stu_id,express_name,express_type,express_weight" +
                    ",express_value,meeting_location,meeting_time,note,access_code,sender_name,sender_tel,sender_location" +
                    ",receiver_name,receiver_tel,receiver_location,express_company,money) "+
                    "values('"
                    +taskInfo.stu_id+"',"+
                    "'"+taskInfo.express_name+"',"+
                    "'"+taskInfo.express_type+"',"+
                    "'"+taskInfo.express_weight+"',"+
                    "'"+taskInfo.express_value+"',"+
                    "'"+taskInfo.meeting_location+"',"+
                    "'"+taskInfo.meeting_time+"',"+
                    "'"+taskInfo.note+"',"+
                    "'"+taskInfo.access_code+"',"+
                    "'"+taskInfo.sender_name+"',"+
                    "'"+taskInfo.sender_tel+"',"+
                    "'"+taskInfo.sender_location+"',"+
                    "'"+taskInfo.receiver_name+"',"+
                    "'"+taskInfo.receiver_tel+"',"+
                    "'"+taskInfo.receiver_location+"',"+
                    "'"+taskInfo.express_company+"',"+
                    "'"+taskInfo.money+"')";

            int affectRow = sql.executeUpdate(addReceiveSql);
            if(affectRow != 0){
                sql.closeDB();
                return true;
            }

        }

        responseMsg = "数据库操作错误";
        sql.closeDB();
        return false;
    }

    public boolean receiveTask(String task_id,String sender_id,String sender_tel,
                               String receiver_id,String receiver_tel,
                               String praise,String send_time,String receive_time,String finish_time,
                               String express_number,String task_type){

        String insertSql = "insert into Task_Manage values('"+
                task_id+"',"+
                "'"+sender_id+"',"+
                "'"+sender_tel+"',"+
                "'"+receiver_id+"',"+
                "'"+receiver_tel+"',"+
                "'"+praise+"',"+
                "'"+send_time+"',"+
                "'"+receive_time+"',"+
                "'"+finish_time+"',"+
                "'"+express_number+"',"+
                "'"+task_type+"')";

        String updateSendSql = "update Send_Express set successful = 1 where task_id = "+task_id;
        String updateReceiveSql = "update Receive_Express set successful = 1 where task_id = "+task_id;

        //获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        int affectRow = sql.executeUpdate(insertSql);
        if(task_type.equals("代发快递")){
            sql.executeUpdate(updateSendSql);
        } else {
            sql.executeUpdate(updateReceiveSql);
        }

        if(affectRow != 0){
            sql.closeDB();
            return true;
        }

        responseMsg = "数据库操作错误";
        sql.closeDB();
        return false;

    }

    public List<TaskInfo> myReleaseTasks(String stu_id){

        List<TaskInfo> list = new ArrayList<TaskInfo>();
        String myReceiveSql = "select * from Users,Receive_Express where Users.stu_id =Receive_Express.stu_id and Users.stu_id = '"+stu_id+"'";
        String mySendSql = "select * from Users,Send_Express where Users.stu_id =Send_Express.stu_id and Users.stu_id = '"+stu_id+"'";


        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        try {
            /**
             * 代发
             */
            resultSet = sql.executeQuery(mySendSql);
            while(resultSet.next()){
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.task_id = resultSet.getString("task_id");
                taskInfo.stu_id = resultSet.getString("stu_id");
                taskInfo.express_name = resultSet.getString("express_name");
                taskInfo.express_type = resultSet.getString("express_type");
                taskInfo.express_weight = resultSet.getString("express_weight");
                taskInfo.express_value = resultSet.getString("express_value");
                taskInfo.meeting_location = resultSet.getString("meeting_location");
                taskInfo.meeting_time = resultSet.getString("meeting_time");
                taskInfo.note = resultSet.getString("note");
                taskInfo.sender_name = resultSet.getString("sender_name");
                taskInfo.sender_tel = resultSet.getString("sender_tel");
                taskInfo.sender_location = resultSet.getString("sender_location");
                taskInfo.receiver_name = resultSet.getString("receiver_name");
                taskInfo.receiver_tel = resultSet.getString("receiver_tel");
                taskInfo.receiver_location = resultSet.getString("receiver_location");
                taskInfo.express_company = resultSet.getString("express_company");
                taskInfo.money = resultSet.getString("money");
                taskInfo.successful = resultSet.getString("successful");

                /**
                 * 发布者的信息
                 */
                taskInfo.tel = resultSet.getString("tel");
                taskInfo.nickname = resultSet.getString("nickname");
                taskInfo.sex = resultSet.getString("sex");
                taskInfo.name = resultSet.getString("name");
                taskInfo.school = resultSet.getString("school");
                taskInfo.grade = resultSet.getString("grade");

                taskInfo.type = "代发快递";


                list.add(taskInfo);


            }


            /**
             * 代收
             */
            receiveResultSet = sql.executeQuery(myReceiveSql);
            while(receiveResultSet.next()){
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.task_id = receiveResultSet.getString("task_id");
                taskInfo.stu_id = receiveResultSet.getString("stu_id");
                taskInfo.express_name = receiveResultSet.getString("express_name");
                taskInfo.express_type = receiveResultSet.getString("express_type");
                taskInfo.express_weight = receiveResultSet.getString("express_weight");
                taskInfo.express_value = receiveResultSet.getString("express_value");
                taskInfo.meeting_location = receiveResultSet.getString("meeting_location");
                taskInfo.meeting_time = receiveResultSet.getString("meeting_time");
                taskInfo.note = receiveResultSet.getString("note");
                taskInfo.access_code = receiveResultSet.getString("access_code");
                taskInfo.sender_name = receiveResultSet.getString("sender_name");
                taskInfo.sender_tel = receiveResultSet.getString("sender_tel");
                taskInfo.sender_location = receiveResultSet.getString("sender_location");
                taskInfo.receiver_name = receiveResultSet.getString("receiver_name");
                taskInfo.receiver_tel = receiveResultSet.getString("receiver_tel");
                taskInfo.receiver_location = receiveResultSet.getString("receiver_location");
                taskInfo.express_company = receiveResultSet.getString("express_company");
                taskInfo.money = receiveResultSet.getString("money");
                taskInfo.successful = receiveResultSet.getString("successful");

                /**
                 * 发布者的信息
                 */
                taskInfo.nickname = receiveResultSet.getString("nickname");
                taskInfo.tel = receiveResultSet.getString("tel");
                taskInfo.sex = receiveResultSet.getString("sex");
                taskInfo.name = receiveResultSet.getString("name");
                taskInfo.school = receiveResultSet.getString("school");
                taskInfo.grade = receiveResultSet.getString("grade");

                taskInfo.type = "代收快递";

                list.add(taskInfo);


            }



        }catch (SQLException e){
            e.printStackTrace();
        }

        sql.closeDB();
        return list;

    }


    public List<TaskInfo> myAcceptTasks(String stu_id){

        List<TaskInfo> list = new ArrayList<TaskInfo>();
        String myAcceptSendSql = "select * from Users,Send_Express where Users.stu_id = Send_Express.stu_id and task_id in " +
                "(select task_id from Task_Manage where receiver_id = '"+stu_id+"' and task_type = '代发快递')";
        String myAcceptReceiveSql = "select * from Users,Receive_Express where Users.stu_id = Receive_Express.stu_id and task_id in " +
                "(select task_id from Task_Manage where receiver_id = '"+stu_id+"' and task_type = '代收快递')";


        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        try {
            /**
             * 我接受的代发任务
             */
            resultSet = sql.executeQuery(myAcceptSendSql);
            while(resultSet.next()){
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.task_id = resultSet.getString("task_id");
                taskInfo.stu_id = resultSet.getString("stu_id");
                taskInfo.express_name = resultSet.getString("express_name");
                taskInfo.express_type = resultSet.getString("express_type");
                taskInfo.express_weight = resultSet.getString("express_weight");
                taskInfo.express_value = resultSet.getString("express_value");
                taskInfo.meeting_location = resultSet.getString("meeting_location");
                taskInfo.meeting_time = resultSet.getString("meeting_time");
                taskInfo.note = resultSet.getString("note");
                taskInfo.sender_name = resultSet.getString("sender_name");
                taskInfo.sender_tel = resultSet.getString("sender_tel");
                taskInfo.sender_location = resultSet.getString("sender_location");
                taskInfo.receiver_name = resultSet.getString("receiver_name");
                taskInfo.receiver_tel = resultSet.getString("receiver_tel");
                taskInfo.receiver_location = resultSet.getString("receiver_location");
                taskInfo.express_company = resultSet.getString("express_company");
                taskInfo.successful = resultSet.getString("successful");
                taskInfo.money = resultSet.getString("money");

                /**
                 * 发布者的信息
                 */
                taskInfo.tel = resultSet.getString("tel");
                taskInfo.nickname = resultSet.getString("nickname");
                taskInfo.sex = resultSet.getString("sex");
                taskInfo.name = resultSet.getString("name");
                taskInfo.school = resultSet.getString("school");
                taskInfo.grade = resultSet.getString("grade");

                taskInfo.type = "代发快递";


                list.add(taskInfo);

            }

            /**
             * 我接受的代收任务
             */
            receiveResultSet = sql.executeQuery(myAcceptReceiveSql);
            while(receiveResultSet.next()) {
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.stu_id = receiveResultSet.getString("stu_id");
                taskInfo.task_id = receiveResultSet.getString("task_id");
                taskInfo.express_name = receiveResultSet.getString("express_name");
                taskInfo.express_type = receiveResultSet.getString("express_type");
                taskInfo.express_weight = receiveResultSet.getString("express_weight");
                taskInfo.express_value = receiveResultSet.getString("express_value");
                taskInfo.meeting_location = receiveResultSet.getString("meeting_location");
                taskInfo.meeting_time = receiveResultSet.getString("meeting_time");
                taskInfo.note = receiveResultSet.getString("note");
                taskInfo.access_code = receiveResultSet.getString("access_code");
                taskInfo.sender_name = receiveResultSet.getString("sender_name");
                taskInfo.sender_tel = receiveResultSet.getString("sender_tel");
                taskInfo.sender_location = receiveResultSet.getString("sender_location");
                taskInfo.receiver_name = receiveResultSet.getString("receiver_name");
                taskInfo.receiver_tel = receiveResultSet.getString("receiver_tel");
                taskInfo.receiver_location = receiveResultSet.getString("receiver_location");
                taskInfo.express_company = receiveResultSet.getString("express_company");
                taskInfo.money = receiveResultSet.getString("money");
                taskInfo.successful = receiveResultSet.getString("successful");
                /**
                 * 发布者的信息
                 */
                taskInfo.nickname = receiveResultSet.getString("nickname");
                taskInfo.tel = receiveResultSet.getString("tel");
                taskInfo.sex = receiveResultSet.getString("sex");
                taskInfo.name = receiveResultSet.getString("name");
                taskInfo.school = receiveResultSet.getString("school");
                taskInfo.grade = receiveResultSet.getString("grade");

                taskInfo.type = "代收快递";

                list.add(taskInfo);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        sql.closeDB();
        return list;

    }

    public boolean deleteThisTask(String task_id,String type){

        String deleteSendSql = "delete from Send_Express where task_id = '"+task_id+"'";
        String deleteReceiveSql = "delete from Receive_Express where task_id = '"+task_id+"'";
        String searchSendSql = "select successful from Send_Express where task_id = '"+task_id+"'";
        String searchReceiveSql = "select successful from Receive_Express where task_id = '"+task_id+"'";

        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        if(type.equals("代发快递")){
            resultSet = sql.executeQuery(searchSendSql);
            try {
                if(resultSet.next()){

                    if(resultSet.getString("successful").equals("1")){
                        responseMsg = "该任务已被接受，不可删除";
                    }else{
                        int affectNumber = sql.executeUpdate(deleteSendSql);
                        if(affectNumber!=0){
                            sql.closeDB();
                            return true;
                        }else{
                            sql.closeDB();
                            return false;
                        }

                    }
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            receiveResultSet = sql.executeQuery(searchReceiveSql);
            try {
                if(receiveResultSet.next()){

                    if(receiveResultSet.getString("successful").equals("1")){
                        responseMsg = "该任务已被接受，不可删除";
                    }else{
                        int affectNumber = sql.executeUpdate(deleteReceiveSql);
                        if(affectNumber!=0){
                            sql.closeDB();
                            return true;
                        }else{
                            sql.closeDB();
                            return false;
                        }

                    }
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        sql.closeDB();
        return false;
    }



}
