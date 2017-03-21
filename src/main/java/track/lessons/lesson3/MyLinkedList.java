package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */

public class MyLinkedList extends List implements Queue, Stack {

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    Node lastNode = null;
    Node startNode = null;


    @Override
    void add(int item) {
        if ( lastNode == null) {
            lastNode = new Node(null, null, item);
            startNode = lastNode;
        } else {
            Node node = new Node(lastNode, null, item);
            lastNode.next = node;
            lastNode = node;
        }
        size++;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >= size) {
            throw new NoSuchElementException();
        }
        Node node = startNode;
        for (int i = 0; i < idx; i++) {
            node = node.next;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            startNode = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            lastNode = node.prev;
        }
        size--;

        return node.val;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >= size) {
            throw new NoSuchElementException();
        }
        Node node = startNode;
        for (int i = 0; i < idx; i++) {
            node = node.next;
        }
        return node.val;
    }


    @Override
    public void push(int val) {
        add(val);
    }

    @Override
    public int pop() throws NoSuchElementException {
        return remove(size - 1 );
    }

    @Override
    public void enqueue(int val) {
        add(val);
    }

    @Override
    public int dequeue() throws NoSuchElementException {
        return remove(0);
    }
}
