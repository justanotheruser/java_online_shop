package com.onlineshop.onlineshop;

import java.io.*;

import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "message" + "</h1>");
        /*List<Item> items = getItems();
        out.println(items.size());
        for (Item item : items) {
            out.println("Товар №" + item.getId() + ": " + item.getName());
        }*/
        testPersistence();
        out.println("<h1>" + "message2" + "</h1>");
        out.println("</body></html>");
    }

    private void testPersistence() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User user = new User();
            user.setUsername("username");
            user.setEmail("email");
            user.setPassword("pass");
            user.setFullName("full name");
            user.setPhoneNumber("phone");
            user.setCompanyName("company");
            user.setRole("user");

            entityManager.persist(user);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /*
    private List<Item> getItems()  {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();
        Query<Item> query = session.createQuery("SELECT i FROM Item i", Item.class);
        List<Item> itemList = query.list();
        sessionFactory.close();
        return itemList;
     }
     */
    public void destroy() {
    }
}