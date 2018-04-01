package track.msgtest.messenger.net;

import track.msgtest.messenger.handlers.CommandHandler;
import track.msgtest.messenger.handlers.InfoMessageHandler;
import track.msgtest.messenger.handlers.LogingMessageHandler;
import track.msgtest.messenger.handlers.TextMessageHandler;
import track.msgtest.messenger.messages.Type;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class MessengerServer implements Runnable {
    private static final int PORT = 19000;
    private static final int CONNECTION_NUMBS = 100;
    private  ExecutorService service;
    private Map<Type, CommandHandler> handlerMap;
    private ServerSocket serverSocket;
    private Map<Long, Session> sessionMap;

    public MessengerServer() throws IOException {
        service = Executors.newFixedThreadPool(CONNECTION_NUMBS);
        serverSocket = new ServerSocket(PORT);
        sessionMap = new ConcurrentHashMap<>(0);
    }

    public Map<Long, Session> getSessionMap() {
        return sessionMap;
    }

    public void setSession(Long userId, Session session) {
        sessionMap.put(userId, session);
    }

    public void removeSession(Long userId) {
        sessionMap.remove(userId);
    }

    private void createHandlerList() {
        handlerMap = new HashMap<>();
        handlerMap.put(Type.MSG_TEXT, new TextMessageHandler());
        handlerMap.put(Type.MSG_INFO, new InfoMessageHandler());
        handlerMap.put(Type.MSG_LOGIN, new LogingMessageHandler());
    }

    public CommandHandler getHandler(Type type) {
        return handlerMap.get(type);
    }

    public static void main(String[] args) throws IOException {
        MessengerServer messengerServer = new MessengerServer();
        messengerServer.createHandlerList();
        new Thread(messengerServer).start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                Session session = new Session(null, socket, new JsonProtocol(), this);
                service.submit(session);
            } catch (IOException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
