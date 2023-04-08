package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.services.OrderService;
import com.onlineshop.onlineshop.services.OrderServiceImpl;
import com.onlineshop.onlineshop.utils.AppUtils;
import entity.Order;
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

@WebServlet(name = "orderHistory", value = "/history")
public class OrderHistoryServlet extends HttpServlet {
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = AppUtils.getLoggedInUser(session);
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        Collection<Order> orders = orderService.getCustomerOrders(loggedInUser.getId());
        request.setAttribute("orders", orders);
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/history.jsp");
        dispatcher.forward(request, response);
    }
}
