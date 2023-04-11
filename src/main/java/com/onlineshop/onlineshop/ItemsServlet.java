package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.dao.ItemDao;
import com.onlineshop.onlineshop.dao.ItemDaoImpl;
import com.onlineshop.onlineshop.services.ItemService;
import com.onlineshop.onlineshop.services.ItemServiceImpl;
import entity.Item;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "items", value = "/items")
public class ItemsServlet extends HttpServlet {
    private final ItemDao itemDao = ItemDaoImpl.getInstance();
    private final ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        Collection<Item> items;
        if (category == null) {
            items = itemDao.findAll();
        } else {
            items = itemDao.findByCategory(category);
        }
        request.setAttribute("items", items);
        request.setAttribute("categories", itemService.getCategories());
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/items.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        Collection<Item> items;
        if (request.getParameter("partNumber") != null) {
            items = itemDao.findByPartNumber(request.getParameter("partNumber"), category);
        } else if (request.getParameter("keyword") != null) {
            items = itemDao.findWithDescriptionLike(request.getParameter("keyword"), category);
        } else if (category != null) {
            items = itemDao.findByCategory(category);
        } else {
            items = itemDao.findAll();
        }

        request.setAttribute("items", items);
        request.setAttribute("categories", itemService.getCategories());
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/items.jsp");
        dispatcher.forward(request, response);
    }
}