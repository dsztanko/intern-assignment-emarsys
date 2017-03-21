package com.codecool.holiday_optimizer.model;

import com.codecool.holiday_optimizer.exception.InvalidLocationNameException;
import com.codecool.holiday_optimizer.exception.NoLocationsGivenException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by dorasztanko on 2017.03.20..
 */
public class HolidayOptimizerTest {
    private Location l1;
    private Location l2;
    private Location l3;
    private Location l4;
    private Location l5;
    private Location l6;
    private Route route;
    private Route route2;

    @Before
    public void setUp() throws InvalidLocationNameException {
        l1 = new Location("x");
        l2 = new Location("y");
        l3 = new Location("z");
        route = new Route(new ArrayList<Location>(Arrays.asList(l1)));
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

    @Test
    public void location_having_dependency() throws Exception {
        l2.setDependency(l1);
        assertTrue(l2.hasDependency());
    }

    @Test
    public void location_not_having_dependency() throws Exception {
        assertFalse(l1.hasDependency());
    }

    @Test(expected = NoLocationsGivenException.class)
    public void null_list_of_locations_as_journey_throws_exception() throws Exception {
        route = new Route(null);
        route.optimizeRoute();
    }

    @Test
    public void list_contains_one_element() throws Exception {
        assertTrue(route.containsOnlyOne());
    }

    @Test
    public void list_contains_more_than_one_element() throws Exception {
        route.getUnsortedJourney().add(l2);
        assertFalse(route.containsOnlyOne());
    }

    @Test
    public void one_input_location_returns_itself() throws Exception {
        assertEquals(route.getUnsortedJourney(), route.optimizeRoute());
    }

    @Test
    public void locations_without_dependencies_returns_itself() throws Exception {
        route.getUnsortedJourney().add(l2);
        route.getUnsortedJourney().add(l3);
        assertEquals(route.getUnsortedJourney(), route.optimizeRoute());
    }

    @Test
    public void middle_one_location_has_dependency() throws Exception {
        l2.setDependency(l3);
        route.getUnsortedJourney().add(l1);
        route.getUnsortedJourney().add(l2);
        route.getUnsortedJourney().add(l3);
        route.optimizeRoute();
        assertEquals(route.getSortedJourney(), new ArrayList<Location>(Arrays.asList(l1, l3, l2)));
    }

    @Test
    public void test_complex_dependency_chain_1() throws Exception {
        l4 = new Location("o");
        l1.setDependency(l2);
        l3.setDependency(l4);
        route.getUnsortedJourney().add(l1);
        route.getUnsortedJourney().add(l2);
        route.getUnsortedJourney().add(l3);
        route.getUnsortedJourney().add(l4);
        route.optimizeRoute();
        assertEquals(route.getSortedJourney(), new ArrayList<Location>(Arrays.asList(l2, l1, l4, l3)));
    }

    @Test
    public void test_complex_dependency_chain_2() throws Exception {
        route2 = new Route(new ArrayList<Location>());
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
        route2.getUnsortedJourney().add(l1);
        route2.getUnsortedJourney().add(l2);
        route2.getUnsortedJourney().add(l3);
        route2.getUnsortedJourney().add(l4);
        route2.getUnsortedJourney().add(l5);
        route2.getUnsortedJourney().add(l6);
        route2.optimizeRoute();
        assertEquals(route2.getSortedJourney(), new ArrayList<Location>(Arrays.asList(l6, l3, l1, l4, l2, l5)));
    }
}