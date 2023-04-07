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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "adminListItems", value = "/admin/listItems")
public class ListItemsServlet extends HttpServlet {
    private final ItemDao itemDao = ItemDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Item> items = itemDao.findAll();
        List<List<Item>> preparedItems = splitIntoRows(items, 3);
        request.setAttribute("itemRows", preparedItems);
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/listItems.jsp");
        dispatcher.forward(request, response);
    }

    private List<List<Item>> splitIntoRows(Collection<Item> items, int by) {
        List<List<Item>> result = new ArrayList<>();
        List<Item> itemRow = new ArrayList<>();
        for (Item item : items) {
            if (itemRow.size() < by) {
                itemRow.add(item);
            } else {
                result.add(itemRow);
                itemRow = new ArrayList<>();
                itemRow.add(item);
            }
        }
        if (itemRow.size() > 0) {
            result.add(itemRow);
        }
        return result;
    }
}
