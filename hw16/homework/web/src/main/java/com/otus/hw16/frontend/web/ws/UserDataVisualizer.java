package com.otus.hw16.frontend.web.ws;

import com.otus.hw16.frontend.client.FrontendMessagesDispatcher;
import com.otus.hw16.frontend.communication.FetchUserAction;
import com.otus.hw16.frontend.communication.MessagesChain;
import com.otus.hw16.frontend.data.UserVisualizerRequest;
import com.otus.hw16.frontend.data.messages.FindUserByIDMessage;
import com.otus.hw16.frontend.data.model.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Logger;

@ServerEndpoint(value = "/userdata", configurator = SpringConfigurator.class)
@Component
public class UserDataVisualizer {

    private static final String SHOW_USER_COMMAND = "SHOW_USER";
    private static Logger logger = Logger.getLogger(UserDataVisualizer.class.getName());
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired private MessagesChain messagesChain;
    @Autowired FrontendMessagesDispatcher msgDispatcher;
    @Autowired ApplicationContext context;

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

    public void showUser(Session session, User user) {
        try {
            notifyOne(session, createClientMessage(user));
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }

    private void sendShowUserMessage(Session session, long userId) {
        FindUserByIDMessage message = new FindUserByIDMessage((int) userId);
        FetchUserAction action = context.getBean(FetchUserAction.class);
        action.setSession(session);
        messagesChain.pushAction(message.getMessageID(), action);
        msgDispatcher.sendMessage(message);
    }
}
