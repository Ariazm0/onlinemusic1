package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ariazm
 * Date: 2020-07-29
 * Time: 16:12
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        String username = req.getParameter("username");
        String passwod = req.getParameter("password");
        User loginUser = new User();
        loginUser.setUserName(username);
        loginUser.setPassword(passwod);

        Map<String,Object> return_map = new HashMap<>();
        try {
            UserDao dao = new UserDao();
            User user = dao.login(loginUser);
            if (user != null) {
                req.getSession().setAttribute("user", user);
                System.out.println("登录成功");
                return_map.put("msg", true);
            } else {
                System.out.println("登录失败");
                return_map.put("msg", false);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),return_map);
    }
}