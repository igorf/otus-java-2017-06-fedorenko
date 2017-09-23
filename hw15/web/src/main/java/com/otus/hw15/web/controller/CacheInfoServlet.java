package com.otus.hw15.web.controller;

import com.otus.hw15.web.base.SpringServlet;
import com.otus.hw15.web.service.CacheInfoService;
import com.otus.hw15.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configurable
public class CacheInfoServlet extends SpringServlet {

    @Autowired private CacheInfoService cacheInfoService;
    @Autowired private LoginService loginService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginService.setSession(request.getSession());
        if (!loginService.isLogged()) {
            response.sendRedirect("/login");
            return;
        }

        showCaches(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginService.setSession(request.getSession());
        if (!loginService.isLogged()) {
            response.sendRedirect("/login");
            return;
        }

        if (request.getParameter("cache") != null) {
            cacheInfoService.cleanCache(request.getParameter("cache"));
        }
        response.sendRedirect("/");
    }

    private void showCaches(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("caches", cacheInfoService.findCaches());
        request.getRequestDispatcher("/template/cacheinfo.ftl").forward(request, response);
    }
}
