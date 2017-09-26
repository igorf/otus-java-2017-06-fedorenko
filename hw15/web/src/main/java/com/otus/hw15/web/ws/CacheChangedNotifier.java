package com.otus.hw15.web.ws;

import com.otus.hw15.data.common.Address;
import com.otus.hw15.data.common.MessageAgent;
import com.otus.hw15.data.specific.ClientsNotifier;
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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ServerEndpoint(value = "/cachechanged", configurator = SpringConfigurator.class)
@Component
public class CacheChangedNotifier implements MessageAgent, ClientsNotifier {

    private final static String GET_MSG = "GET";
    private static Set<Session> sessions = new HashSet<>();
    private static Logger logger = Logger.getLogger(CacheChangedNotifier.class.getName());

    @Autowired private CacheService cacheService;
    @Autowired private LoginService loginService;
    @Autowired private Address screenUpdaterAddress;

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
        try {
            if (msg.equalsIgnoreCase(GET_MSG) && isLogged(session)) {
                notifyOne(session, createClientMessage());
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
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

    @Override
    public Address getAddress() {
        return screenUpdaterAddress;
    }

    @Override
    public void notifyClients() {
        sendAll(createClientMessage());
    }
}
