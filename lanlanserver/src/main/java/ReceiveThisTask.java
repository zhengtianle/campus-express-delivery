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
 * @Date: 18-6-12
 */
public class ReceiveThisTask extends HttpServlet {
    private String msg;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String task_id = request.getParameter("task_id");
        String sender_id = request.getParameter("sender_id");
        String sender_tel = request.getParameter("sender_tel");
        String receiver_id = request.getParameter("receiver_id");
        String receiver_tel = request.getParameter("receiver_tel");
        String praise = request.getParameter("praise");
        String send_time = request.getParameter("send_time");
        String receive_time = request.getParameter("receive_time");
        String finish_time = request.getParameter("finish_time");
        String express_number = request.getParameter("express_number");
        String task_type = request.getParameter("task_type");

        //新建服务对象
        Service service = new Service();

        //验证处理
        boolean pass = service.receiveTask(task_id,sender_id,sender_tel,
                receiver_id,receiver_tel,
                praise,send_time,receive_time,finish_time,
                express_number,task_type);
        if(pass){
            msg = "success";
            //request.getSession().setAttribute("tel",tel);
        }else{
            msg = "fail";
        }

        //返回信息到客户端
        PrintWriter out = response.getWriter();
        Map<String,String> map = new HashMap<String,String>();
        map.put("tag",msg);
        map.put("info",service.responseMsg);

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        String message = gson.toJson(map);
        out.println(message);
        out.flush();
        out.close();

    }
}
