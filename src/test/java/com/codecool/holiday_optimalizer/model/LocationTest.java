package com.codecool.holiday_optimalizer.model;

import com.codecool.holiday_optimalizer.exception.InvalidLocationNameException;
import com.codecool.holiday_optimalizer.exception.NoLocationsGivenException;
import org.junit.Before;
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
    private Location l3;
    private Location l4;
    private Location l5;
    private Location l6;
    private ArrayList<Location> unsortedLocations;

    @Before
    public void setUp() throws InvalidLocationNameException {
        l1 = new Location("x");
        l2 = new Location("y");
        l3 = new Location("z");
    }

    @Test
    public void valid_name_initialization() throws Exception {
        assertEquals("x", l1.getName());
    }

    @Test
    public void valid_dependency_setting() throws Exception {
        l2.setDependency(l1);
        assertEquals(l1, l2.getDependency());
    }

    @Test
    public void dependency_location_can_be_null() throws Exception {
        assertNull(l1.getDependency());
    }

    @Test (expected = InvalidLocationNameException.class)
    public void location_name_cannot_be_number() throws Exception {
        l3 = new Location("1");
    }

    @Test (expected = InvalidLocationNameException.class)
    public void location_name_cannot_be_uppercase() throws Exception {
        l3 = new Location("A");
    }

    @Test (expected = InvalidLocationNameException.class)
    public void location_name_cannot_be_more_than_one_letter() throws Exception {
        l3 = new Location("Békéscsaba");
    }

    @Test (expected = InvalidLocationNameException.class)
    public void location_name_cannot_be_special_char() throws Exception {
        l3 = new Location("%");
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
        unsortedLocations.add(l1);
        assertEquals(unsortedLocations, Location.setUpRoute(unsortedLocations));
    }

    @Test
    public void locations_without_dependencies_returns_itself() throws Exception {
        unsortedLocations = new ArrayList<Location>();
        unsortedLocations.add(l1);
        unsortedLocations.add(l2);
        unsortedLocations.add(l3);
        assertEquals(unsortedLocations, Location.setUpRoute(unsortedLocations));
    }

    @Test
    public void location_not_having_dependency() throws Exception {
        assertFalse(l1.hasDependency());
    }

    @Test
    public void location_having_dependency() throws Exception {
        l2.setDependency(l1);
        assertTrue(l2.hasDependency());
    }

    @Test
    public void middle_one_location_has_dependency() throws Exception {
        unsortedLocations = new ArrayList<Location>();
        l2.setDependency(l3);
        unsortedLocations.add(l1);
        unsortedLocations.add(l2);
        unsortedLocations.add(l3);
        ArrayList<Location> sortedLocations = Location.setUpRoute(unsortedLocations);
        assertEquals(sortedLocations, new ArrayList<Location>(Arrays.asList(l1, l3, l2)));
    }

    @Test
    public void test_complex_dependency_chain_1() throws Exception {
        unsortedLocations = new ArrayList<Location>();
        l4 = new Location("o");
        l1.setDependency(l2);
        l3.setDependency(l4);
        unsortedLocations.add(l1);
        unsortedLocations.add(l2);
        unsortedLocations.add(l3);
        unsortedLocations.add(l4);
        ArrayList<Location> sortedLocations = Location.setUpRoute(unsortedLocations);
        assertEquals(sortedLocations, new ArrayList<Location>(Arrays.asList(l2, l1, l4, l3)));
    }

    @Test
    public void test_complex_dependency_chain_2() throws Exception {
        unsortedLocations = new ArrayList<Location>();
        l1 = new Location("u");
        l6 = new Location("z");
        l3 = new Location("w");
        l3.setDependency(l6);
        l2 = new Location("v");
        l2.setDependency(l3);
        l4 = new Location("x");
        l4.setDependency(l1);
        l5 = new Location("y");
        l5.setDependency(l2);
        unsortedLocations.add(l1);
        unsortedLocations.add(l2);
        unsortedLocations.add(l3);
        unsortedLocations.add(l4);
        unsortedLocations.add(l5);
        unsortedLocations.add(l6);
        ArrayList<Location> sortedLocations = Location.setUpRoute(unsortedLocations);
        assertEquals(sortedLocations, new ArrayList<Location>(Arrays.asList(l6, l3, l1, l4, l2, l5)));
    }
}