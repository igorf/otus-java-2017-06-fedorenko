package com.otus.hw02.memory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class StreamMemoryChcker implements MemoryChecker {
    @Override
    public long getObjectSize(Object object) throws Exception {
        if (object instanceof Serializable) {
            return getSerializableObjectSize((Serializable) object);
        }
        throw new Exception("Non serializable object. Use another memory checker");
    }

    //Внаглую взято с https://stackoverflow.com/questions/52353/in-java-what-is-the-best-way-to-determine-the-size-of-an-object
    private long getSerializableObjectSize(Serializable object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();
        return baos.size();
    }
}
