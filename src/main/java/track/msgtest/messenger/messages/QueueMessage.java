package track.msgtest.messenger.messages;

import java.util.ArrayDeque;

/**
 * Created by oleg on 11.05.17.
 */
public class QueueMessage {
    private int size = 1024;
    private ArrayDeque<TextMessage> messageArrayDeque;

    public QueueMessage() {
        messageArrayDeque = new ArrayDeque<>(size);
    }

    public QueueMessage(int size) {
        this.size = size;
        messageArrayDeque = new ArrayDeque<>(size);
    }

    public synchronized void setMessage(TextMessage msg) throws InterruptedException {
        while (messageArrayDeque.size() == size ) {
            wait();
        }
        messageArrayDeque.add(msg);
        notifyAll();
    }

    public synchronized TextMessage getMessage() throws InterruptedException {
        while (messageArrayDeque.isEmpty()) {
            wait();
        }
        TextMessage msg = messageArrayDeque.removeFirst();
        notifyAll();
        return msg;
    }
}