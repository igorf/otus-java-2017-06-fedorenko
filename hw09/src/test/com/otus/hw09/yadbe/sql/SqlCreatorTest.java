package com.otus.hw09.yadbe.sql;

import mock.SampleDataSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SqlCreatorTest {
    private SampleDataSet sds;
    private final SqlCreator creator = new SqlCreator();

    @Before
    public void setUp() throws Exception {
        sds = new SampleDataSet();
    }

    @Test
    public void createSQL() throws Exception {
        sds.setX(1);
        sds.setY("A'bc");
        sds.setZ(false);

        String sql = creator.createSQL(sds).getSql();
        assertEquals("insert into sample_data (x, y) values (?, ?)", sql);
    }

    @Test
    public void updateSQL() throws Exception {
        sds.setId(25);
        sds.setX(5);
        sds.setY("XXX");
        sds.setZ(true);

        String sql = creator.updateSQL(sds).getSql();
        assertEquals("update sample_data set x = ?, y = ? where id = ?", sql);
    }

    @Test
    public void readSQL() throws Exception {
        String sql = creator.readSQL(10, SampleDataSet.class);
        assertEquals("select * from sample_data where id = 10", sql);
    }
}