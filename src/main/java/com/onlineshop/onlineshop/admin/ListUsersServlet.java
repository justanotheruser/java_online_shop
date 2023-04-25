package com.onlineshop.onlineshop.admin;

import com.onlineshop.onlineshop.dao.ItemDao;
import com.onlineshop.onlineshop.dao.ItemDaoImpl;
import com.onlineshop.onlineshop.dao.UserDao;
import com.onlineshop.onlineshop.dao.UserDaoImpl;
import com.onlineshop.onlineshop.services.ItemService;
import com.onlineshop.onlineshop.services.ItemServiceImpl;
import entity.Item;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "adminListUsers", value = "/admin/listUsers")
public class ListUsersServlet extends HttpServlet {
    private final UserDao userDao = UserDaoImpl.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            doGet_ListAll(request, response);
        }
        else if (action.equals("block")) {
            int userId = Integer.parseInt(request.getParameter("id"));
            User user = userDao.findById(userId);
            user.setIsBlocked(true);
            userDao.update(user);
            doGet_ListAll(request, response);
        }
        else if (action.equals("unblock")) {
            int userId = Integer.parseInt(request.getParameter("id"));
            User user = userDao.findById(userId);
            user.setIsBlocked(false);
            userDao.update(user);
            doGet_ListAll(request, response);
        }
    }

    private void doGet_ListAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<User> users = userDao.findAll();
        request.setAttribute("users", users);
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/listUsers.jsp");
        dispatcher.forward(request, response);
    }
}
