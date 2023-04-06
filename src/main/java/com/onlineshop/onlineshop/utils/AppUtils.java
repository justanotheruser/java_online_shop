package com.onlineshop.onlineshop.utils;

import java.util.Map;
import java.util.HashMap;

import jakarta.servlet.http.HttpSession;

import entity.User;

public class AppUtils {
    private static int REDIRECT_ID = 0;

    private static final Map<Integer, String> id_uri_map = new HashMap<>();
    private static final Map<String, Integer> uri_id_map = new HashMap<>();

    // Store user info in Session.
    public static void storeLoggedInUser(HttpSession session, User loggedInUser) {
        session.setAttribute("loggedInUser", loggedInUser);
    }

    // Get the user information stored in the session.
    public static User getLoggedInUser(HttpSession session){
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
        if (url != null) {
            return url;
        }
        return null;
    }
}
