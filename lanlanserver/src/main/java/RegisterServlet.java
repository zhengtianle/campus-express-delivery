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
 * @Date: 18-5-11
 */
public class RegisterServlet extends HttpServlet {

    public String msg = "";

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
        //tel = new String(tel.getBytes("ISO-8859-1"),"UTF-8");
        String passwords = request.getParameter("passwords");
        String sex = request.getParameter("sex");
        String name = request.getParameter("name");
        String school = request.getParameter("school");
        String stu_id = request.getParameter("stu_id");
        //System.out.println("tel:"+tel+"\n"+"passwords:"+passwords);

        //新建服务对象
        Service service = new Service();

        //验证处理
        boolean pass = service.register(tel,passwords,sex,name,school,stu_id);
        if(pass){
            msg = "success";
            //request.getSession().setAttribute("tel",tel);
        }else{
            msg = "fail";
        }

        //返回信息到客户端
        PrintWriter out = response.getWriter();
        Map<String,String> map = new  HashMap<String,String>();
        map.put("tag",msg);
        map.put("info",service.responseMsg);

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        String message = gson.toJson(map);
        out.println(message);

        /*out.println(msg);
        out.println(service.responseMsg);*/
        out.flush();
        out.close();

    }

}
