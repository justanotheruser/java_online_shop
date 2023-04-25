package com.onlineshop.onlineshop;

import com.onlineshop.onlineshop.services.UserService;
import com.onlineshop.onlineshop.services.UserServiceException;
import com.onlineshop.onlineshop.services.UserServiceImpl;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.onlineshop.onlineshop.config.SecurityConfig;
import jakarta.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

@WebServlet(name = "registration", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!request.getParameter("password").equals(request.getParameter("passwordConfirmation"))) {
            request.setAttribute("errorMessage", "Введённые пароли не совпадают");
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp");
            dispatcher.forward(request, response);
            return;
        }

        User user = new User();
        user.setUsername(request.getParameter("username"));
        String passwordHash = LoginServlet.getHash(request.getParameter("password"));
        user.setPasswordHash(passwordHash);
        user.setEmail(request.getParameter("email"));
        user.setFullName(request.getParameter("fullName"));
        user.setPhoneNumber(request.getParameter("phone"));
        user.setCompanyName(request.getParameter("companyName"));
        user.setRole(SecurityConfig.ROLE_CUSTOMER);
        try {
            userService.save(user);
        } catch (UserServiceException e) {
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp");
            dispatcher.forward(request, response);
            return;
        }
        try {
            EmailService emailService = new EmailService();
            ArrayList<String> emailList = new ArrayList<>(Collections.singletonList(user.getEmail()));
            emailService.SendMail(emailList, "Подтверждение регистрации", buildEmailConfirmationLetter(request, user));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registration_success.jsp");
        dispatcher.forward(request, response);
    }

    protected String buildEmailConfirmationLetter(HttpServletRequest request, User user) {
        String text = "Для завершения регистрации перейдите по ссылке ";
        String activationURL =  buildActivationURL(request, user.getUsername(), user.getPasswordHash());
        text += activationURL;
        return text;
    }

    private String buildActivationURL(HttpServletRequest request, String username, String passHash) {
        ServletContext servletContext = this.getServletContext();
        ServletRegistration accountActivationServletRegistration = servletContext.getServletRegistration("accountActivation");
        String activationEndpoint = accountActivationServletRegistration.getMappings().toArray()[0].toString();
        String activationURL = request.getRequestURL().toString().replace("/registration", activationEndpoint);
        activationURL += "?username=" + username;
        activationURL += "&passwordHash=" + passHash;
        return activationURL;
    }
}
