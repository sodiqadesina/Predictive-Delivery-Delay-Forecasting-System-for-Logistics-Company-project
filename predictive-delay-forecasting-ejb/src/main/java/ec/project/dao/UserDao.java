package ec.project.dao;

import java.util.List;
import ec.project.model.User;

public interface UserDao {
    void addUser(User user);
    User getUser(String name, String password);
    User getUserByName(String name);
    List<User> getAllUsers();
}
