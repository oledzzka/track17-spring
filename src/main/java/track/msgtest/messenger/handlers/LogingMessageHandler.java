package track.msgtest.messenger.handlers;

import track.msgtest.messenger.User;
import track.msgtest.messenger.messages.LoginMessage;
import track.msgtest.messenger.messages.Message;
import track.msgtest.messenger.messages.StatusMessage;
import track.msgtest.messenger.net.ProtocolException;
import track.msgtest.messenger.net.Session;

import java.io.IOException;

/**
 * Created by oleg on 12.06.17.
 */
public class LogingMessageHandler implements CommandHandler {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        LoginMessage loginMessage = (LoginMessage) message;
        String userName = loginMessage.getName();
        String password = loginMessage.getPass();
        User user = new User(userName, password);
        user.setId(session.getNewId());
        session.setUser(user);
        session.getServer().setSession(user.getId(), session);
        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatus(user.toString());
        try {
            session.send(statusMessage);
        } catch (ProtocolException | IOException e) {
            throw new CommandException(e);
        }
    }
}
