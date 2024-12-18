package ec.project.jpa;

import ec.project.dao.UserDao;
import ec.project.model.User;
import ec.project.util.PasswordUtil;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserService {

    @EJB
    private UserDao userDao; // Injected as an EJB (stateful bean)

    public void addUser(User user) {
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userDao.addUser(user);
    }

    public User authenticateUser(String name, String password) {
        User user = userDao.getUserByName(name);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void createDefaultAdminIfNotExists() {
        User existingAdmin = getUserByName("admin");
        if (existingAdmin == null) {
            User adminUser = new User();
            adminUser.setName("admin");
            adminUser.setPassword("admin123");
            adminUser.setRole("admin");
            addUser(adminUser);
        }
    }
}
