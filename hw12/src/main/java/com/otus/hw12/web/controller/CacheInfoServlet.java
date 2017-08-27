package com.otus.hw12.web.controller;

import com.otus.hw12.web.service.CacheInfoService;
import com.otus.hw12.web.service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CacheInfoServlet extends HttpServlet {

    private final CacheInfoService cacheInfoService = new CacheInfoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService loginService = new LoginService(request);
        if (!loginService.isLogged()) {
            response.sendRedirect("/login");
            return;
        }

        showCaches(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService loginService = new LoginService(request);
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
