package com.otus.hw16.frontend.web.ws;

import com.otus.hw16.frontend.client.FrontendMessagesDispatcher;
import com.otus.hw16.frontend.communication.LoginAction;
import com.otus.hw16.frontend.communication.MessagesChain;
import com.otus.hw16.frontend.data.UserLoginRequest;
import com.otus.hw16.frontend.data.messages.LoginMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Logger;

@ServerEndpoint(value = "/userlogin", configurator = SpringConfigurator.class)
@Component
public class UserLoginVisualizer {

    private static final String LOGIN_COMMAND = "LOGIN";

    private static Logger logger = Logger.getLogger(UserLoginVisualizer.class.getName());
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired private MessagesChain messagesChain;
    @Autowired FrontendMessagesDispatcher msgDispatcher;
    @Autowired ApplicationContext context;

    @OnMessage
    public void onMessage(Session session, String json) {
        try {
            UserLoginRequest request = mapper.readValue(json, UserLoginRequest.class);
            if (request.getCommand().equalsIgnoreCase(LOGIN_COMMAND)) {
                sendLoginMessage(session, request);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String createClientMessage(boolean result) {
        try {
            return mapper.writeValueAsString(result);
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return "";
    }

    private static void notifyOne(Session session, String msg) throws Exception {
        session.getBasicRemote().sendText(msg);
    }

    private void sendLoginMessage(Session session, UserLoginRequest request) {
        LoginMessage loginMessage = new LoginMessage(request.getLogin(), request.getPassword());
        LoginAction loginAction = context.getBean(LoginAction.class);
        loginAction.setSession(session);
        messagesChain.pushAction(loginMessage.getMessageID(), loginAction);
        msgDispatcher.sendMessage(loginMessage);
    }

    public void onLogin(Session client, boolean result) {
        try {
            notifyOne(client, createClientMessage(result));
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }
}
