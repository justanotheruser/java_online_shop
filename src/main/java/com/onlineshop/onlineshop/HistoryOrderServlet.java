package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.services.OrderService;
import com.onlineshop.onlineshop.services.OrderServiceImpl;
import com.onlineshop.onlineshop.utils.AppUtils;
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
        request.setAttribute("orderId", orderId);
        Collection<OrderItem> orderItems = orderService.getFullOrderInfo(orderId);
        request.setAttribute("orderItems", orderItems);
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/order.jsp");
        dispatcher.forward(request, response);
    }
}