package ec.project.jpa;

import ec.project.dao.UserDao;
import ec.project.model.User;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UserService {

    @Inject
    private UserDao userDao;

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User authenticateUser(String name, String password) {
        return userDao.getUser(name, password);
    }

    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
