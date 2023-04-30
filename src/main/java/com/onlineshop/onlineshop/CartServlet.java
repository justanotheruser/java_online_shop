package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.config.SecurityConfig;
import com.onlineshop.onlineshop.dao.ItemDao;
import com.onlineshop.onlineshop.dao.ItemDaoImpl;
import com.onlineshop.onlineshop.dao.UserDao;
import com.onlineshop.onlineshop.dao.UserDaoImpl;
import com.onlineshop.onlineshop.services.OrderService;
import com.onlineshop.onlineshop.services.OrderServiceImpl;
import com.onlineshop.onlineshop.utils.AppUtils;
import entity.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final ItemDao itemDao = ItemDaoImpl.getInstance();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final OrderService orderService = OrderServiceImpl.getInstance();

    private static double getTotalPrice(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        double result = 0;
        for (CartItem item : cart) {
            result += item.getProduct().getPrice() * item.getQuantity();
        }
        return result;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    protected void doGet_DisplayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        ArrayList<OrderedItemQuantityProblem> orderProblems = getOrderedItemQuantityProblems(cart);
        request.setAttribute("orderProblems", orderProblems);
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/cart.jsp");
        dispatcher.forward(request, response);
    }

    private ArrayList<OrderedItemQuantityProblem> getOrderedItemQuantityProblems(List<CartItem> cart) {
        ArrayList<OrderedItemQuantityProblem> orderProblems = new ArrayList<>();
        if (cart != null) {
            for (CartItem cartItem : cart) {
                Item item = itemDao.findById(cartItem.getProduct().getId());
                if (item != null && item.getQuantity() < cartItem.getQuantity()) {
                    orderProblems.add(new OrderedItemQuantityProblem(item, cartItem.getQuantity()));
                }
            }
        }
        return orderProblems;
    }

    protected void doGet_Remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        int itemId = Integer.parseInt(request.getParameter("id"));
        int index = isExisting(itemId, cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
        response.sendRedirect("cart");
    }

    protected void doGet_Buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        if (session.getAttribute("cart") == null) {
            List<CartItem> cart = new ArrayList<>();
            int itemId = Integer.parseInt(request.getParameter("id"));
            cart.add(new CartItem(itemDao.findById(itemId), quantity));
            session.setAttribute("cart", cart);
        } else {
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            int itemId = Integer.parseInt(request.getParameter("id"));
            int index = isExisting(itemId, cart);
            if (index == -1) {
                cart.add(new CartItem(itemDao.findById(itemId), quantity));
            } else {
                quantity += cart.get(index).getQuantity();
                cart.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart", cart);
        }
        response.sendRedirect(request.getContextPath() + "/items");
    }

    private int isExisting(int id, List<CartItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getId() == id) {
                return i;
            }
        }
        return -1;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        ArrayList<OrderItem> orderItems = saveOrderToDb(request, session, loggedInUser);
        sendEmailToAdmins(orderItems);
        User user = AppUtils.getLoggedInUser(session);
        sendEmailToCustomer(user, orderItems);
        session.removeAttribute("cart");
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/order_success.jsp");
        dispatcher.forward(request, response);
    }

    private void sendEmailToCustomer(User user, ArrayList<OrderItem> orderItems) {
        ArrayList<String> emailAddresses = new ArrayList<>(Collections.singletonList(user.getEmail()));
        try {
            EmailService emailService = new EmailService();
            emailService.SendMail(emailAddresses, "Копия заказа", buildOrderEmailBody(orderItems));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmailToAdmins(ArrayList<OrderItem> orderItems) {
        List<User> admins = userDao.findByRole(SecurityConfig.ROLE_ADMIN);
        ArrayList<String> adminEmails = new ArrayList<>();
        for (User admin : admins) {
            adminEmails.add(admin.getEmail());
        }
        try {
            EmailService emailService = new EmailService();
            emailService.SendMail(adminEmails, "Новый заказ", buildOrderEmailBody(orderItems));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String buildOrderEmailBody(ArrayList<OrderItem> orderItems) {
        User buyer = userDao.findById(((OrderItem) orderItems.toArray()[0]).getOrder().getUserId());
        StringBuilder messageBody = new StringBuilder();
        messageBody.append("<h1>Заказ от " + buyer.getUsername() + "</h1>");
        messageBody.append("<table><tr><th>Товар</th><th>Партнамбер</th><th>Количество</th>");
        for (OrderItem orderItem : orderItems) {
            messageBody.append("<tr>");
            messageBody.append("<td>" + orderItem.getItem().getName() + "</td>");
            messageBody.append("<td>" + orderItem.getItem().getPartNumber() + "</td>");
            messageBody.append("<td>" + orderItem.getQuantity() + "</td>");
            messageBody.append("</td>");
        }
        messageBody.append("</table><br>");
        Order order = orderItems.get(0).getOrder();
        messageBody.append("Способ доставки: " + order.getDeliveryMethod() + "<br>");
        messageBody.append("Доп. информация: " + order.getAdditionalNotes() + "<br>");
        messageBody.append("Дата оформления: " + LocalDate.now());
        return messageBody.toString();
    }

    private ArrayList<OrderItem> saveOrderToDb(HttpServletRequest request, HttpSession session, User loggedInUser) {
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
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(itemDao.findById(cartItem.getProduct().getId()));
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        }
        orderService.saveOrder(order, orderItems);
        return orderItems;
    }
}