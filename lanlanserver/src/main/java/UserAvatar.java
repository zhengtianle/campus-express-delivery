import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @auther ZhengTianle
 * @Date: 18-6-13
 */
public class UserAvatar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*try {
            ServerSocket server = new ServerSocket(30000);
            Socket socket = server.accept();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream("/home/mrzheng/warehouse/1.jpg");
            int size = fis.available();

            System.out.println("size = "+size);
            byte[] data = new byte[size];
            fis.read(data);
            dos.writeInt(size);
            dos.write(data);

            dos.flush();
            dos.close();
            fis.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
