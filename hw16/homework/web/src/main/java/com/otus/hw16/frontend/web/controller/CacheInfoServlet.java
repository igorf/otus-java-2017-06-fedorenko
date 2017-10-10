package com.otus.hw16.frontend.web.controller;

import com.otus.hw16.frontend.web.base.SpringServlet;
import com.otus.hw16.frontend.web.service.CacheService;
import com.otus.hw16.frontend.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configurable
public class CacheInfoServlet extends SpringServlet {

    @Autowired private CacheService cacheService;
    @Autowired private LoginService loginService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginService.setSession(request.getSession());
        if (!loginService.isLogged()) {
            response.sendRedirect("/login");
            return;
        }

        request.setAttribute("caches", cacheService.findCaches());
        request.getRequestDispatcher("/template/cacheinfo.ftl").forward(request, response);
    }
}
