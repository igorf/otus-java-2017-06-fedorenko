package com.otus.hw16.frontend.communication;

import com.otus.hw16.frontend.data.messages.ResponseMessage;
import com.otus.hw16.frontend.data.messages.UserFromDBMessage;
import com.otus.hw16.frontend.data.model.User;
import com.otus.hw16.frontend.web.service.LoginService;
import com.otus.hw16.frontend.web.ws.UserLoginVisualizer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.Serializable;

@Component
@Scope("prototype")
public class LoginAction implements MessageAction, Serializable {

    @Autowired private LoginService loginService;
    @Autowired private UserLoginVisualizer loginVisualizer;

    private UserFromDBMessage message;
    @Setter private Session session;

    @Override
    public void setResponse(ResponseMessage message) {
        this.message = (UserFromDBMessage) message;
    }

    public void run() {
        User user = message.getUser();
        if (user != null) {
            loginService.setUserLogged(user);
        }
        if (session != null) {
            loginVisualizer.onLogin(session, user != null);
        }
    }
}
