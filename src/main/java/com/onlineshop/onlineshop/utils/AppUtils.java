package com.onlineshop.onlineshop.utils;

import com.onlineshop.onlineshop.dao.ItemDao;
import entity.Item;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppUtils {
    private static final Map<Integer, String> id_uri_map = new HashMap<>();
    private static final Map<String, Integer> uri_id_map = new HashMap<>();
    private static int REDIRECT_ID = 0;

    // Store user info in Session.
    public static void storeLoggedInUser(HttpSession session, User loggedInUser) {
        session.setAttribute("loggedInUser", loggedInUser);
    }

    // Get the user information stored in the session.
    public static User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    // TODO: разобраться с session
    public static int storeRedirectAfterLoginUrl(HttpSession session, String requestUri) {
        Integer id = uri_id_map.get(requestUri);

        if (id == null) {
            id = REDIRECT_ID++;

            uri_id_map.put(requestUri, id);
            id_uri_map.put(id, requestUri);
            return id;
        }

        return id;
    }

    public static String getRedirectAfterLoginUrl(HttpSession session, int redirectId) {
        String url = id_uri_map.get(redirectId);
        return url;
    }

    public static int addItemToRequestAttrs(HttpServletRequest request, HttpServletResponse response, ItemDao itemDao) throws IOException {
        int itemId = -1;
        try {
            itemId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath());
        }
        Item item = itemDao.findById(itemId);
        request.setAttribute("item", item);
        return itemId;
    }
}
