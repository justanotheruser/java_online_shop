package com.onlineshop.onlineshop.admin;

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

@WebServlet(name = "adminListItems", value = "/admin/listItems")
public class ListItemsServlet extends HttpServlet {
    private final ItemDao itemDao = ItemDaoImpl.getInstance();
    private final ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        Collection<Item> items;
        if (category == null) {
            String quantity = request.getParameter("quantity");
            if (quantity != null && quantity.equals("zero_or_less")) {
                items = itemDao.findWithZeroOrLessQuantity();
            } else {
                items = itemDao.findAll();
            }
        } else {
            items = itemDao.findByCategory(category);
        }
        request.setAttribute("items", items);
        request.setAttribute("categories", itemService.getCategories());
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/listItems.jsp");
        dispatcher.forward(request, response);
    }
}
