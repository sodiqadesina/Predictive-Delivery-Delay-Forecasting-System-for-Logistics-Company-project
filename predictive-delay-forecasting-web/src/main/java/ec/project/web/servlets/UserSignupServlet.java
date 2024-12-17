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

@WebServlet("/user/signup")
public class UserSignupServlet extends HttpServlet {

    @Inject
    private UserService userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User newUser = new User();
        newUser.setName(username);
        newUser.setPassword(password);
        newUser.setRole("user");

        userService.addUser(newUser);
        response.sendRedirect(request.getContextPath() + "/user/login.jsp");
    }
}
