package com.codecool.holiday_optimalizer.model;

import com.codecool.holiday_optimalizer.exception.InvalidLocationNameException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by dorasztanko on 2017.03.20..
 */
public class LocationTest {
    private Location l1;
    private Location l2;

    @Test
    public void valid_name_initialization() throws Exception {
        l1 = new Location("a");
        assertEquals(l1.getName(), "a");
    }

    @Test
    public void valid_dependency_setting() throws Exception {
        l1 = new Location("a");
        l2 = new Location("b");
        l2.setDependency(l1);
        assertEquals(l2.getDependency(), l1);
    }

    @Test
    public void dependency_location_can_be_null() throws Exception {
        l1 = new Location("a");
        assertNull(l1.getDependency());
    }

    @Test (expected = InvalidLocationNameException.class)
    public void location_name_cannot_be_number() throws Exception {
        l1 = new Location("1");
    }

    @Test (expected = InvalidLocationNameException.class)
    public void location_name_cannot_be_uppercase() throws Exception {
        l1 = new Location("A");
    }

    @Test (expected = InvalidLocationNameException.class)
    public void location_name_cannot_be_more_than_one_letter() throws Exception {
        l1 = new Location("Békéscsaba");
    }

    @Test (expected = InvalidLocationNameException.class)
    public void location_name_cannot_be_special_char() throws Exception {
        l1 = new Location("%");
    }
}