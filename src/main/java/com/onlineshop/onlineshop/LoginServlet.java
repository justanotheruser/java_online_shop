package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.utils.AppUtils;
import entity.User;
import jakarta.persistence.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        User user = findUser(userName, password);
        if (user == null) {
            String errorMessage = "Неправильный логин или пароль";
            request.setAttribute("errorMessage", errorMessage);

            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");

            dispatcher.forward(request, response);
            return;
        }

        AppUtils.storeLoggedInUser(request.getSession(), user);
        int redirectId = -1;
        try {
            redirectId = Integer.parseInt(request.getParameter("redirectId"));
        } catch (Exception e) {
        }
        String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
        // By default after successful login redirect to /userInfo page
        response.sendRedirect(Objects.requireNonNullElseGet(requestUri, () -> request.getContextPath() + "/userInfo"));
    }

    protected User findUser(String username, String password) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            TypedQuery<User> userByUsernameAndPass = entityManager.createNamedQuery("User.byUsernameAndPassword", User.class);
            userByUsernameAndPass.setParameter(1, username);
            userByUsernameAndPass.setParameter(2, password);
            List<User> foundUsers = userByUsernameAndPass.getResultList();
            transaction.commit();
            if (foundUsers.size() > 0) {
                return foundUsers.get(0);
            }
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return null;
    }
}
