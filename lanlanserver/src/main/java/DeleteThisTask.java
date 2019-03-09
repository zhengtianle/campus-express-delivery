import com.google.gson.Gson;

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
 * @Date: 18-6-13
 */
public class DeleteThisTask extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String task_id = request.getParameter("task_id");
        String type = request.getParameter("type");

        Service service = new Service();
        Boolean pass = service.deleteThisTask(task_id,type);

        PrintWriter out = response.getWriter();
        Map<String,String> map = new HashMap<String,String>();
        if(pass){
            map.put("tag","success");
        }else{
            map.put("tag","fail");
        }
        map.put("info",service.responseMsg);

        Gson gson = new Gson();
        String message = gson.toJson(map);
        out.println(message);
        out.flush();
        out.close();

    }
}
