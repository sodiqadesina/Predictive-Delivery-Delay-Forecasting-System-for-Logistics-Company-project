package ec.project.web.servlets;

import ec.project.jpa.UserService;
import ec.project.model.User;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserSignupServlet")
public class UserSignupServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = "USER"; // Default role

        // Create a new User object
        User newUser = new User();
        newUser.setName(username);  // Corrected: Use setName() for the name field
        newUser.setPassword(password); // Plain password; hashing happens in service
        newUser.setRole(role);

        // Add user to the database
        try {
            userService.addUser(newUser);
            response.sendRedirect("user/login.jsp"); // Redirect to login page
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred during signup. Username might already exist.");
            request.getRequestDispatcher("user/signup.jsp").forward(request, response);
        }
    }
}
