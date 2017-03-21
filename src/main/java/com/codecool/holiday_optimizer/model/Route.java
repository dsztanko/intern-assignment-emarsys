package com.codecool.holiday_optimizer.model;

import com.codecool.holiday_optimizer.exception.NoLocationsGivenException;

import java.util.ArrayList;

/**
 * Created by dorasztanko on 2017.03.21..
 */
public class Route {
    private ArrayList<Location> unsortedJourney;
    private ArrayList<Location> sortedJourney;

    public Route(ArrayList<Location> locations) {
        this.unsortedJourney = locations;
    }

    public ArrayList<Location> getUnsortedJourney() {
        return unsortedJourney;
    }

    public ArrayList<Location> getSortedJourney() {
        return sortedJourney;
    }

    public ArrayList<Location> optimizeRoute() throws NoLocationsGivenException {
        if (this.unsortedJourney == null) {
            throw new NoLocationsGivenException();
        } else {
            if (containsOnlyOne() || allLocationsWithoutDependencies()) {
                return this.unsortedJourney;
            } else {
                return orderingLocations();
            }
        }
    }

    public boolean containsOnlyOne() {
        return this.unsortedJourney.size() == 1;
    }

    public boolean allLocationsWithoutDependencies() {
        for (Location location : this.unsortedJourney) {
            if (location.hasDependency()) {
                return false;
            }
        }
        return true;
    }

    public boolean alreadyInList(Location location) {
        return this.sortedJourney.contains(location);
    }

    public void updateElement(Location location) {
        this.sortedJourney.remove(location);
        this.sortedJourney.add(location);
    }

    public ArrayList<Location> orderingLocations() {
        this.sortedJourney = new ArrayList<Location>();
        for (Location location : this.unsortedJourney) {
            if (!location.hasDependency()) {
                if (!alreadyInList(location)) {
                    this.sortedJourney.add(location);
                }
            } else {
                if (!alreadyInList(location.getDependency())) {
                    this.sortedJourney.add(location.getDependency());
                } else {
                    updateElement(location.getDependency());
                }
                if (alreadyInList(location)) {
                    updateElement(location);
                } else {
                    this.sortedJourney.add(location);
                }
            }
        }
        return this.sortedJourney;
    }
}

