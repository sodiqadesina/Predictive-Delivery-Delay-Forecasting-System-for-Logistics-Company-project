package ec.project.jpa;

import ec.project.dao.UserDao;
import ec.project.model.User;
import ec.project.util.PasswordUtil;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(UserServiceLocal.class) // Ensure it's explicitly associated with the local interface
public class UserService implements UserServiceLocal {

    @EJB
    private UserDao userDao;

    @Override
    public void addUser(User user) {
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userDao.addUser(user);
    }

    @Override
    public User authenticateUser(String name, String password) {
        User user = userDao.getUserByName(name);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
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
