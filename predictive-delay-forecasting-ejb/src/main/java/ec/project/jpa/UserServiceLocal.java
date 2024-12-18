package ec.project.jpa;

import ec.project.model.User;

import java.util.List;

public interface UserServiceLocal {
    void addUser(User user);

    User authenticateUser(String name, String password);

    User getUserByName(String name);

    List<User> getAllUsers();

    void createDefaultAdminIfNotExists();
}
