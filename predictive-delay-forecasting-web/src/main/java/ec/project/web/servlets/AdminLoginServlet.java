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

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entering AdminLoginServlet...");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        User user = userService.authenticateUser(username, password);
        System.out.println("User authenticated: " + (user != null));

        if (user != null) {
            System.out.println("User role: " + user.getRole());
            if ("admin".equals(user.getRole())) {
                request.getSession().setAttribute("admin", user);
                System.out.println("Redirecting to admin landing page...");
                response.sendRedirect(request.getContextPath() + "/admin/landing.jsp");
            } else {
                System.out.println("User is not an admin.");
                request.setAttribute("error", "You do not have admin privileges.");
                request.getRequestDispatcher("admin/login.jsp").forward(request, response);
            }
        } else {
            System.out.println("Invalid username or password.");
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("admin/login.jsp").forward(request, response);
        }
    }
}
