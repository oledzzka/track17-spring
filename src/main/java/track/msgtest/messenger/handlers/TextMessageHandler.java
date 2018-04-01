package track.msgtest.messenger.handlers;

import track.msgtest.messenger.messages.Message;
import track.msgtest.messenger.messages.StatusMessage;
import track.msgtest.messenger.messages.TextMessage;
import track.msgtest.messenger.net.ProtocolException;
import track.msgtest.messenger.net.Session;

import java.io.IOException;
import java.util.Map;

/**
 * Created by oleg on 12.05.17.
 */
public class TextMessageHandler  implements CommandHandler {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        StatusMessage statusMessage = new StatusMessage();
        TextMessage textMessage = (TextMessage) message;
        if (session.getUser() == null ) {
            statusMessage.setStatus("Залогиньтесь");
        } else {
            statusMessage.setStatus(textMessage.getText());
            Map<Long, Session> sessionMap = session.getServer().getSessionMap();
            sessionMap.entrySet()
                    .stream()
                    .filter(entry -> !entry.getKey().equals(session.getUser().getId()))
                    .forEach(entry -> {
                        try {
                            entry.getValue().send(statusMessage);
                        } catch (ProtocolException | IOException ignored) { }
                    });
            statusMessage.setStatus("Доставлено");
        }
        try {
            session.send(statusMessage);
        } catch (ProtocolException | IOException e) {
            throw new CommandException(e);
        }
    }
}
