package com.otus.hw15.web.ws;

import com.otus.hw15.data.UserVisualizerRequest;
import com.otus.hw15.data.common.Address;
import com.otus.hw15.data.common.MessageAgent;
import com.otus.hw15.data.common.MessageBroker;
import com.otus.hw15.data.model.User;
import com.otus.hw15.data.specific.UserSessionVisualizer;
import com.otus.hw15.data.specific.messages.FindUserMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Logger;

@ServerEndpoint(value = "/userdata", configurator = SpringConfigurator.class)
@Component
public class UserDataVisualizer implements MessageAgent, UserSessionVisualizer {

    private static final String SHOW_USER_COMMAND = "SHOW_USER";

    private static Logger logger = Logger.getLogger(UserDataVisualizer.class.getName());
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired private Address userVisualizerAddress;
    @Autowired private Address userServiceAddress;
    @Autowired private ApplicationContext context;

    @OnError
    public void error(Session session, Throwable t) {
        logger.warning(t.getMessage());
    }

    @OnMessage
    public void onMessage(Session session, String json) {
        try {
            UserVisualizerRequest request = mapper.readValue(json, UserVisualizerRequest.class);
            if (request.getCommand().equalsIgnoreCase(SHOW_USER_COMMAND)) {
                sendShowUserMessage(session, request.getUserId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String createClientMessage(User user) {
        try {
            return mapper.writeValueAsString(user);
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return "";
    }

    private static void notifyOne(Session session, String msg) throws Exception {
        session.getBasicRemote().sendText(msg);
    }

    @Override
    public Address getAddress() {
        return userVisualizerAddress;
    }

    @Override
    public void showUser(Session session, User user) {
        try {
            notifyOne(session, createClientMessage(user));
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }

    private void sendShowUserMessage(Session session, long userId) {
        MessageBroker messageBroker = context.getBean("messageBroker", MessageBroker.class);
        FindUserMessage msg = new FindUserMessage(userVisualizerAddress, session, userId);
        messageBroker.sendMessage(userServiceAddress, msg);
    }
}
