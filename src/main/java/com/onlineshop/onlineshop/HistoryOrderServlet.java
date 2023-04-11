package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.config.SecurityConfig;
import com.onlineshop.onlineshop.dao.OrderDao;
import com.onlineshop.onlineshop.dao.OrderDaoImpl;
import com.onlineshop.onlineshop.dao.UserDao;
import com.onlineshop.onlineshop.dao.UserDaoImpl;
import com.onlineshop.onlineshop.services.OrderService;
import com.onlineshop.onlineshop.services.OrderServiceImpl;
import com.onlineshop.onlineshop.utils.AppUtils;
import entity.Order;
import entity.OrderItem;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "historyOrder", value = "/history/order")
public class HistoryOrderServlet extends HttpServlet {
    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final OrderDao orderDao = OrderDaoImpl.getInstance();
    private final UserDao userDao = UserDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = AppUtils.getLoggedInUser(session);
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        int orderId = -1;
        try {
            orderId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/history");
        }
        Order order = orderDao.findById(orderId);
        if (!loggedInUser.getRole().equals(SecurityConfig.ROLE_ADMIN) && order.getUserId() != loggedInUser.getId()) {
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDenied.jsp");
            dispatcher.forward(request, response);
        }

        request.setAttribute("order", order);
        Collection<OrderItem> orderItems = orderService.getFullOrderInfo(orderId);
        request.setAttribute("orderItems", orderItems);
        if (loggedInUser.getRole().equals(SecurityConfig.ROLE_ADMIN)) {
            request.setAttribute("user", userDao.findById(order.getUserId()));
        }

        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/order.jsp");
        dispatcher.forward(request, response);
    }
}