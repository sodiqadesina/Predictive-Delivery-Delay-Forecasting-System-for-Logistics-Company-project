package ec.project.web.servlets;

import ec.project.model.User;
import ec.project.web.dto.UserDTO;
import ec.project.service.UserService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AdminLoginServlet extends HttpServlet {
    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User admin = userService.getUser(username, password);
        if (admin != null && admin.getRole().equalsIgnoreCase("admin")) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            response.sendRedirect(request.getContextPath() + "/admin/landing.jsp");
        } else {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        }
    }
}
