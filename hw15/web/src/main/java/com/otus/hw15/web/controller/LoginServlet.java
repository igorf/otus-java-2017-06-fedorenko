package com.otus.hw15.web.controller;

import com.otus.hw15.web.base.SpringServlet;
import com.otus.hw15.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configurable
public class LoginServlet extends SpringServlet {

    @Autowired private LoginService loginService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginService.setSession(request.getSession());
        if (!loginService.isLogged()) {
            request.getRequestDispatcher("/template/login.ftl").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        loginService.setSession(request.getSession());
        if (loginService.login(login, password)) {
            response.sendRedirect("/");
        } else {
            request.setAttribute("loginFailed", true);
            request.getRequestDispatcher("/template/login.ftl").forward(request, response);
        }
    }
}
