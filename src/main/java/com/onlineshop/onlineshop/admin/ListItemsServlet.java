package com.onlineshop.onlineshop.admin;

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
import java.util.Collection;

@WebServlet(name = "adminListItems", value = "/admin/listItems")
public class ListItemsServlet extends HttpServlet {
    private final ItemDao itemDao = ItemDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Item> items = itemDao.findAll();
        request.setAttribute("items", items);
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/listItems.jsp");
        dispatcher.forward(request, response);
    }
}
