package com.otus.hw10;

import com.otus.hw10.domain.AddressDataSet;
import com.otus.hw10.domain.AdvancedUserDataSet;
import com.otus.hw10.domain.PhoneDataSet;
import com.otus.hw10.hvariant.logic.service.HibernateAdvancedUserService;
import com.otus.hw10.hvariant.stuff.HibernateConnector;
import com.otus.hw10.service.AdvancedUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HibernateAdvancedUserTest {

    private AdvancedUserService aus;
    @Before
    public void setup() {
        HibernateConnector.connect(HibernatePropertiesCreator.getProperties());
        aus = new HibernateAdvancedUserService();
    }

    @After
    public void shutdown() {
        HibernateConnector.disconnect();
    }

    @Test
    public void complex() throws Exception {
        AdvancedUserDataSet user = new AdvancedUserDataSet();

        user.setAge(25);
        user.setName("Test User");
        new PhoneDataSet("000-111-222").addToUser(user);
        new PhoneDataSet("000-111-333").addToUser(user);
        new PhoneDataSet("000-111-444").addToUser(user);
        new AddressDataSet("Some address").addToUser(user);

        user = aus.save(user);

        assertNotEquals(0, user.getId());

        long userId = user.getId();

        AdvancedUserDataSet anotherUser = aus.find(userId);
        assertNotEquals(null, anotherUser);
        List<PhoneDataSet> anotherPhones = anotherUser.getPhones();
        assertEquals(3, anotherPhones.size());
        AddressDataSet anotherAddress = anotherUser.getAddress();
        assertEquals("Some address", anotherAddress.getAddress());

        aus.remove(anotherUser);
    }
}