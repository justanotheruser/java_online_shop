package com.onlineshop.onlineshop.admin;

import com.onlineshop.onlineshop.services.OrderService;
import com.onlineshop.onlineshop.services.OrderServiceImpl;
import entity.Order;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "adminListOrders", value = "/admin/listOrders")
public class ListOrdersServlet extends HttpServlet {
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Order> orders = orderService.getAll();
        request.setAttribute("orders", orders);
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/listOrders.jsp");
        dispatcher.forward(request, response);
    }

}
