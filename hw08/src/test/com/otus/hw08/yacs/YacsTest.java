package com.otus.hw08.yacs;

import com.google.gson.Gson;
import mock.objects.MockOne;
import mock.objects.MockTwo;
import org.junit.Test;

import static org.junit.Assert.*;

public class YacsTest {
    @Test
    public void serializeToJSON() throws Exception {
        MockTwo mockTwo = new MockTwo();
        mockTwo.setA(100);
        mockTwo.getStrings().add("Hello ");
        mockTwo.getStrings().add("world");

        String jsonMockTwo = Yacs.serializeToJSON(mockTwo);
        assertEquals("{\"a\":100,\"b\":null,\"strings\":[\"Hello \",\"world\"]}", jsonMockTwo);

        MockTwo[] innerArray = {mockTwo};
        MockOne mockOne = new MockOne();
        mockOne.setInner(innerArray);

        String jsonMockOne = Yacs.serializeToJSON(mockOne);
        assertEquals("{\"b2\":true,\"x\":0,\"numbers\":[1,2,4,6],\"y\":1.5,\"z\":\"hello, world\",\"inner\":[{\"a\":100,\"b\":null,\"strings\":[\"Hello \",\"world\"]}],\"b1\":false}", jsonMockOne);

        MockTwo mockTwoCopy = new Gson().fromJson(jsonMockTwo, MockTwo.class);
        assertEquals(mockTwo, mockTwoCopy);

        MockOne mockOneCopy = new Gson().fromJson(jsonMockOne, MockOne.class);
        assertEquals(mockOne, mockOneCopy);
    }
}