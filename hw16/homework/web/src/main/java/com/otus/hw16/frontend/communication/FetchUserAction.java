package com.otus.hw16.frontend.communication;

import com.otus.hw16.frontend.data.messages.ResponseMessage;
import com.otus.hw16.frontend.data.messages.UserFromDBMessage;
import com.otus.hw16.frontend.data.model.User;
import com.otus.hw16.frontend.web.ws.UserDataVisualizer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.Serializable;

@Component
@Scope("prototype")
public class FetchUserAction implements MessageAction, Serializable {

    @Autowired private UserDataVisualizer userVisualizer;

    private UserFromDBMessage message;
    @Setter private Session session;

    @Override
    public void setResponse(ResponseMessage message) {
        this.message = (UserFromDBMessage) message;
    }

    public void run() {
        User user = message.getUser();
        if (session != null) {
            userVisualizer.showUser(session, user);
        }
    }
}
