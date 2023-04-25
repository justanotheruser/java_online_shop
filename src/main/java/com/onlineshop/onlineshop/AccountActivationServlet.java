package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.dao.UserDao;
import com.onlineshop.onlineshop.dao.UserDaoImpl;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/activate", name = "accountActivation")
public class AccountActivationServlet extends HttpServlet {
    UserDao userDao = UserDaoImpl.getInstance();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userDao.findByUsername(request.getParameter("username"));
        if (user != null && user.getPasswordHash().equals(request.getParameter("passwordHash"))) {
            user.setIsActive(true);
            userDao.update(user);
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/activation_success.jsp");
            dispatcher.forward(request, response);
            return;
        }
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/activation_failure.jsp");
        dispatcher.forward(request, response);
    }
}
