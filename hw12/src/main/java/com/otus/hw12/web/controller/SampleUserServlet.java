package com.otus.hw12.web.controller;

import com.otus.hw12.db.service.UserService;
import com.otus.hw12.managers.DbServiceManager;
import com.otus.hw12.web.service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class SampleUserServlet extends HttpServlet {

    private final UserService userService = DbServiceManager.getUserService();
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService loginService = new LoginService(request);
        if (!loginService.isLogged()) {
            response.sendRedirect("/login");
            return;
        }

        try {
            Long uid = Long.valueOf(request.getParameter("uid"));
            request.setAttribute("user", userService.find(uid));
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        } finally {
            request.getRequestDispatcher("/template/user.ftl").forward(request, response);
        }
    }
}
