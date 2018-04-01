package track.msgtest.messenger.teacher.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import track.msgtest.messenger.messages.*;
import track.msgtest.messenger.net.Protocol;
import track.msgtest.messenger.net.ProtocolException;
import track.msgtest.messenger.net.JsonProtocol;


/**
 *
 */
public class MessengerClient {


    /**
     * Механизм логирования позволяет более гибко управлять записью данных в лог (консоль, файл и тд)
     * */
    static Logger log = LoggerFactory.getLogger(MessengerClient.class);

    /**
     * Протокол, хост и порт инициализируются из конфига
     *
     * */
    private Protocol protocol;
    private int port;
    private String host;

    /**
     * С каждым сокетом связано 2 канала in/out
     */
    private InputStream in;
    private OutputStream out;

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void initSocket() throws IOException {
        Socket socket = new Socket(host, port);
        in = socket.getInputStream();
        out = socket.getOutputStream();

        /*
      Тред "слушает" сокет на наличие входящих сообщений от сервера
     */
        Thread socketListenerThread = new Thread(() -> {
            final byte[] buf = new byte[1024 * 64];
            log.info("Starting listener thread...");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Здесь поток блокируется на ожидании данных
                    int read = in.read(buf);
                    if (read > 0) {

                        // По сети передается поток байт, его нужно раскодировать с помощью протокола
                        Message msg = protocol.decode(Arrays.copyOf(buf, read));
                        onMessage(msg);
                    }
                } catch (Exception e) {
                    log.error("Failed to process connection: {}", e);
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });

        socketListenerThread.start();
    }

    /**
     * Реагируем на входящее сообщение
     */
    public void onMessage(Message msg) {
        log.info("Message received: {}", msg);
        switch (msg.getType()) {
            case MSG_STATUS:
                StatusMessage statusMessage = (StatusMessage) msg;
                System.out.println(statusMessage.getStatus());
                break;
            case MSG_INFO_RESULT:
                InfoResultMessage infoResultMessage = (InfoResultMessage) msg;
                System.out.println(infoResultMessage.getInformation());
                break;
            default:
                log.error("Error type message {}", msg.getType());
        }
    }

    /**
     * Обрабатывает входящую строку, полученную с консоли
     * Формат строки можно посмотреть в вики проекта
     */
    public void processInput(String line) throws IOException, ProtocolException {
        String[] tokens = line.split(" ");
        log.info("Tokens: {}", Arrays.toString(tokens));
        String cmdType = tokens[0];
        switch (cmdType) {
            case "/login":
                if (tokens.length > 2) {
                    String user = tokens[1];
                    String passwd = tokens[2];
                    LoginMessage loginMessage = new LoginMessage(user, passwd);
                    send(loginMessage);
                } else {
                    log.error("Error login string {}", line);
                }

                break;
            case "/help":
                System.out.println("help");
                break;
            case "/text":
                // FIXME: пример реализации для простого текстового сообщения
                if (tokens.length > 1 ) {
                    TextMessage sendMessage = new TextMessage();
                    sendMessage.setType(Type.MSG_TEXT);
                    sendMessage.setText(tokens[1]);
                    send(sendMessage);
                } else {
                    log.error("Error text string {}", line);
                }

                break;
            // TODO: implement another types from wiki

            default:
                log.error("Invalid input: " + line);
        }
    }

    /**
     * Отправка сообщения в сокет клиент -> сервер
     */
    public void send(Message msg) throws IOException, ProtocolException {
        log.info(msg.toString());
        out.write(protocol.encode(msg));
        out.flush(); // принудительно проталкиваем буфер с данными
    }

    public static void main(String[] args) throws Exception {

        MessengerClient client = new MessengerClient();
        client.setHost("localhost");
        client.setPort(19000);
        client.setProtocol(new JsonProtocol());

        try {
            client.initSocket();

            // Цикл чтения с консоли
            Scanner scanner = new Scanner(System.in);
            System.out.println("$");
            while (true) {
                String input = scanner.nextLine();
                if ("q".equals(input)) {
                    return;
                }
                try {
                    client.processInput(input);
                } catch (ProtocolException | IOException e) {
                    log.error("Failed to process user input", e);
                }
            }
        } catch (Exception e) {
            log.error("Application failed.", e);
        } finally {
            if (client != null) {
                // TODO
//                client.close();
            }
        }
    }
}