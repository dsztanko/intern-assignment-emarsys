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
    private ArrayList<Location> unsortedLocations;

    @Before
    public void setUp() throws InvalidLocationNameException {
        l1 = new Location("x");
        l2 = new Location("y");
        l3 = new Location("z");
    }

    @Test
    public void valid_name_initialization() throws Exception {
        assertEquals(l1.getName(), "x");
    }

    @Test
    public void valid_dependency_setting() throws Exception {
        l2.setDependency(l1);
        assertEquals(l2.getDependency(), l1);
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
        assertEquals(Location.setUpRoute(unsortedLocations), unsortedLocations);
    }

    @Test
    public void locations_without_dependencies_returns_itself() throws Exception {
        unsortedLocations = new ArrayList<Location>();
        unsortedLocations.add(l1);
        unsortedLocations.add(l2);
        unsortedLocations.add(l3);
        assertEquals(Location.setUpRoute(unsortedLocations), unsortedLocations);
    }

    @Test
    public void location_not_having_dependency() throws Exception {
        assertFalse(l1.haveDependency());
    }

    @Test
    public void location_having_dependency() throws Exception {
        l2.setDependency(l1);
        assertTrue(l2.haveDependency());
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
}