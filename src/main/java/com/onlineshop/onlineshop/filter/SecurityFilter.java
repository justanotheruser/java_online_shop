package com.onlineshop.onlineshop.filter;

import com.onlineshop.onlineshop.request.UserRoleRequestWrapper;
import com.onlineshop.onlineshop.utils.AppUtils;
import com.onlineshop.onlineshop.utils.SecurityUtils;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath();

        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }

        // User information stored in the Session (after successful login).
        User loggedInUser = AppUtils.getLoggedInUser(request.getSession());
        HttpServletRequest wrapRequest = request;
        if (loggedInUser != null) {
            String userName = loggedInUser.getUsername();
            List<String> roles = new ArrayList<>() {{
                add(loggedInUser.getRole());
            }};
            wrapRequest = new UserRoleRequestWrapper(userName, roles, request);
        }

        if (SecurityUtils.isSecurityPage(request)) {
            // If the user is not logged in, redirect to the login page.
            if (loggedInUser == null) {
                String requestUri = request.getRequestURI();
                // Store the current page to redirect to after successful login.
                int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }

            // Check if the user has a valid role
            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDenied.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(wrapRequest, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

}