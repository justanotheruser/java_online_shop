package com.onlineshop.onlineshop;

import java.io.*;

import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;

@WebServlet(name = "userInfo", value = "/userInfo")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userInfo.jsp");

        dispatcher.forward(request, response);
    }
}
