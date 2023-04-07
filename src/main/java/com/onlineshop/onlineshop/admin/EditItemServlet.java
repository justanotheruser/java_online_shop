package com.onlineshop.onlineshop.admin;

import com.onlineshop.onlineshop.dao.ItemDao;
import com.onlineshop.onlineshop.dao.ItemDaoImpl;
import com.onlineshop.onlineshop.utils.AppUtils;
import entity.Item;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@WebServlet(name = "adminEditItem", value = "/admin/editItem")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,    // 10 MB
        maxFileSize = 1024 * 1024 * 50,        // 50 MB
        maxRequestSize = 1024 * 1024 * 100)    // 100 MB
public class EditItemServlet extends HttpServlet {
    private final ItemDao itemDao = ItemDaoImpl.getInstance();
    private int itemId;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        itemId = AppUtils.addItemToRequestAttrs(request, response, itemDao);
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/editItem.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getContentType().equals("application/x-www-form-urlencoded")) {
            // TODO add validation
            Item item = new Item();
            item.setId(itemId);
            item.setName(request.getParameter("name"));
            item.setCategory(request.getParameter("category"));
            item.setBrand(request.getParameter("brand"));
            item.setManufacturer(request.getParameter("manufacturer"));
            item.setPartNumber(Integer.parseInt(request.getParameter("partNumber")));
            item.setDescription(request.getParameter("description"));
            item.setPrice(Double.parseDouble(request.getParameter("price")));
            item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            itemDao.update(item);
        } else if (request.getContentType().startsWith("multipart/form-data")) {
            Part imageFilePart = request.getPart("image");
            Item item;
            if (imageFilePart != null) {
                item = itemDao.findById(itemId);
                InputStream inputStream;
                inputStream = imageFilePart.getInputStream();
                byte[] imageByteArray = new byte[inputStream.available()];
                inputStream.read(imageByteArray);
                inputStream.close();
                String base64Image = Base64.getEncoder().encodeToString(imageByteArray);
                item.setBase64Image(base64Image);
                itemDao.update(item);
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/listItems");
    }
}
