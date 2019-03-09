import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @auther ZhengTianle
 * @Date: 18-6-6
 */

public class AddTaskServlet extends HttpServlet {
    private String msg = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        TaskInfo taskInfo = new TaskInfo();
        /*taskInfo.task_id = request.getParameter("task_id");*/
        taskInfo.stu_id = request.getParameter("stu_id");
        taskInfo.express_name = request.getParameter("express_name");
        taskInfo.express_type = request.getParameter("express_type");
        taskInfo.express_weight = request.getParameter("express_weight");
        taskInfo.express_value = request.getParameter("express_value");
        taskInfo.meeting_location = request.getParameter("meeting_location");
        taskInfo.meeting_time = request.getParameter("meeting_time");
        taskInfo.note = request.getParameter("note");
        taskInfo.sender_name = request.getParameter("sender_name");
        taskInfo.sender_tel = request.getParameter("sender_tel");
        taskInfo.sender_location = request.getParameter("sender_location");
        taskInfo.receiver_name = request.getParameter("receiver_name");
        taskInfo.receiver_tel = request.getParameter("receiver_tel");
        taskInfo.receiver_location = request.getParameter("receiver_location");
        taskInfo.express_company = request.getParameter("express_company");
        taskInfo.money = request.getParameter("money");
        taskInfo.type = request.getParameter("type");
        taskInfo.access_code = request.getParameter("access_code");

        taskInfo.tel = request.getParameter("tel");
        taskInfo.nickname = request.getParameter("nickname");
        taskInfo.sex = request.getParameter("sex");
        taskInfo.name = request.getParameter("name");
        taskInfo.school = request.getParameter("school");
        taskInfo.grade = request.getParameter("grade");



        Service service = new Service();
        boolean pass = service.addTask(taskInfo);
        if(pass){
            msg = "success";
        }else{
            msg = "fail";
        }

        //返回信息到客户端
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        Map<String,String> map = new HashMap<>();
        map.put("tag",msg);
        map.put("info",service.responseMsg);
        String message = gson.toJson(map);
        out.println(message);

        out.flush();
        out.close();

    }
}
