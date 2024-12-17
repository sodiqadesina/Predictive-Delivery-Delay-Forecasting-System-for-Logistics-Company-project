package ec.project.web.servlets;

import ec.project.UserService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UserLoginServlet extends HttpServlet {
    @Inject
    private UserService userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        if (userService.validateUser(name, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", name);
            response.sendRedirect("/user/landing.jsp");
        } else {
            response.sendRedirect("/user/login.jsp?error=invalid");
        }
    }
}
