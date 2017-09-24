package com.otus.hw15.web.ws;

import com.otus.hw15.web.service.CacheService;
import com.otus.hw15.web.service.LoginService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

@ServerEndpoint(value = "/cachechanged", configurator = SpringConfigurator.class)
@Component
public class CacheChangedNotifier {

    private final static String GET_MSG = "GET";
    private static Queue<Session> queue = new ConcurrentLinkedQueue<>();
    private static Logger logger = Logger.getLogger(CacheChangedNotifier.class.getName());
    @Autowired private CacheService cacheService;
    @Autowired private LoginService loginService;

    @OnOpen
    public void open(Session session) {
        if (isLogged(session)) {
            queue.add(session);
        }
    }

    @OnClose
    public void closedConnection(Session session) {
        queue.remove(session);
    }

    @OnError
    public void error(Session session, Throwable t) {
        logger.warning(t.getMessage());
        queue.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            if (msg.equalsIgnoreCase(GET_MSG) && isLogged(session)) {
                notifyOne(session, createClientMessage());
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void notifyClients() {
        sendAll(createClientMessage());
    }

    private String createClientMessage() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(cacheService.summary());
            return result;
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return "";
    }

    private static void notifyOne(Session session, String msg) throws Exception {
        session.getBasicRemote().sendText(msg);
    }

    private static void sendAll(String msg) {
        try {
            ArrayList<Session> closedSessions = new ArrayList<>();
            for (Session session : queue) {
                if(!session.isOpen()) {
                    closedSessions.add(session);
                } else {
                    notifyOne(session, msg);
                }
            }
            queue.removeAll(closedSessions);
        } catch (Throwable e) {
            logger.warning(e.getMessage());
        }
    }

    private boolean isLogged(Session session) {
        try {
            HttpSession httpSession = ((PrincipalWithSession) session.getUserPrincipal()).getSession();
            loginService.setSession(httpSession);
            return loginService.isLogged();
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return false;
    }
}
