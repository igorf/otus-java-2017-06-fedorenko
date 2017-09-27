package com.otus.hw15.web.ws;

import com.otus.hw15.data.UserLoginRequest;
import com.otus.hw15.data.common.Address;
import com.otus.hw15.data.common.MessageAgent;
import com.otus.hw15.data.common.MessageBroker;
import com.otus.hw15.data.specific.UserLoginCarrier;
import com.otus.hw15.data.specific.UserLoginService;
import com.otus.hw15.data.specific.messages.FindUserMessage;
import com.otus.hw15.data.specific.messages.LoginMessage;
import com.otus.hw15.web.service.LoginService;
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

@ServerEndpoint(value = "/userlogin", configurator = SpringConfigurator.class)
@Component
public class UserLoginVisualizer implements MessageAgent, UserLoginCarrier {

    private static final String LOGIN_COMMAND = "LOGIN";

    private static Logger logger = Logger.getLogger(UserLoginVisualizer.class.getName());
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired private Address loginVisualizerAddress;
    @Autowired private Address userServiceAddress;
    @Autowired private ApplicationContext context;

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

    @Override
    public Address getAddress() {
        return loginVisualizerAddress;
    }


    private void sendLoginMessage(Session session, UserLoginRequest request) {
        MessageBroker messageBroker = context.getBean("messageBroker", MessageBroker.class);
        UserLoginService loginService = context.getBean("loginService", LoginService.class);
        LoginMessage msg = new LoginMessage(loginVisualizerAddress, session, loginService, request.getLogin(), request.getPassword());
        messageBroker.sendMessage(userServiceAddress, msg);
    }

    @Override
    public void onLogin(Session client, boolean result) {
        try {
            notifyOne(client, createClientMessage(result));
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }
}
