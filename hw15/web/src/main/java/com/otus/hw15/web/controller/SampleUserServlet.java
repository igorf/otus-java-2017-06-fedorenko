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
public class SampleUserServlet extends SpringServlet {

    @Autowired private LoginService loginService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginService.setSession(request.getSession());
        if (!loginService.isLogged()) {
            response.sendRedirect("/login");
            return;
        }

        request.getRequestDispatcher("/template/user.ftl").forward(request, response);
    }
}
