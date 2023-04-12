package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.dao.ItemDao;
import com.onlineshop.onlineshop.dao.ItemDaoImpl;
import entity.Item;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@WebServlet(name = "newItems", value = "/items/new")
public class NewItemsServlet extends HttpServlet {
    private final ItemDao itemDao = ItemDaoImpl.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate now = LocalDate.now();
        Date createdAfter = java.sql.Date.valueOf(now.minusDays(7));
        Collection<Item> items = itemDao.findCreatedAfter(createdAfter);
        request.setAttribute("items", items);
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/newItems.jsp");
        dispatcher.forward(request, response);
    }
}