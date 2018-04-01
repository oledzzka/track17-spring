package track.msgtest.messenger.handlers;

/**
 * Created by oleg on 12.05.17.
 */
public class CommandException extends Exception {

    public CommandException(String ex) {
        super(ex);
    }

    public CommandException(Throwable ex) {
        super(ex);
    }
}
