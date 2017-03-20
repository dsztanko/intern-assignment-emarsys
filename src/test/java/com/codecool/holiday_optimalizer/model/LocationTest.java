package com.codecool.holiday_optimalizer.model;

import com.codecool.holiday_optimalizer.exception.InvalidLocationNameException;
import com.codecool.holiday_optimalizer.exception.NoLocationsGivenException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by dorasztanko on 2017.03.20..
 */
public class LocationTest {
    private Location l1;
    private Location l2;
    private ArrayList<Location> unsortedLocations;

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

    @Test(expected = NoLocationsGivenException.class)
    public void empty_list_of_locations_as_journey_throws_exception() throws Exception {
        Location.setUpRoute(unsortedLocations);
    }

    @Test
    public void list_contains_one_element() throws Exception {
        ArrayList<Integer> listOfElements = new ArrayList<Integer>(Arrays.asList(1));
        assertTrue(Location.containsOnlyOne(listOfElements));
    }

    @Test
    public void list_contains_more_than_one_element() throws Exception {
        ArrayList<Integer> listOfElements = new ArrayList<Integer>(Arrays.asList(1, 2));
        assertFalse(Location.containsOnlyOne(listOfElements));
    }

    @Test
    public void one_input_location_returns_itself() throws Exception {
        unsortedLocations = new ArrayList<Location>();
        unsortedLocations.add(new Location("x"));
        assertEquals(Location.setUpRoute(unsortedLocations), unsortedLocations);
    }

    @Test
    public void locations_without_dependencies_returns_itself() throws Exception {
        unsortedLocations = new ArrayList<Location>();
        unsortedLocations.add(new Location("x"));
        unsortedLocations.add(new Location("y"));
        unsortedLocations.add(new Location("z"));
        assertEquals(Location.setUpRoute(unsortedLocations), unsortedLocations);
    }
}