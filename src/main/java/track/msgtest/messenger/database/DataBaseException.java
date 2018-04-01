package track.msgtest.messenger.database;

/**
 * Created by oleg on 19.06.17.
 */

public class DataBaseException extends Exception {

    public DataBaseException(String msg) {
        super(msg);
    }

    public DataBaseException(Throwable ex) {
            super(ex);
    }

}
