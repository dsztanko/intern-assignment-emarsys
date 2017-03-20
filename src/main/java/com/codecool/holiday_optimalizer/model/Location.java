package com.codecool.holiday_optimalizer.model;

import com.codecool.holiday_optimalizer.exception.InvalidLocationNameException;

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
}
