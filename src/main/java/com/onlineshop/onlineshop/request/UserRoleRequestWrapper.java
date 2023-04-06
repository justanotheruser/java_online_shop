package com.onlineshop.onlineshop.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.security.Principal;
import java.util.List;

public class UserRoleRequestWrapper extends HttpServletRequestWrapper {

    private final String user;
    private List<String> roles = null;
    private final HttpServletRequest realRequest;

    public UserRoleRequestWrapper(String user, List<String> roles, HttpServletRequest request) {
        super(request);
        this.user = user;
        this.roles = roles;
        this.realRequest = request;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (roles == null) {
            return this.realRequest.isUserInRole(role);
        }
        return roles.contains(role);
    }

    @Override
    public Principal getUserPrincipal() {
        if (this.user == null) {
            return realRequest.getUserPrincipal();
        }
        return () -> user;
    }
}