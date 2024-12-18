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

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {

    @EJB
    private UserServiceLocal userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entering UserLoginServlet...");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Authenticate user
            User user = userService.authenticateUser(username, password);

            if (user != null) {
                // Save user to session and redirect to landing page
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/user/landing.jsp");
            } else {
                // Invalid login credentials
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("user/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Log exception and display generic error message
            e.printStackTrace();
            request.setAttribute("error", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("user/login.jsp").forward(request, response);
        }
    }
}
