package ec.project.startup;

import ec.project.jpa.UserServiceLocal;
import ec.project.jpa.PredictionService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.EJB;

@Singleton
@Startup
public class AppStartup {

    @EJB
    private UserServiceLocal userService;

    @EJB
    private PredictionService predictionService;

    @PostConstruct
    public void init() {
        // Initialize default admin user
        System.out.println("Checking for default admin user...");
        userService.createDefaultAdminIfNotExists();
        System.out.println("Default admin user setup complete.");

        // Initialize models in the database
        System.out.println("Initializing models from file directory...");
        predictionService.initializeModels();
        System.out.println("Model initialization complete.");
    }
}
