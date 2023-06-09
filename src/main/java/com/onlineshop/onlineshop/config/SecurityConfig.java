package com.onlineshop.onlineshop.config;

import java.util.*;

public class SecurityConfig {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CUSTOMER = "CUSTOMER";

    private static final Map<String, List<String>> mapConfig = new HashMap</*role, urlPatterns*/>();

    static {
        init();
    }

    private static void init() {
        List<String> customerUrlPatterns = new ArrayList<>();
        customerUrlPatterns.add("/userInfo");
        customerUrlPatterns.add("/cart");
        customerUrlPatterns.add("/history");
        customerUrlPatterns.add("/history/order");
        mapConfig.put(ROLE_CUSTOMER, customerUrlPatterns);

        List<String> adminUrlPatterns = new ArrayList<>();
        adminUrlPatterns.add("/userInfo");
        adminUrlPatterns.add("/admin/listItems");
        adminUrlPatterns.add("/admin/listOrders");
        adminUrlPatterns.add("/admin/listUsers");
        adminUrlPatterns.add("/history/order");
        mapConfig.put(ROLE_ADMIN, adminUrlPatterns);
    }

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }

}