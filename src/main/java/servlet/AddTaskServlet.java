package servlet;

import manager.TaskManager;
import manager.UserManager;
import model.Task;
import model.TaskStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = "/addTask")
public class AddTaskServlet extends HttpServlet {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TaskManager taskManager = new TaskManager();
    private UserManager userManager = new UserManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String deadline = req.getParameter("deadline");
        String status = req.getParameter("status");
        int userId = Integer.parseInt(req.getParameter("user_id"));
        try {
            taskManager.add(Task.builder()
                    .name(name)
                    .description(description)
                    .deadline(simpleDateFormat.parse(deadline))
                    .status(TaskStatus.valueOf(status))
                    .userId(userId)
                    .user(userManager.getUserById(userId))
                    .build());
            resp.sendRedirect("/managerHome");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
