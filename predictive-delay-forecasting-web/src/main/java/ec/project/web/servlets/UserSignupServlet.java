package ec.project.web.servlets;

import ec.project.jpa.UserServiceLocal;
import ec.project.model.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserSignupServlet")
public class UserSignupServlet extends HttpServlet {

    @EJB
    private UserServiceLocal userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entering UserSignupServlet...");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate input
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Username and password are required.");
            request.getRequestDispatcher("user/signup.jsp").forward(request, response);
            return;
        }

        try {
            // Create new User object
            User newUser = new User();
            newUser.setName(username); // Set username
            newUser.setPassword(password); // Raw password; hashing happens in service
            newUser.setRole("USER"); // Default role

            // Add user to the database
            userService.addUser(newUser);

            // Redirect to login page after successful signup
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
        } catch (Exception e) {
            // Log exception and display error
            e.printStackTrace();
            request.setAttribute("error", "An error occurred during signup. Username might already exist.");
            request.getRequestDispatcher("user/signup.jsp").forward(request, response);
        }
    }
}
