package com.codecool.holiday_optimalizer.model;

import com.codecool.holiday_optimalizer.exception.InvalidLocationNameException;
import com.codecool.holiday_optimalizer.exception.NoLocationsGivenException;

import java.util.ArrayList;

/**
 * Created by dorasztanko on 2017.03.20..
 */
public class Location {
    private String name;
    private Location dependency;

    public Location(String name) throws InvalidLocationNameException {
        if (!name.matches("[a-z]")) {
            throw new InvalidLocationNameException();
        }
        this.name = name;
    }

    public void setDependency(Location dependency) {
        this.dependency = dependency;
    }

    public String getName() {
        return name;
    }

    public Location getDependency() {
        return dependency;
    }

    public static ArrayList<Location> setUpRoute(ArrayList<Location> unsortedJourney) throws NoLocationsGivenException {
        if (unsortedJourney == null) {
            throw new NoLocationsGivenException();
        }
        else {
            if (Location.containsOnlyOne(unsortedJourney)) {
                return unsortedJourney;
            }
            else {
                return null;
            }
        }
    }

    public static boolean containsOnlyOne(ArrayList listOfElements) {
        return listOfElements.size() == 1;
    }
}
