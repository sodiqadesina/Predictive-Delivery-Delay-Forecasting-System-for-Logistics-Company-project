package ec.project.web.servlets;

import ec.project.UserService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UserSignupServlet extends HttpServlet {
    @Inject
    private UserService userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        userService.addUser(name, password);
        response.sendRedirect("/user/login.jsp?success=signup");
    }
}
