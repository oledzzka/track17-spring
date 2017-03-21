package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Created by oleg on 22.03.17.
 */
public interface Stack {

    void push(int val);

    int pop() throws NoSuchElementException;
}
