package com.otus.hw16.frontend.web.ws;

import com.otus.hw16.frontend.data.CacheRequest;
import com.otus.hw16.frontend.web.service.CacheService;
import com.otus.hw16.frontend.web.service.LoginService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ServerEndpoint(value = "/cachechanged", configurator = SpringConfigurator.class)
@Component
public class CacheChangedNotifier {

    private final static String GET_MSG = "GET";
    private final static String CLEAN_MSG = "CLEAN";

    private static Set<Session> sessions = new HashSet<>();
    private static Logger logger = Logger.getLogger(CacheChangedNotifier.class.getName());
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired private CacheService cacheService;
    @Autowired private LoginService loginService;

    @OnOpen
    public void open(Session session) {
        if (isLogged(session)) {
            sessions.add(session);
        }
    }

    @OnClose
    public void closedConnection(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void error(Session session, Throwable t) {
        logger.warning(t.getMessage());
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        if (isLogged(session)) {
            try {
                CacheRequest request = mapper.readValue(msg, CacheRequest.class);
                if (request.getCommand().equalsIgnoreCase(GET_MSG)) {
                    notifyOne(session, createClientMessage());
                } else if (request.getCommand().equalsIgnoreCase(CLEAN_MSG)) {
                    cacheService.cleanCache(request.getCacheID());
                    notifyClients();
                }
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        }
    }

    private String createClientMessage() {
        try {

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
            for (Session session : sessions) {
                if(!session.isOpen()) {
                    closedSessions.add(session);
                } else {
                    notifyOne(session, msg);
                }
            }
            sessions.removeAll(closedSessions);
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

    public void notifyClients() {
        sendAll(createClientMessage());
    }
}
