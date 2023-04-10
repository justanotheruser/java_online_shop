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


@WebServlet(name = "adminCreateItem", value = "/admin/createItem")
public class CreateItemServlet extends HttpServlet {
    private final ItemDao itemDao = ItemDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/createItem.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO add validation
        Item item = new Item();
        item.setName(request.getParameter("name"));
        item.setCategory(request.getParameter("category"));
        item.setBrand(request.getParameter("brand"));
        item.setManufacturer(request.getParameter("manufacturer"));
        item.setPartNumber(request.getParameter("partNumber"));
        item.setDescription(request.getParameter("description"));
        item.setPrice(Double.parseDouble(request.getParameter("price")));
        item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        itemDao.save(item);
        response.sendRedirect(request.getContextPath() + "/admin/listItems");
    }
}
