package track.msgtest.messenger.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static track.msgtest.messenger.messages.Type.MSG_INFO;

/**
 * Created by oleg on 02.05.17.
 */
public class InfoMessage extends Message {

    public static final long CURRENT_USER = 0;
    long userId;


    public InfoMessage() {
        userId = CURRENT_USER;
        type = MSG_INFO;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public String toString() {
        return type + String .valueOf(userId);
    }
}
