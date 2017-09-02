package com.otus.hw13.web.ws;

import com.otus.hw13.web.service.CacheInfoService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@ServerEndpoint(value = "/cachechanged", configurator = SpringConfigurator.class)
@Component
public class CacheChangedNotifier {

    private final static String GET_MSG = "GET";
    private static Queue<Session> queue = new ConcurrentLinkedQueue<>();
    @Autowired private CacheInfoService cacheInfoService;

    @OnOpen
    public void open(Session session) {
        queue.add(session);
    }

    @OnClose
    public void closedConnection(Session session) {
        queue.remove(session);
    }

    @OnError
    public void error(Session session, Throwable t) {
        queue.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            if (msg.equalsIgnoreCase(GET_MSG)) {
                notifyOne(session, createClientMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyClients() {
        sendAll(createClientMessage());
    }

    private String createClientMessage() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(cacheInfoService.cacheInfo());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
