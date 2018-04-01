package track.msgtest.messenger.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import track.msgtest.messenger.User;
import track.msgtest.messenger.handlers.CommandException;
import track.msgtest.messenger.messages.*;



/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
public class Session implements Runnable {
    static Logger log = LoggerFactory.getLogger(Session.class);

    private User user;
    private static AtomicInteger counter = new AtomicInteger(0);
    private Protocol protocol;
    private MessengerServer server;

    private InputStream in;
    private OutputStream out;

    private volatile boolean isActive;
    private byte[] buffer = new byte[1024 * 16]; // 16 kb

    public Session(User user, Socket socket, Protocol protocol, MessengerServer server) throws IOException {
        this.user = user;
        this.protocol = protocol;
        this.server = server;
        isActive = true;

        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MessengerServer getServer() {
        return server;
    }

    public int getNewId() {
        return counter.getAndIncrement();
    }

    public void send(Message msg) throws ProtocolException, IOException {
        out.write(protocol.encode(msg));
        out.flush();
        // TODO: Отправить клиенту сообщение
    }

    private void onMessage(Message msg) {
        try {
            server.getHandler(msg.getType()).execute(this, msg);
        } catch (CommandException e) {
            log.error("Bad type of message: {}", msg.getType());
        }
    }

    public void close() {
        try {
            out.close();
            in.close();
        } catch (IOException ignored) { }
    }

    @Override
    public void run() {
        while (isActive) {
            try {
                int readBytes = in.read(buffer);
                final byte[] slice = Arrays.copyOfRange(buffer, 0, readBytes);
                Message msg = protocol.decode(slice);
                System.out.println(msg.toString());
                onMessage(msg);
            } catch (Exception e) {
                isActive = false;
                close();
                if (user != null ) {
                    server.removeSession(user.getId());
                }
//                log.error("Session failed: ", e);
            }
        }
    }
}