package com.otus.hw03.collection;

import java.util.*;

public class CustomArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 3;
    private static final int PRINTABLE_ELEMENTS = 25;
    private static final float SCALE_FACTOR = 0.1f;

    private Object[] data;
    private int size = 0;

    private boolean incrementStorageSize() {
        Object[] incremented = new Object[data.length + calcSizeIncrement()];
        System.arraycopy(data, 0, incremented, 0, data.length);
        data = incremented;

        return true;
    }

    private int calcSizeIncrement() {
        return Math.max((int)((float)size * SCALE_FACTOR), DEFAULT_CAPACITY);
    }

    CustomArrayList(int capacity) {
        int realCapacity = capacity > 0 ? capacity : DEFAULT_CAPACITY;
        data = new Object[realCapacity];
        size = capacity;
    }

    CustomArrayList() {
        this(DEFAULT_CAPACITY);
        size = 0;
    }

    private CustomArrayList(T[] data) {
        this.data = data;
        size = data.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Object[] toArray() {
        Object[] copy = new Object[size];
        System.arraycopy(data, 0, copy, 0, size);
        return copy;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int copySize = Math.min(size, a.length);
        System.arraycopy(data, 0, a, 0, copySize);
        return a;
    }

    @Override
    public boolean add(T t) {
        if (data.length == size && !incrementStorageSize()) {
            return false;
        }

        data[size] = t;
        size += 1;

        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {
            add(element);
        } else {
            Object[] increased = new Object[data.length + 1];
            increased[index] = element;

            System.arraycopy(data, 0, increased, 0, index);
            System.arraycopy(data, index, increased, index + 1, size - index);
            size += 1;

            data = increased;
        }
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        while (index >= 0) {
            try {
                remove(index);
                index = indexOf(o);
                // OutOfBounds?
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < size) {
            T removed = get(index);
            size -= 1;

            if (size > 0) {
                Object[] newData = new Object[data.length - 1];
                System.arraycopy(data, 0, newData, 0, index);
                System.arraycopy(data, index + 1, newData, index, data.length - index - 1);
                data = newData;
            } else {
                clear();
            }
            return removed;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public void clear() {
        size = 0;
        data = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            return (T) data[index];
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T set(int index, T element) {
        if (index >= 0 && index < size) {
            data[index] = element;
            return element;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i > -1; i--) {
            if (data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item: c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomListIterator<>(this);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new CustomListIterator<>(this);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }
        CustomListIterator<T> iterator = new CustomListIterator<>(this);
        iterator.setPosition(index);
        return iterator;
    }

    @Override
    public String toString() {
        //Ну да, муторно, но все равно не больше 25 элементов. На скорости не скжется.
        StringBuilder sb = new StringBuilder();
        int maxElements = Math.min(size, PRINTABLE_ELEMENTS);

        sb.append("[");
        for (int i = 0; i < maxElements; i++) {
            sb.append(data[i].toString());
            if (i < maxElements - 1 || maxElements < size) {
                sb.append(", ");
            }
        }

        if (maxElements < size) {
            sb.append("...");
        }
        sb.append("]");

        return sb.toString();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object newData[] = new Object[size() + c.size()];
        System.arraycopy(data, 0, newData, 0, size);
        System.arraycopy(c.toArray(), 0, newData, size, c.size());
        data = newData;
        size = data.length;

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        Object newData[] = new Object[size() + c.size()];
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            return addAll(c);
        }
        if (index > 0) {
            System.arraycopy(data, 0, newData, 0, index);
        }
        System.arraycopy(c.toArray(), 0, newData, index, c.size());
        System.arraycopy(data, index, newData, index + c.size(), size - index);

        data = newData;
        size = data.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // А вот это как сделать пока вопрос. Так - очевидно медленно
        for(Object element: c) {
            remove(element);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int toRetain = 0;
        Object[] retain = new Object[c.size()];
        for (Object o: c) {
            if (contains(o)) {
                retain[toRetain] = o;
                toRetain ++;
            }
        }

        clear();
        if (toRetain > 0) {
            data = new Object[toRetain];
            System.arraycopy(retain, 0, data, 0, toRetain);
            size = data.length;
        }

        return true;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0
                || fromIndex >= size
                || fromIndex >= toIndex
                || toIndex <= 0
                || toIndex >= size) {
            throw new IndexOutOfBoundsException();
        }

        Object[] subData = new Object[toIndex - fromIndex + 1];
        System.arraycopy(data, fromIndex, subData, 0, toIndex - fromIndex + 1);

        return new CustomArrayList<>((T[]) subData);
    }
}
