package track.msgtest.messenger.messages;

import static track.msgtest.messenger.messages.Type.MSG_STATUS;

/**
 * Created by oleg on 02.05.17.
 */
public class StatusMessage extends Message {
    String status;

    public StatusMessage() {
        type = MSG_STATUS;
        status = "";
    }

    public String  getStatus() {
        return status;
    }

    public void setStatus(String  status) {
        this.status = status;
    }
}
