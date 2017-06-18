package com.otus.hw03.collection;

import java.util.*;

import static org.junit.Assert.*;

public class CustomArrayListTest {
    private CustomArrayList<Integer> list;

    @org.junit.Before
    public void setUp() throws Exception {
        list = new CustomArrayList<>();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        list.clear();
    }

    @org.junit.Test
    public void collectionsAddAll() throws Exception {
        Collections.addAll(list, 1, 2, 3);
        assert list.contains(1);
        assert list.contains(2);
        assert list.contains(3);
        assert !list.contains(4);
    }

    @org.junit.Test
    public void collectionsCopy() throws Exception {
        Collections.addAll(list, 1, 2, 3, 4);
        List<Integer> copy = new CustomArrayList<>(4);
        Collections.copy(copy, list);

        assert copy.contains(1);
        assert copy.contains(2);
        assert copy.contains(3);
        assert !copy.contains(5);
    }

    @org.junit.Test
    public void collectionsSort() throws Exception {
        Collections.addAll(list, 1, 5, 2, 3, 8, 12);
        Object[] naturalOrder = {1, 2, 3, 5, 8, 12};
        Object[] reverseOrder = {12, 8, 5, 3, 2, 1};

        Collections.sort(list, Comparator.reverseOrder());
        assertEquals(reverseOrder, list.toArray());

        Collections.sort(list, Comparator.naturalOrder());
        assertEquals(naturalOrder, list.toArray());
    }

    @org.junit.Test
    public void size() throws Exception {
        assert list.size() == 0;

        list.add(1);
        assert list.size() == 1;

        list.add(0, 5);
        assert list.size() == 2;

        list.add(10);
        list.remove(new Integer(5));
        assert list.size() == 2;

        list.remove(0);
        assert list.size() == 1;

        list.clear();
        assert list.size() == 0;
    }

    @org.junit.Test
    public void isEmpty() throws Exception {
        assert list.isEmpty();

        list.add(1);
        assert !list.isEmpty();

        list.clear();
        assert list.isEmpty();
    }

    @org.junit.Test
    public void contains() throws Exception {
        assert !list.contains(1);

        list.add(1);
        list.add(2);
        list.add(3);

        assert list.contains(1);
        assert list.contains(2);
        assert list.contains(3);
        assert !list.contains(4);
    }

    @org.junit.Test
    public void toArray() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);

        Object[] compare = {1, 2, 3};
        assertEquals(list.toArray(), compare);

        Integer fullCompare[] = {1, 2, 3};
        Integer reducedCompare[] = {1, 2};

        Integer full[] = new Integer[3];
        Integer reduced[] = new Integer[2];

        assertEquals(fullCompare, list.toArray(full));
        assertEquals(reducedCompare, list.toArray(reduced));
        assertNotEquals(fullCompare, list.toArray(reduced));
    }

    @org.junit.Test
    public void add() throws Exception {
        list.add(1);
        list.add(2);

        Object before[] = {1, 2};
        Object after[] = {1, 3, 2};

        assertEquals(list.toArray(), before);

        list.add(1, 3);
        assertEquals(list.toArray(), after);
    }

    @org.junit.Test
    public void remove() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);

        Object afterFirst[] = {1, 3};
        Object afterSecond[] = {1};

        list.remove(1);
        assertEquals(list.toArray(), afterFirst);

        list.remove(new Integer(3));
        assertEquals(list.toArray(), afterSecond);
    }

    @org.junit.Test
    public void clear() throws Exception {
        list.add(1);
        list.add(2);
        Object[] empty = {};

        assert list.size() == 2;

        list.clear();
        assert list.size() == 0;
        assertEquals(empty, list.toArray());
    }

    @org.junit.Test
    public void get() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);

        assert list.get(0) == 1;
        assert list.get(1) == 2;
        assert list.get(2) == 3;
    }

    @org.junit.Test
    public void set() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);

        list.set(0, 0);
        list.set(1, 5);

        Object[] expected = {0, 5, 3};
        assertEquals(expected, list.toArray());
    }

    @org.junit.Test
    public void indexOf() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(5);

        assert list.indexOf(100) == -1;
        assert list.indexOf(1) == 0;
        assert list.indexOf(3) == 2;
        assert list.lastIndexOf(1) == 3;
    }

    @org.junit.Test
    public void containsAll() throws Exception {
        Collections.addAll(list, 1, 2, 3, 4, 5);

        Integer[] inclusion = {2, 3, 4};
        Integer[] exclusion = {5, 6};

        assert list.containsAll(Arrays.asList(inclusion));
        assert !list.containsAll(Arrays.asList(exclusion));
    }

    @org.junit.Test
    public void addAll() throws Exception {
        CustomArrayList<Integer> runOne = new CustomArrayList<>();
        CustomArrayList<Integer> runTwo = new CustomArrayList<>();
        Collections.addAll(runOne, 1, 2, 3);
        Collections.addAll(runTwo, 3, 4, 5);

        Object[] first = {1, 2, 3};
        Object[] second = {0, 1, 2, 3};
        Object[] third = {0, 3, 4, 5, 1, 2, 3};

        list.addAll(runOne);
        assertEquals(first, list.toArray());

        list.clear();
        list.add(0);
        list.addAll(runOne);
        assertEquals(second, list.toArray());

        list.addAll(1, runTwo);
        assertEquals(third, list.toArray());
    }

    @org.junit.Test
    public void removeAll() throws Exception {
        CustomArrayList<Integer> values = new CustomArrayList<>();
        Object[] control = {0, 4, 5};

        Collections.addAll(values, 1, 2, 3);
        Collections.addAll(list, 0, 2, 3, 4, 2, 5);

        list.removeAll(values);
        assertEquals(control, list.toArray());
    }

    @org.junit.Test
    public void retainAll() throws Exception {
        CustomArrayList<Integer> values = new CustomArrayList<>();
        Object[] control = {2, 3};

        Collections.addAll(values, 1, 2, 3);
        Collections.addAll(list, 0, 2, 3, 4, 2, 5);

        list.retainAll(values);
        assertEquals(control, list.toArray());
    }

    @org.junit.Test
    public void subList() throws Exception {
        Collections.addAll(list, 1, 2, 3, 4, 5);
        Object[] expected = {2, 3, 4};

        List<Integer> sublist = list.subList(1, 3);
        assertEquals(expected, sublist.toArray());
        assert sublist.size() == 3;
    }

    @org.junit.Test
    public void iterator() throws Exception {
        Object[] expected = {1, 3, 4, 5};
        ListIterator<Integer> iterator = list.listIterator();

        assert !iterator.hasNext();
        assert !iterator.hasNext();

        Collections.addAll(list, 1, 2, 3, 4, 5);
        assert iterator.hasNext();
        assert iterator.nextIndex() == 0;
        assert iterator.next() == 1;
        assert iterator.next() == 2;
        assert iterator.hasPrevious();
        assert iterator.previousIndex() == 0;

        iterator.remove();
        assertEquals(expected, list.toArray());
    }
}