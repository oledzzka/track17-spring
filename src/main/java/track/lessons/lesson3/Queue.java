package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Created by oleg on 22.03.17.
 */
public interface Queue {

    void enqueue(int val);

    int dequeue() throws NoSuchElementException;
}
