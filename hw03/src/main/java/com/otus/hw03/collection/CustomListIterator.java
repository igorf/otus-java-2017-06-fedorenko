package com.otus.hw03.collection;

import java.util.ListIterator;

public class CustomListIterator<T> implements ListIterator<T> {

    private int position = -1;
    private CustomArrayList<T> list;

    CustomListIterator(CustomArrayList<T> list) {
        this.list = list;
    }

    void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean hasNext() {
        return list.size() > position + 1;
    }

    @Override
    public T next() {
        if (position < list.size() - 1) {
            position++;
        }
        return list.get(position);
    }

    @Override
    public boolean hasPrevious() {
        return (position > 0 && list.size() > 0);
    }

    @Override
    public T previous() {
        if (position > 0) {
            position--;
            return list.get(position);
        }
        return null;
    }

    @Override
    public int nextIndex() {
        return position + 1;
    }

    @Override
    public int previousIndex() {
        return (position > 0 ? position - 1 : 0);
    }

    @Override
    public void remove() {
        if (position >= 0 && position < list.size()) {
            list.remove(position);
            if (position > 0) {
                position --;
            }
        }
    }

    @Override
    public void set(T t) {
        list.set(position, t);
    }

    @Override
    public void add(T t) {
        list.add(position, t);
    }
}
