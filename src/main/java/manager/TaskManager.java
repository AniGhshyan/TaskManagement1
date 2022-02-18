package manager;

import db.DBConnectionProvider;
import model.Task;
import model.TaskStatus;
import model.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private UserManager userManager = new UserManager();

    public void add(Task task) {
        String sql = "INSERT into task(name,description,deadline,status,user_id) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            if (task.getDeadline() != null) {
                preparedStatement.setString(3, simpleDateFormat.format(task.getDeadline()));
            } else {
                preparedStatement.setString(3, null);
            }
            preparedStatement.setString(4, task.getStatus().name());
            preparedStatement.setInt(5, task.getUserId());

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                task.setId(id);
            }
            preparedStatement.executeUpdate();
            System.out.println("Task was added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public Task getById(int id) {
//        String sql = "SELECT * FROM task WHERE id = " + id;
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//            if (resultSet.next()) {
//                return getTaskFromResulSet(resultSet);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt(1));
                task.setName(resultSet.getString(2));
                task.setDescription(resultSet.getString(3));
                task.setDeadline(simpleDateFormat.parse(resultSet.getString(4)));
                task.setStatus(TaskStatus.valueOf(resultSet.getString(5)));
                task.setUserId(resultSet.getInt(6));
                task.setUser(userManager.getUserById(task.getUserId()));
                tasks.add(task);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getAllTasksByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task where user_id =" + userId;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(getTaskFromResulSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private Task getTaskFromResulSet(ResultSet resultSet) {
        try {
            return Task.builder()
                    .id(resultSet.getInt(1))
                    .name(resultSet.getString(2))
                    .description(resultSet.getString(3))
                    .deadline(resultSet.getString(4) == null ? null : simpleDateFormat.parse(resultSet.getString(4)))
                    .status(TaskStatus.valueOf(resultSet.getString(5)))
                    .user(userManager.getUserById(resultSet.getInt(6)))
                    .build();
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTaskStatus(int taskId, String newStatus) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE task SET status = ? where id = ?");
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean delete(int id) {
        String sql = "DELETE FROM task WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
