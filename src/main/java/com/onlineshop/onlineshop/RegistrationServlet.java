package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.services.UserService;
import com.onlineshop.onlineshop.services.UserServiceException;
import com.onlineshop.onlineshop.services.UserServiceImpl;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.onlineshop.onlineshop.config.SecurityConfig;

import java.io.IOException;

@WebServlet(name = "registration", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!request.getParameter("password").equals(request.getParameter("passwordConfirmation"))) {
            request.setAttribute("errorMessage", "Введённые пароли не совпадают");
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp");
            dispatcher.forward(request, response);
            return;
        }

        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("email"));
        user.setFullName(request.getParameter("fullName"));
        user.setPhoneNumber(request.getParameter("phone"));
        user.setCompanyName(request.getParameter("companyName"));
        user.setRole(SecurityConfig.ROLE_CUSTOMER);
        try {
            userService.save(user);
        } catch (UserServiceException e) {
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp");
            dispatcher.forward(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
