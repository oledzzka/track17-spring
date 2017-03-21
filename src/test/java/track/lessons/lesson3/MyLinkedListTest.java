package track.lessons.lesson3;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class MyLinkedListTest {

    @Test(expected = NoSuchElementException.class)
    public void emptyList() throws Exception {
        List list = new MyLinkedList();
        Assert.assertTrue(list.size() == 0);
        list.get(0);
    }

    @Test
    public void listAdd() throws Exception {
        List list = new MyLinkedList();
        list.add(1);

        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void listAddRemove() throws Exception {
        List list = new MyLinkedList();
        list.add(1);
        list.add(2);
        list.add(3);

        Assert.assertEquals(3, list.size());

        Assert.assertEquals(1, list.get(0));
        Assert.assertEquals(2, list.get(1));
        Assert.assertEquals(3, list.get(2));

        list.remove(1);
        Assert.assertEquals(3, list.get(1));
        Assert.assertEquals(1, list.get(0));

        list.remove(1);
        list.remove(0);

        Assert.assertTrue(list.size() == 0);
    }

    @Test
    public void listRemove() throws Exception {
        List list = new MyLinkedList();
        list.add(1);
        list.remove(0);

        Assert.assertTrue(list.size() == 0);
    }

    @Test(expected = NoSuchElementException.class)
    public void throwQueue() throws Exception {
        Queue queue = new MyLinkedList();
        queue.enqueue(0);

        Assert.assertEquals(0, queue.dequeue());

        queue.dequeue();
    }

    @Test
    public void checkQueue() throws Exception {
        Queue queue = new MyLinkedList();
        queue.enqueue(0);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        Assert.assertEquals(0, queue.dequeue());
        Assert.assertEquals(1, queue.dequeue());
        Assert.assertEquals(2, queue.dequeue());
        Assert.assertEquals(3, queue.dequeue());
    }

    @Test(expected = NoSuchElementException.class)
    public void throwStack() throws Exception {
        Stack stack = new MyLinkedList();
        stack.push(0);

        Assert.assertEquals(0, stack.pop());

        stack.pop();
    }

    @Test
    public void checkStack() throws Exception {
        Stack stack = new MyLinkedList();
        stack.push(0);
        stack.push(1);
        stack.push(2);
        stack.push(3);

        Assert.assertEquals(3, stack.pop());
        Assert.assertEquals(2, stack.pop());
        Assert.assertEquals(1, stack.pop());
        Assert.assertEquals(0, stack.pop());
    }
}