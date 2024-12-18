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

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entering UserLoginServlet...");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        User user = userService.authenticateUser(username, password);
        if (user != null) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("user/landing.jsp");
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("user/login.jsp").forward(request, response);
        }
    }

}
