package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
class MyArrayList extends List {

    static final int DEFAULT_CAPACITY = 10;

    private int realSize = DEFAULT_CAPACITY;
    private int [] array;

    MyArrayList() {
        array = new int[realSize];
    }

    MyArrayList(int capacity) {
        size = capacity;
        if (realSize < capacity) {
            realSize = capacity;
        }
        array = new int [realSize];
    }

    @Override
    void add(int item) {
        if (realSize == size) {
            realSize = size * 3 / 2;
            int [] tmp = new int[realSize];
            System.arraycopy(array, 0, tmp, 0, size);
            array = tmp;
        }
        array[size] = item;
        size++;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >= size) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            size--;
            return array[size];
        }
        System.arraycopy(array, (idx + 1), array, idx, (--size - idx));
        int num = array[idx];
        return num;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >= size) {
            throw new NoSuchElementException();
        }
        return array[idx];
    }

}
