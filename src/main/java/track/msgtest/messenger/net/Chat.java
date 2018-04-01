package track.msgtest.messenger.net;

import track.msgtest.messenger.messages.QueueMessage;
import track.msgtest.messenger.messages.TextMessage;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by oleg on 02.05.17.
 */
public class Chat extends Thread {
    private long id;
    private MessengerServer server;
    private ArrayList<Long> userIdList;
    private QueueMessage queueMessage;



    public Chat(MessengerServer server) {
        this.server = server;
        userIdList = new ArrayList<>(0);
        queueMessage = new QueueMessage();
    }

    public Chat(MessengerServer server, ArrayList<Long> userIdList ) {
        this.server = server;
        this.userIdList = userIdList;
        queueMessage = new QueueMessage(userIdList.size() * 1024);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQueueMessage(TextMessage msg) throws InterruptedException {
        queueMessage.setMessage(msg);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                TextMessage msg = queueMessage.getMessage();
                server.getSessionMap().entrySet()
                        .stream()
                        .filter(entry -> (
                            !entry.getKey().equals(msg.getSenderId()) && userIdList.contains(entry.getKey())
                            ))
                        .forEach(entry -> {
                            try {
                                entry.getValue().send(msg);
                            } catch (ProtocolException | IOException ignored) { }
                        });
            } catch (InterruptedException ignored) { }
        }
    }
}
