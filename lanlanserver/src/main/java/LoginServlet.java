import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @auther ZhengTianle
 * @Date: 18-5-11
 */
public class LoginServlet extends HttpServlet {
    public String msg ="";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String tel = request.getParameter("tel");
        String passwords = request.getParameter("passwords");
        //System.out.println("tel:"+tel+"\n"+"passwords:"+passwords);

        //新建服务对象
        Service service = new Service();

        //验证处理
        CurrentUserInfo list = service.login(tel,passwords);
        if(service.responseMsg.equals("无")){
            msg = "success";
            //request.getSession().setAttribute("tel",tel);
        }else{
            msg = "fail";
        }

        //返回信息到客户端
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        String currentUserInfo = gson.toJson(list);

        Map<String,String> map = new HashMap<String,String>();
        map.put("tag",msg);
        map.put("info",service.responseMsg);
        map.put("currentUserInfo",currentUserInfo);

        String message = gson.toJson(map);
        out.println(message);
       /* out.println(msg);
        out.println(service.responseMsg);*///
        out.flush();
        out.close();

    }


}
