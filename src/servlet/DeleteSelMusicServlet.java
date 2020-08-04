package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import entity.Music;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ariazm
 * Date: 2020-07-31
 * Time: 15:09
 */
@WebServlet("/deleteSelMusicServlet")
public class DeleteSelMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        String[] ids = req.getParameterValues("id[]");
        System.out.println("ids:" + Arrays.toString(ids));
        int sum = 0;
        Map<String,Object> return_map = new HashMap<>();
        for (int i = 0; i < ids.length; i++) {
            String idStr = ids[i];
            int id = Integer.parseInt(idStr);
            MusicDao dao = new MusicDao();
            Music music = dao.findMusicById(id);
            int num = dao.deleteMusicById(id);
            if (num == 1) {
                File file = new File("D://javacode//onlinemusic//web//" + music.getUrl()+ ".mp3");
                System.out.println("文件是否存在：" + file.exists());
                System.out.println("file: " + file);
                if (file.delete()) {
                    sum = sum + num;
                }else {
                    System.out.println("文件名： " + file.getName());
                }
            }
        }
        System.out.println(sum);
        if (sum == ids.length) {
            return_map.put("msg", true);
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
            return_map.put("msg", false);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(),return_map);
    }
}