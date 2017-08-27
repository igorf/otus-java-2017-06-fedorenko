package com.otus.hw12.web.controller;

import com.otus.hw12.web.service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService service = new LoginService(request);
        if (!service.isLogged()) {
            request.getRequestDispatcher("/template/login.ftl").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        LoginService service = new LoginService(request);
        if (service.login(login, password)) {
            response.sendRedirect("/");
        } else {
            request.setAttribute("loginFailed", true);
            request.getRequestDispatcher("/template/login.ftl").forward(request, response);
        }
    }
}
