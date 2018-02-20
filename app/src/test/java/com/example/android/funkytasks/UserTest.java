package com.example.android.funkytasks;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by fc1 on 2018-02-20.
 */

public class UserTest extends ActivityInstrumentationTestCase2 {


    public UserTest(){
        super(LoginActivity.class);
    }

    @Test
    public void testGetUsername() {
        User user = new User("Kenw", "kenw@ualberta.ca", "101-001-0011");

        assertEquals("Kenw", user.getUsername());

    }

    @Test
    public void testGetEmail()  {
        User user = new User("Kenw", "kenw@ualberta.ca", "101-001-0011");
        assertEquals("kenw@ualberta.ca", user.getEmail());
    }

    @Test
    public void testSetEmail()  {
        User user = new User("Kenw", "kenw@ualberta.ca", "101-001-0011");
        user.setEmail("kenw@cs.ualberta.ca");
        assertEquals("kenw@cs.ualberta.ca", user.getEmail());
    }

    @Test
    public void testGetPhonenumber(){
        User user = new User("Kenw", "kenw@ualberta.ca", "101-001-0011");
        assertEquals("101-001-0011", user.getPhonenumber());
    }

    @Test
    public void testSetPhonenumber() {
        User user = new User("Kenw", "kenw@ualberta.ca", "101-001-0011");
        user.setPhonenumber("101-001-1111");
        assertEquals("101-001-1111", user.getPhonenumber());
    }

    @Test
    public void testGetRating() {
        User user = new User("Kenw", "kenw@ualberta.ca", "101-001-0011");
        assertEquals(3, user.getRating());
    }

    @Test
    public void testSetRating() {
        User user = new User("Kenw", "kenw@ualberta.ca", "101-001-0011");
        user.setRating(5);
        assertEquals(5, user.getRating());
    }
}
