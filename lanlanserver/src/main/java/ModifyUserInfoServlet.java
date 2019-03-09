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
 * 修改个人信息
 *
 * @auther ZhengTianle
 * @Date: 18-6-6
 */
public class ModifyUserInfoServlet extends HttpServlet {
    private String msg = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String nickname = request.getParameter("nickname");
        String sex = request.getParameter("sex");
        String name = request.getParameter("name");
        String school = request.getParameter("school");
        String stu_id = request.getParameter("stu_id");
        String avatar = request.getParameter("avatar");

        Service service = new Service();

        boolean pass = service.modifyUserInfo(nickname,sex,name,school,stu_id,avatar);
        if(pass){
            msg = "success";
        }else{
            msg = "fail";
        }

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
