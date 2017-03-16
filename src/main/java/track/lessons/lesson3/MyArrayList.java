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


    private int realSize = 10;
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
            float increase = (float) 1.5;
            int [] tmp = new int[size];
            System.arraycopy(array, 0, tmp, 0, size);
            realSize = (int)(size * increase);
            array = new int[realSize];
            System.arraycopy(tmp, 0, array, 0, size);
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
        int [] tmp = new int [size - 1];
        int tmpIter = 0;
        for (int i = 0 ; i < size; i++) {
            if (i == idx) {
                continue;
            }
            tmp[tmpIter] = array[i];
            tmpIter++;
        }
        int num = array[idx];
        size--;
        System.arraycopy(tmp, 0, array, 0, size);
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
