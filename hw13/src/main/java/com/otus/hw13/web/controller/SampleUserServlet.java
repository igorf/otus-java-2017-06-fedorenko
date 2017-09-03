package com.otus.hw13.web.controller;

import com.otus.hw13.db.service.UserService;
import com.otus.hw13.web.base.SpringServlet;
import com.otus.hw13.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Configurable
public class SampleUserServlet extends SpringServlet {

    @Autowired private LoginService loginService;
    @Autowired private UserService userService;
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginService.setSession(request.getSession());
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
