package ec.project.startup;

import ec.project.jpa.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.EJB;

@Singleton
@Startup
public class AppStartup {

    @EJB
    private UserService userService;

    @PostConstruct
    public void init() {
        System.out.println("Checking for default admin user...");
        userService.createDefaultAdminIfNotExists();
        System.out.println("Default admin user setup complete.");
    }
}
