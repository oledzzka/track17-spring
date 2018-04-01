package track.msgtest.messenger.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import track.msgtest.messenger.messages.*;

import java.io.IOException;
import java.util.Map;


/**
 * Простейший протокол передачи данных
 */
public class JsonProtocol implements Protocol {

    static Logger log = LoggerFactory.getLogger(JsonProtocol.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        Map map = null;
        try {
            map = objectMapper.readValue(bytes, Map.class);
        } catch (IOException e) {
            throw new ProtocolException("Error decode: " + e.toString());
        }
        log.info("decoded: {}", map);
        Type type = Type.valueOf((String) map.get("type"));
        try {
            switch (type) {
                case MSG_TEXT:
                    return objectMapper.readValue(bytes, TextMessage.class);
                case MSG_LOGIN:
                    return objectMapper.readValue(bytes, LoginMessage.class);
                case MSG_INFO:
                    return new InfoMessage();
                case MSG_STATUS:
                    return objectMapper.readValue(bytes, StatusMessage.class);
                case MSG_INFO_RESULT:
                    return objectMapper.readValue(bytes, InfoResultMessage.class);
                default:
                    throw new ProtocolException("Invalid type: " + type);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new ProtocolException("Invalid message" + e.toString());
        }
    }

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        String string = null;
        try {
            string = objectMapper.writeValueAsString(msg);
        } catch (IOException e) {
            throw new ProtocolException("Error encode msg:" + msg.toString());
        }
        log.info("encoded: {}", string);
        return string.getBytes();
    }

    private Long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            // who care
        }
        return null;
    }
}