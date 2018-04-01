package track.msgtest.messenger.messages;

import static track.msgtest.messenger.messages.Type.MSG_INFO_RESULT;

/**
 * Created by oleg on 02.05.17.
 */

public class InfoResultMessage extends Message {

    String information;

    public InfoResultMessage() {
        type = MSG_INFO_RESULT;
        information = "";
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
