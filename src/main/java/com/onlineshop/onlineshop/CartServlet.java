package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.dao.ItemDao;
import com.onlineshop.onlineshop.dao.ItemDaoImpl;
import com.onlineshop.onlineshop.dao.OrderDao;
import com.onlineshop.onlineshop.dao.OrderDaoImpl;
import com.onlineshop.onlineshop.services.OrderService;
import com.onlineshop.onlineshop.services.OrderServiceImpl;
import com.onlineshop.onlineshop.utils.AppUtils;
import entity.CartItem;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final ItemDao itemDao = ItemDaoImpl.getInstance();
    private final OrderService orderService = OrderServiceImpl.getInstance();
    private static double getTotalPrice(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        double result = 0;
        for (CartItem item : cart) {
            result += item.getProduct().getPrice() * item.getQuantity();
        }
        return result;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            doGet_DisplayCart(request, response);
        } else {
            if (action.equalsIgnoreCase("buy")) {
                doGet_Buy(request, response);
            } else if (action.equalsIgnoreCase("remove")) {
                doGet_Remove(request, response);
            }
        }
    }

    protected void doGet_DisplayCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/cart.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet_Remove(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        int itemId = Integer.parseInt(request.getParameter("id"));
        int index = isExisting(itemId, cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
        response.sendRedirect("cart");
    }

    protected void doGet_Buy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("cart") == null) {
            List<CartItem> cart = new ArrayList<>();
            int itemId = Integer.parseInt(request.getParameter("id"));
            cart.add(new CartItem(itemDao.findById(itemId), 1));
            session.setAttribute("cart", cart);
        } else {
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            int itemId = Integer.parseInt(request.getParameter("id"));
            int index = isExisting(itemId, cart);
            if (index == -1) {
                cart.add(new CartItem(itemDao.findById(itemId), 1));
            } else {
                int quantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart", cart);
        }
        response.sendRedirect(request.getContextPath());
    }

    private int isExisting(int id, List<CartItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getId() == id) {
                return i;
            }
        }
        return -1;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = AppUtils.getLoggedInUser(session);
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        if (session.getAttribute("cart") == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        Order order = new Order();
        order.setUserId(loggedInUser.getId());
        order.setDateCreated(new Date(System.currentTimeMillis()));
        order.setTotalPrice(CartServlet.getTotalPrice(session));
        order.setStatus("OPEN");
        order.setDeliveryMethod(request.getParameter("method"));
        order.setAdditionalNotes(request.getParameter("additionalNotes"));

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart) {
            OrderItem orderItem = new OrderItem(order);
            orderItem.setItemId(cartItem.getProduct().getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        }
        orderService.saveOrder(order, orderItems);
        response.sendRedirect(request.getContextPath());
    }
}