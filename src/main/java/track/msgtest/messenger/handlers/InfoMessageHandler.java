package track.msgtest.messenger.handlers;

import track.msgtest.messenger.messages.InfoMessage;
import track.msgtest.messenger.messages.InfoResultMessage;
import track.msgtest.messenger.messages.Message;
import track.msgtest.messenger.net.ProtocolException;
import track.msgtest.messenger.net.Session;

import java.io.IOException;

/**
 * Created by oleg on 12.06.17.
 */
public class InfoMessageHandler implements CommandHandler {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        InfoMessage infoMessage = (InfoMessage) message;
        long userId = infoMessage.getUserId();
        InfoResultMessage infoResultMessage = new InfoResultMessage();
        if (userId == InfoMessage.CURRENT_USER) {
            infoResultMessage.setInformation(session.getUser().toString());
        } else {
            infoResultMessage.setInformation("Другой юзер");
        }
        try {
            session.send(infoResultMessage);
        } catch (ProtocolException | IOException  e) {
            throw new CommandException(e);
        }
    }
}
